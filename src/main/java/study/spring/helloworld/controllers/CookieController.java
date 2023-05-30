package study.spring.helloworld.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;


@Controller
public class CookieController {
    /**
     * 쿠키 저장을 위한 작성 페이지
     */

    @GetMapping("/cookie/home.do")
     public String home(Model model,
            @CookieValue(value = "my_cookie", defaultValue = "") String myCookie){
                /**컨트롤러에서 쿠키를 식별하기 위한 처리 */

                try {
                    //평상시에 URLEncoding이 적용되었으므로 URLDecoding이 별도로 필요함
                    //-> import java.net.URLDecoder;
                    myCookie = URLDecoder.decode(myCookie, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                //추출한 값을 View에게 전달
                model.addAttribute("my_cookie", myCookie);

                //"/src/main/webapp/WEB-INF/views/cookie/write/jsp" 파일을 View로 지정한다.

                return "cookie/home"; 
            }

            /**
             * 쿠키를 저장하기 위한 action 페이지
             */
            @PostMapping("/cookie/save.do")
            public String save(HttpServletResponse response,
            @RequestParam(value = "user_input", defaultValue="")String userInput){
                /** 1) 파라미터를 쿠키에 저장하기 위한 URLEncoding 처리 */
                //쿠키 저장을 위해서는 URLEncoding 처리가 필요하다.
                if(!userInput.equals("")){
                    try {
                        userInput = URLEncoder.encode(userInput, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

                /** 2) 쿠키 저장하기 */
                //저장할 쿠키 객체 생성.
                Cookie cookie = new Cookie("my_cookie", userInput);

                //유효 경로 --> 사이트 전역에 대한 설정.
                cookie.setPath("/");

                //유효 도메인(로컬 개발시에는 설정할 필요 없음)
                //--> "www.naver.com" 인 경우 ".naver.com"으로 설정
                //cookie.setDomain("localhost");

                //유효시간 설정
                if(userInput.equals("")){
                    cookie.setMaxAge(0);    //쿠키 설정시간이 0이면 즉시 삭제된다.
                }
                else{
                    cookie.setMaxAge(60);   //값이 있다면 60초 동안 쿠키 저장
                }

                //쿠키 저장
                response.addCookie(cookie);

                /** 3) Spring 방식의 페이지 이동 */
                //Servlet의 response.sendRedirect(url)과 동일
                //-->"/부터 시작할 경우 ContextPath는 자동으로 앞에 추가된다."
                return "redirect:/cookie/home.do";
            }
    
}
