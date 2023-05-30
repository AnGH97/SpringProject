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
import study.spring.helloworld.services.DepartmentService;

@Controller
@RequiredArgsConstructor
public class DepartmentController {
    /** 비지니스 로직을 구현하는 Service 객체 */
    private final DepartmentService departmentService;

    /**WbeHelper 객체 */
    private final WebHelper webHelper;

    /**정규표현식 객체 */
    private final RegexHelper regexHelper;

    /** 목록 페이지 */
    @GetMapping({"/department", "/department/list.do"})
    public ModelAndView list(Model model,
                    //검색어
                    @RequestParam(value="keyword", required=false) String keyword,
                    @RequestParam(value="search", defaultValue = "") String search,
                    //페이지 구현에서 사용할 현재 페이지 번호
                    @RequestParam(value="page", defaultValue = "1") int nowPage){
        
        int totalCount = 0; //전체 게시글 수
        int listCount = 10; //한 페이지당 표시할 목록 수
        int pageCount = 5;  //한 그룹당 표시할 페이지 번호 수

        List<DepartmentModel> output = null; //조회 결과가 저장될 객체
        Pagenation pagenation = null;   //페이지 번호를 계산한 결과가 저장될 객체

        //조회에 필요한 조건값(검색어)를 Beans에 담는다.
        DepartmentModel input = new DepartmentModel();
        
        if(search.equals("dname")){
            input.setDname(keyword);
        }
        else if(search.equals("loc")){
            input.setLoc(keyword);
        }
        else{
            input.setDname(keyword);
            input.setLoc(keyword);
        }
        
        try {
            //전체 게시글 수 조회
            totalCount = departmentService.selectCount(input);
            //페이지 번호 계산 --> 계산 결과를 로그로 출력될 것이다.
            pagenation = new Pagenation(nowPage, totalCount, listCount, pageCount);

            //SQL의 LIMIT절에서 사용될 값을 Beans의 static 변수에 저장
            DepartmentModel.setOffset(pagenation.getOffset());
            DepartmentModel.setListCount(pagenation.getListCount());
            //데이터 조회하기
            output = departmentService.selectList(input);
        } catch (Exception e) {
            return webHelper.serverError(e);
        } 

        //view 처리
        model.addAttribute("output", output);
        model.addAttribute("keyword", keyword);
        model.addAttribute("pagenation", pagenation);
        model.addAttribute("search", search);
 
        return new ModelAndView("department/list");
    }

    /**상세 페이지 */
    @GetMapping("/department/read.do")
    public ModelAndView read(Model model,
            @RequestParam(value="deptno", defaultValue = "0") int deptno){
                //데이터 조회에 필요한 조건값을 Beans에 저장하기
                DepartmentModel input = new DepartmentModel();
                input.setDeptno(deptno);

                //조회 결과를 저장할 객체 선언
                DepartmentModel output = null;

                try {
                    output = departmentService.selectItem(input);
                } catch (Exception e) {
                    return webHelper.serverError(e);
                }

                //view 처리
                model.addAttribute("output", output);

        return new ModelAndView("department/read");
    }

    /** 작성 폼 페이지 */
    @GetMapping("/department/add.do")
    public ModelAndView add(Model model){
        return new ModelAndView("department/add");
    }

    /** 작성 폼에 대한 action 페이지 */
    @PostMapping("/department/add_ok.do")
    public ModelAndView addOk(Model model,
            @RequestParam(value="dname") String dname,
            @RequestParam(value="loc") String loc){

                try {
                    regexHelper.isValue(dname, "학과이름을 입력하세요.");
                    regexHelper.isKor(dname, "학과이름은 한글만 입력 가능합니다.");
                } catch (StringFormatException e) {
                    return webHelper.badRequest(e);
                }

                //저장할 값들을 Beans에 담는다.
                DepartmentModel input = new DepartmentModel();
                input.setDname(dname);
                input.setLoc(loc);


                try {
                    departmentService.insert(input);
                }  catch (Exception e) {
                    return webHelper.serverError(e);
                }

            return webHelper.redirect("/department/read.do?deptno=" + input.getDeptno(), "저장되었습니다.");
    }   

    /** 수정 폼 페이지 */
    @GetMapping("/department/edit.do")
    public ModelAndView edit(Model model,
            @RequestParam(value="deptno") int deptno){
            //데이터 조회에 필요한 조건값을 Beans에 저장하기
            DepartmentModel input = new DepartmentModel();
            input.setDeptno(deptno);

            //수정할 데이터의 원본 조회하기
            DepartmentModel output = null;

            try {
                output = departmentService.selectItem(input);
            } catch (Exception e) {
                return webHelper.serverError(e);
            }

            //view 처리
            model.addAttribute("output", output);

        return new ModelAndView("department/edit");
    }

    /** 수정 폼에 대한 action 페이지 */
    @PostMapping("/department/edit_ok.do")
    public ModelAndView editOk(Model model,
            @RequestParam(value="deptno") int deptno,
            @RequestParam(value="dname") String dname,
            @RequestParam(value="loc") String loc){

                try {
                    regexHelper.isValue(dname, "학과이름을 입력하세요.");
                    regexHelper.isKor(dname, "학과이름은 한글만 입력 가능합니다.");
                } catch (StringFormatException e) {
                    return webHelper.badRequest(e);
                }
        
                //수정할 값들을 Beans에 담는다.
                DepartmentModel input = new DepartmentModel();
                input.setDeptno(deptno);
                input.setDname(dname);
                input.setLoc(loc);

                try {
                    departmentService.update(input);
                } catch (Exception e) {
                    return webHelper.serverError(e);
                }

                return webHelper.redirect("/department/read.do?deptno=" + input.getDeptno(), "수정되었습니다.");
    }

    /** 삭제 처리 페이지 */
    @GetMapping("/department/delete.do")
    public ModelAndView delete(Model model,
            @RequestParam(value="deptno") int deptno){
                //데이터 삭제에 필요한 조건값을 Beans에 저장하기
                DepartmentModel input = new DepartmentModel();
                input.setDeptno(deptno);

                try {
                    departmentService.delete(input);
                } catch (Exception e) {
                    return webHelper.serverError(e);
                }
                return webHelper.redirect("/department/list.do", "삭제되었습니다.");
    }
}
