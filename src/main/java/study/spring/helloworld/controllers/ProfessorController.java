package study.spring.helloworld.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import study.spring.helloworld.exceptions.StringFormatException;
import study.spring.helloworld.helpers.Pagenation;
import study.spring.helloworld.helpers.RegexHelper;
import study.spring.helloworld.helpers.WebHelper;
import study.spring.helloworld.models.DepartmentModel;
import study.spring.helloworld.models.ProfessorModel;
import study.spring.helloworld.services.DepartmentService;
import study.spring.helloworld.services.ProfessorService;

@Controller
@RequiredArgsConstructor
public class ProfessorController {
    /** 비지니스 로직을 구현하는 Service 객체 */
    private final ProfessorService professorService;

    /** 학과 테이블에 대한 비즈니스 로직 객체 */
    private final DepartmentService departmentService;

    /**WbeHelper 객체 */
    private final WebHelper webHelper;

    /**정규표현식 객체 */
    private final RegexHelper regexHelper;

    /** 목록 페이지 */
    @GetMapping({"/professor", "/professor/list.do"})
    public ModelAndView list(Model model, 
        @RequestParam(value="keyword", required = false)String keyword,
        @RequestParam(value="page", defaultValue = "1") int nowPage){

            int totalCount = 0; //전체 게시글 수
            int listCount = 15; //한 페이지당 표시할 목록수
            int pageCount = 5;  //한 그룹당 표시할 페이지 번호 수

            ProfessorModel input = new ProfessorModel();
            input.setName(keyword);

            List<ProfessorModel> output = null; // 조회결과가 저장될 객체
            Pagenation pagenation = null; // 페이지 번호를 계산한 결과가 저장될 객체

            try {
                totalCount = professorService.selectCount(input);

                pagenation = new Pagenation(nowPage, totalCount, listCount, pageCount);

                ProfessorModel.setOffset(pagenation.getOffset());
                ProfessorModel.setListCount(pagenation.getListCount());

                output = professorService.selectList(input);
                
            }catch (Exception e) {
                return webHelper.serverError(e);
            }

            model.addAttribute("output", output);
            model.addAttribute("keyword", keyword);
            model.addAttribute("pagenation", pagenation);

        return new ModelAndView("professor/list");
    }

    /**상세 페이지 */
    @GetMapping("/professor/read.do")
    public ModelAndView read(Model model,
        @RequestParam(value="profno", defaultValue = "0") int profno){

            /** 1) 파라미터 검사 */
            try {
                regexHelper.isZero(profno, "교수번호가 없습니다.");
            } catch (StringFormatException e) {
                return webHelper.badRequest(e);
            }
            /** 2) 데이터 조회하기 */
            // 데이터 조회에 필요한 조건값을 Beans에 저장하기                  
            ProfessorModel input = new ProfessorModel();
            input.setProfno(profno);

            ProfessorModel output = null;

            try {
                output = professorService.selectItem(input);
            }catch (Exception e) {
                return webHelper.serverError(e);
            }

            model.addAttribute("output", output);

        return new ModelAndView("professor/read");
    }

    /** 작성 폼 페이지 */
    @GetMapping("/professor/add.do")
    public ModelAndView add(Model model){
        List<DepartmentModel> departmentModels = null;

        //데이터 조회--> 검색 조건 없이 모든 학과 조회
        try {
            departmentModels = departmentService.selectList(null);
        } catch (Exception e) {
            return webHelper.serverError(e);
        }

        model.addAttribute("departmentModels", departmentModels);

        return new ModelAndView("professor/add");
    }

    /** 작성 폼에 대한 action 페이지 */
    @PostMapping("/professor/add_ok.do")
    public ModelAndView addOk(Model model,
        @RequestParam(value="name", defaultValue = "") String name,
        @RequestParam(value="userid", defaultValue = "") String userid,
        @RequestParam(value="position", defaultValue = "") String position,
        @RequestParam(value="sal", defaultValue = "0") int sal,
        @RequestParam(value="hiredate", defaultValue = "") String hiredate,
        @RequestParam(value="comm", defaultValue = "0") int comm,
        @RequestParam(value="deptno", defaultValue = "0") int deptno
        ){

            /** 1) 사용자가 입력한 파라미터 유효성 검사 */
            try {
                regexHelper.isValue(name, "교수 이름을 입력하세요.");
                regexHelper.isKor(name, "교수 이름은 한글만 가능합니다.");
                regexHelper.isValue(userid, "아이디를 입력하세요.");
                regexHelper.isEngNum(userid, "아이디는 영어와 숫자로만 입력 가능합니다.");
                regexHelper.isValue(position, "직급을 입력하세요.");
                regexHelper.isZero(sal, "급여를 입력하세요.");
                regexHelper.isUnder(sal, 0, "급여는 0보다 작을 수 없습니다.");
                regexHelper.isValue(hiredate, "입사일을 입력하세요.");
                regexHelper.isUnder(comm, 0, "보직수당은는 0보다 작을 수 없습니다.");
                regexHelper.isZero(deptno, "소속 학과 번호를 입력하세요.");
            } catch (StringFormatException e) {
                return webHelper.badRequest(e);
            }

            ProfessorModel input = new ProfessorModel();
            input.setName(name);
            input.setUserid(userid);
            input.setPosition(position);
            input.setSal(sal);
            input.setHiredate(hiredate);
            input.setComm(comm);
            input.setDeptno(deptno);

            try {
                professorService.insert(input);
            } catch (Exception e) {
                return webHelper.serverError(e);
            }

        return webHelper.redirect("/professor/read.do?profno=" + input.getProfno(), "저장되었습니다.");
    }   

    /** 수정 폼 페이지 */
    @GetMapping("/professor/edit.do")
    public ModelAndView edit(Model model,
        @RequestParam(value="profno", defaultValue = "0") int profno){
            /** 1) 파라미터 검사 */
            try {
                regexHelper.isZero(profno, "교수번호가 없습니다.");
            } catch (StringFormatException e) {
                return webHelper.badRequest(e);
            }        

            ProfessorModel input = new ProfessorModel();
            input.setProfno(profno);

            ProfessorModel output = null;

            List<DepartmentModel>departmentModels = null;

            try {
                output = professorService.selectItem(input);
                // 드롭다운을 위한 학과목록 조회
                departmentModels = departmentService.selectList(null);                
            } catch (Exception e) {
                return webHelper.serverError(e);
            }

            model.addAttribute("output", output);
            model.addAttribute("departmentModels", departmentModels);

        return new ModelAndView("professor/edit");
    }

    /** 수정 폼에 대한 action 페이지 */
    @PostMapping("/professor/edit_ok.do")
    public ModelAndView editOk(Model model,
        @RequestParam(value = "profno", defaultValue = "0") int profno,
        @RequestParam(value = "name", defaultValue = "") String name,
        @RequestParam(value = "userid", defaultValue = "") String userid,
        @RequestParam(value = "position", defaultValue = "") String position,
        @RequestParam(value = "sal", defaultValue = "0") int sal,
        @RequestParam(value = "hiredate", defaultValue = "") String hiredate,
        @RequestParam(value = "comm", defaultValue = "0") Integer comm,
        @RequestParam(value = "deptno", defaultValue = "0") int deptno) {

            /** 1) 사용자가 입력한 파라미터 유효성 검사 */
            try {
                regexHelper.isZero(profno, "교수번호가 없습니다.");
                regexHelper.isValue(name, "교수 이름을 입력하세요.");
                regexHelper.isKor(name, "교수 이름은 한글만 가능합니다.");
                regexHelper.isValue(userid, "아이디를 입력하세요.");
                regexHelper.isEngNum(userid, "아이디는 영어와 숫자로만 입력 가능합니다.");
                regexHelper.isValue(position, "직급을 입력하세요.");
                regexHelper.isZero(sal, "급여를 입력하세요.");
                regexHelper.isUnder(sal, 0, "급여는 0보다 작을 수 없습니다.");
                regexHelper.isValue(hiredate, "입사일을 입력하세요.");
                regexHelper.isUnder(comm, 0, "보직수당은는 0보다 작을 수 없습니다.");
                regexHelper.isZero(deptno, "소속 학과 번호를 입력하세요.");
            } catch (StringFormatException e) {
                return webHelper.badRequest(e);
            }

            ProfessorModel input = new ProfessorModel();
            input.setProfno(profno);
            input.setName(name);
            input.setUserid(userid);
            input.setPosition(position);
            input.setSal(sal);
            input.setHiredate(hiredate);
            input.setComm(comm);
            input.setDeptno(deptno);

            try {
                professorService.update(input);
            } catch (Exception e) {
                return webHelper.serverError(e);
            }

        return webHelper.redirect("/professor/read.do?profno=" + input.getProfno(), "수정되었습니다.");
    }

    /** 삭제 처리 페이지 */
    @GetMapping("/professor/delete.do")
    public ModelAndView delete(Model model,
        @RequestParam(value = "profno", defaultValue = "0") int profno) {

            /** 1) 파라미터 유효성 검사 */
            try {
                regexHelper.isZero(profno, "교수 번호가 없습니다.");
            } catch (StringFormatException e) {
                webHelper.badRequest(e);
            }        

            ProfessorModel input = new ProfessorModel();
            input.setProfno(profno);

            try {
                professorService.delete(input);
            } catch (Exception e) {
                return webHelper.serverError(e);
            }
            
        return webHelper.redirect("/professor/list.do", "삭제되었습니다.");
    }
}
