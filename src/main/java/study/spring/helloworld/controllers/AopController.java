package study.spring.helloworld.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import study.spring.helloworld.services.MyCalcService;

@Controller
@RequiredArgsConstructor
public class AopController {
    private final MyCalcService myCalcService;

    // '/home' 요청이 들어오면 실행되는 메서드
    
    @GetMapping("/aop/home")
    public String home(Model model){
        int value1 = myCalcService.plus(100, 200);
        
        int value2 = myCalcService.minus(200, 300);

        model.addAttribute("value1", value1);
        model.addAttribute("value2", value2);

        return "aop/home";
    }
    
}
