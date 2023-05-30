package study.spring.helloworld.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class SessionController {
    /**
     *  세션 저장을 위한 작성 페이지
     */

     @GetMapping("/session/home.do")
     public String home(Model model, HttpServletRequest request){
        /**컨트롤러에서 세션을 식별하기 위한 처리 */
        // 세션값은 request 내장객체를 통해서 HttpSession객체를 생성해야 접근할 수 있다.
        // --> import jakarta.servlet.http.HttpSession;

        HttpSession session = request.getSession();
        String mySession = (String) session.getAttribute("my_session_value");
        if(mySession == null){
            mySession="";
        }

        //추출한 값을 View에게 전달
        model.addAttribute("my_session", mySession);

        return "session/home";
     }

     /**
      * 세션을 저장하기 위한 action 페이지
      */
      @PostMapping("/session/save.do")
    public String save(HttpServletRequest request, @RequestParam(value="user_input", defaultValue = "")String userInput){
        /** 1) request 객체를 사용해서 세션 객체 만들기 */
        // --> impot javax.servlet.http.HttpSession;
        HttpSession session = request.getSession();

        /** 2) 세션 저장, 삭제 */
        // 생성된 세션 객체를 사용하는 방법은 JSP와 동일하다.
        if(!userInput.equals("")){
            //입력 내용이 있다면 세션 저장 처리
            session.setAttribute("my_session_value", userInput);
        }
        else{
            session.removeAttribute("my_session_value");
        }

        /** 3) Spring 방식의 페이지 이동 */
        //Servlet의 response.sendRedirect(url)과 동일
        // --> "/"부터 시작할 경우 ContextPath는 자동으로 앞에 추가된다.
        return "redirect:/session/home.do";
    }
    
}
