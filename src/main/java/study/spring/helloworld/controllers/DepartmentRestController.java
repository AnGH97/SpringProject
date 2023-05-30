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
import study.spring.helloworld.models.DepartmentModel;
import study.spring.helloworld.services.DepartmentService;

@RestController
@RequiredArgsConstructor
public class DepartmentRestController {
    /** 비지니스 로직을 구현하는 Service 객체 */
    private final DepartmentService departmentService;

    /**WbeHelper 객체 */
    private final RestHelper restHelper;

    /**정규표현식 객체 */
    private final RegexHelper regexHelper;

    /** API */
    @GetMapping("/api/department")
    public Map<String, Object> list(
                    //검색어
                    @RequestParam(value="keyword", required=false) String keyword,
                    //페이지 구현에서 사용할 현재 페이지 번호
                    @RequestParam(value="page", defaultValue = "1") int nowPage){
        
        int totalCount = 0; //전체 게시글 수
        int listCount = 10; //한 페이지당 표시할 목록 수
        int pageCount = 5;  //한 그룹당 표시할 페이지 번호 수

        List<DepartmentModel> output = null; //조회 결과가 저장될 객체
        Pagenation pagenation = null;   //페이지 번호를 계산한 결과가 저장될 객체

        //조회에 필요한 조건값(검색어)를 Beans에 담는다.
        DepartmentModel input = new DepartmentModel();
        input.setDname(keyword);
        input.setLoc(keyword);

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
            return restHelper.serverError(e);
        } 

        Map<String, Object> map = new LinkedHashMap<String, Object>();

        map.put("meta", pagenation);
        map.put("data", output);
 
        return restHelper.sendResult(map);
    }

    /**상세 API */
    @GetMapping("/api/department/{deptno}")
    public Map<String, Object> read(
            @PathVariable(value="deptno") int deptno){
                //데이터 조회에 필요한 조건값을 Beans에 저장하기
                DepartmentModel input = new DepartmentModel();
                input.setDeptno(deptno);

                //조회 결과를 저장할 객체 선언
                DepartmentModel output = null;

                try {
                    output = departmentService.selectItem(input);
                } catch (Exception e) {
                    return restHelper.serverError(e);
                }

                //결과 처리
                Map<String, Object> map = new LinkedHashMap<String, Object>();
                map.put("data", output);

        return restHelper.sendResult(map);
    }

    /** 작성 폼에 대한 action API*/
    @PostMapping("/api/department")
    public Map<String, Object> addOk(
            @RequestParam(value="dname") String dname,
            @RequestParam(value="loc") String loc){

                try {
                    regexHelper.isValue(dname, "학과이름을 입력하세요.");
                } catch (StringFormatException e) {
                    return restHelper.badRequest(e);
                }

                //저장할 값들을 Beans에 담는다.
                DepartmentModel input = new DepartmentModel();
                input.setDname(dname);
                input.setLoc(loc);

                //저장된 결과를 저회하기 위한 객체
                DepartmentModel output = null;


                try {
                    //데이터 저장 --> 데이터 저장에 성공하면 파라미터로 전달하는 input객체에 PK값이 저장된다.
                    output = departmentService.insert(input);
                }  catch (Exception e) {
                    return restHelper.serverError(e);
                }
                
                //결과 처리
                Map<String, Object> map = new LinkedHashMap<String, Object>();

                map.put("data", output);

                return restHelper.sendResult(map);
    }   

    /** 수정 폼에 대한 action API*/
    @PutMapping("/api/department/{deptno}")
    public Map<String, Object> editOk(
            @PathVariable(value="deptno") int deptno,
            @RequestParam(value="dname") String dname,
            @RequestParam(value="loc") String loc){

                try {
                    regexHelper.isValue(dname, "학과이름을 입력하세요.");
                } catch (StringFormatException e) {
                    return restHelper.badRequest(e);
                }
        
                //수정할 값들을 Beans에 담는다.
                DepartmentModel input = new DepartmentModel();
                input.setDeptno(deptno);
                input.setDname(dname);
                input.setLoc(loc);

                //수정된 결과를 저장할 객체
                DepartmentModel output = null;

                try {
                    output = departmentService.update(input);
                } catch (Exception e) {
                    return restHelper.serverError(e);
                }

                //결과 처리
                Map<String, Object> map = new LinkedHashMap<String, Object>();
                map.put("data", output);

                return restHelper.sendResult(map);
    }

    /** 삭제 처리 API */
    @DeleteMapping("/api/department/{deptno}")
    public Map<String, Object> delete(
            @PathVariable(value="deptno") int deptno){
                //데이터 삭제에 필요한 조건값을 Beans에 저장하기
                DepartmentModel input = new DepartmentModel();
                input.setDeptno(deptno);

                try {
                    departmentService.delete(input);
                } catch (Exception e) {
                    return restHelper.serverError(e);
                }

                return restHelper.sendResult();
    }
}
