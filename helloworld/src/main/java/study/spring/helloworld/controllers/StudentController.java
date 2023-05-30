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
import study.spring.helloworld.models.StudentModel;
import study.spring.helloworld.services.DepartmentService;
import study.spring.helloworld.services.ProfessorService;
import study.spring.helloworld.services.StudentService;

@Controller
@RequiredArgsConstructor
public class StudentController {
    /** 비지니스 로직을 구현하는 Service 객체 */
    private final StudentService studentService;
    private final ProfessorService professorService;
    private final DepartmentService departmentService;

    /**WbeHelper 객체 */
    private final WebHelper webHelper;

    /**정규표현식 객체 */
    private final RegexHelper regexHelper;

    /** 목록 페이지 */
    @GetMapping({"/student", "/student/list.do"})
    public ModelAndView list(Model model/*, 검색어 */){
        return null;
    }

    /**상세 페이지 */
    @GetMapping("/student/read.do")
    public ModelAndView read(Model model,
        @RequestParam(value = "keyword", required = false)String keyword,
        @RequestParam(value="page", defaultValue = "1")int nowPage){
            int totalCount = 0; //전체 게시글 수
            int listCount = 15; //한 페이지당 표시할 목록수
            int pageCount = 5;  //한 그룹당 표시할 페이지 번호 수

            StudentModel input = new StudentModel();
            input.setName(keyword);

            List<StudentModel> output = null;

            Pagenation pagenation = null;
            
        return new ModelAndView("student/list");
    }

    /** 작성 폼 페이지 */
    @GetMapping("/student/add.do")
    public ModelAndView add(Model model){
        return new ModelAndView("student/add");
    }

    /** 작성 폼에 대한 action 페이지 */
    @PostMapping("/student/add_ok.do")
    public ModelAndView addOk(Model model){
        return webHelper.redirect("/student/read.do", "저장되었습니다.");
    }   

    /** 수정 폼 페이지 */
    @GetMapping("/student/edit.do")
    public ModelAndView edit(Model model){
        return new ModelAndView("student/edit");
    }

    /** 수정 폼에 대한 action 페이지 */
    @PostMapping("/student/edit_ok.do")
    public ModelAndView editOk(Model model){
        return webHelper.redirect("/student/read.do", "수정되었습니다.");
    }

    /** 삭제 처리 페이지 */
    @GetMapping("/student/delete.do")
    public ModelAndView delete(Model model){
        return webHelper.redirect("/student/list.do", "삭제되었습니다.");
    }
}
