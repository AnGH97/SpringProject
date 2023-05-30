package study.spring.helloworld.interceptors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@SpringBootApplication
public class InterceptorApplication {

	public static void main(String[] args) {
		SpringApplication.run(InterceptorApplication.class, args);
	}

	@GetMapping("/interceptor/hello")
	public String hello(HttpServletRequest request){
		return "interceptor/hello";
	}

	@GetMapping("/interceptor/index")
	public String index(HttpServletRequest request){
		return "interceptor/index";
		
	}

	@GetMapping("/interceptor/main")
	public String main(HttpServletRequest request){
		return "interceptor/main";
		
	}

	@GetMapping("/interceptor/word")
	public String word(){
		return "interceptor/word";
	}

}
