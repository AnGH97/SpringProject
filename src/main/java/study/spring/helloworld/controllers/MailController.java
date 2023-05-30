package study.spring.helloworld.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import study.spring.helloworld.helpers.MailHelper;
import study.spring.helloworld.helpers.RegexHelper;
import study.spring.helloworld.helpers.WebHelper;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MailController {

    private final RegexHelper regexHelper;
    private final WebHelper webHelper;
    private final MailHelper mailHelper;

    @GetMapping("/sendmail")
    public String home(){
        return "sendmail/home";
    }

    @PostMapping("/sendmail/send.do")
    public ModelAndView send(Model model,
        @RequestParam(defaultValue = "") String name,
        @RequestParam(defaultValue = "") String email,
        @RequestParam(defaultValue = "") String subject,
        @RequestParam(defaultValue = "") String content){
            /** 입력여부 검사 후, 입력되지 않은 경우 이전 페이지로 보내기 */
            
            try{
                regexHelper.isValue(email, "이메일 주소를 입력하세요.");
                regexHelper.isEmail(email, "이메일 주소가 잘못되엇습니다.");
                regexHelper.isValue(subject, "제목을 입력하세요.");
                regexHelper.isValue(content, "내용을 입력하세요.");
            }catch(Exception e){
                return webHelper.badRequest(e);
            }
            /** 메일 발송 처리 */

            try {
                //1) 이메일, 제목 내용
                mailHelper.sendMail(email, subject, content);
                //2) 이름, 이메일, 제목, 내용
                //mailHelper.sendMail(name, email, subject, content);
            } catch (Exception e) {
                e.printStackTrace();
                return webHelper.redirect(null, "메일 발송에 실패했습니다.");
            }

            return webHelper.redirect("/sendmail", "메일 발송에 성공했습니다.");

        }
    
}
