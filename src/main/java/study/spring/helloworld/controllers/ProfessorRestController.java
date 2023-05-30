package study.spring.helloworld.controllers;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import study.spring.helloworld.exceptions.StringFormatException;
import study.spring.helloworld.helpers.Pagenation;
import study.spring.helloworld.helpers.RegexHelper;
import study.spring.helloworld.helpers.RestHelper;
import study.spring.helloworld.models.ProfessorModel;
import study.spring.helloworld.services.ProfessorService;

@RestController
@RequiredArgsConstructor
public class ProfessorRestController {
    /** 비지니스 로직을 구현하는 Service 객체 */
    private final ProfessorService professorService;

    /**WbeHelper 객체 */
    private final RestHelper restHelper;

    /**정규표현식 객체 */
    private final RegexHelper regexHelper;

    /** API */
    @GetMapping("/api/professor")
    public Map<String, Object> list( 
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
                return restHelper.serverError(e);
            }

            Map<String, Object> map = new LinkedHashMap<String, Object>();

            map.put("meta", pagenation);
            map.put("data", output);
     
            return restHelper.sendResult(map);            

    }

    /**상세 API */
    @GetMapping("/api/professor/{profno}")
    public Map<String, Object> read(
        @PathVariable(value="profno") int profno){

            /** 1) 파라미터 검사 */
            try {
                regexHelper.isZero(profno, "교수번호가 없습니다.");
            } catch (StringFormatException e) {
                return restHelper.badRequest(e);
            }
            /** 2) 데이터 조회하기 */
            // 데이터 조회에 필요한 조건값을 Beans에 저장하기                  
            ProfessorModel input = new ProfessorModel();
            input.setProfno(profno);

            ProfessorModel output = null;

            try {
                output = professorService.selectItem(input);
            }catch (Exception e) {
                return restHelper.serverError(e);
            }

            //결과 처리
            Map<String, Object> map = new LinkedHashMap<String, Object>();
            map.put("data", output);

        return restHelper.sendResult(map);
    }

    /** 작성 폼에 대한 action API */
    @PostMapping("/api/professor")
    public Map<String, Object> addOk(
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
                regexHelper.isValue(userid, "아이디를 입력하세요.");
                regexHelper.isEngNum(userid, "아이디는 영어와 숫자로만 입력 가능합니다.");
                regexHelper.isValue(position, "직급을 입력하세요.");
                regexHelper.isZero(sal, "급여를 입력하세요.");
                regexHelper.isUnder(sal, 0, "급여는 0보다 작을 수 없습니다.");
                regexHelper.isValue(hiredate, "입사일을 입력하세요.");
                regexHelper.isUnder(comm, 0, "보직수당은는 0보다 작을 수 없습니다.");
                regexHelper.isZero(deptno, "소속 학과 번호를 입력하세요.");
            } catch (StringFormatException e) {
                return restHelper.badRequest(e);
            }

            ProfessorModel input = new ProfessorModel();
            input.setName(name);
            input.setUserid(userid);
            input.setPosition(position);
            input.setSal(sal);
            input.setHiredate(hiredate);
            input.setComm(comm);
            input.setDeptno(deptno);

            ProfessorModel output = null;

            try {
                output = professorService.insert(input);
            } catch (Exception e) {
                return restHelper.serverError(e);
            }
                //결과 처리
                Map<String, Object> map = new LinkedHashMap<String, Object>();

                map.put("data", output);

                return restHelper.sendResult(map);
    }   

    /** 수정 폼에 대한 action 페이지 */
    @PutMapping("/api/professor/{profno}")
    public Map<String, Object> editOk(
        @PathVariable(value = "profno") int profno,
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
                regexHelper.isValue(userid, "아이디를 입력하세요.");
                regexHelper.isEngNum(userid, "아이디는 영어와 숫자로만 입력 가능합니다.");
                regexHelper.isValue(position, "직급을 입력하세요.");
                regexHelper.isZero(sal, "급여를 입력하세요.");
                regexHelper.isUnder(sal, 0, "급여는 0보다 작을 수 없습니다.");
                regexHelper.isValue(hiredate, "입사일을 입력하세요.");
                regexHelper.isUnder(comm, 0, "보직수당은는 0보다 작을 수 없습니다.");
                regexHelper.isZero(deptno, "소속 학과 번호를 입력하세요.");
            } catch (StringFormatException e) {
                return restHelper.badRequest(e);
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

            ProfessorModel output = null;

            try {
                output = professorService.update(input);
            } catch (Exception e) {
                return restHelper.serverError(e);
            }

            //결과 처리
            Map<String, Object> map = new LinkedHashMap<String, Object>();
            map.put("data", output);

            return restHelper.sendResult(map);
    }

    /** 삭제 처리 api */
    @DeleteMapping("/api/professor/{profno}")
    public Map<String, Object> delete(
        @PathVariable(value = "profno") int profno) {

            /** 1) 파라미터 유효성 검사 */
            try {
                regexHelper.isZero(profno, "교수 번호가 없습니다.");
            } catch (StringFormatException e) {
                restHelper.badRequest(e);
            }        

            ProfessorModel input = new ProfessorModel();
            input.setProfno(profno);

            try {
                professorService.delete(input);
            } catch (Exception e) {
                return restHelper.serverError(e);
            }
            
            return restHelper.sendResult();
    }
}
