package study.spring.helloworld;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import study.spring.helloworld.interceptors.LogInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer{

    @Value("${upload.dir}")
    private String uploadDir;

    @Value("${upload.url}")
    private String uploadUrl;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //직접 정의한 MyInterceptor를 spring에 등록
        InterceptorRegistration ir = registry.addInterceptor(new LogInterceptor());
        //해당 경로는 인터셉터가 가로채지 않는다.
        ir.excludePathPatterns("/hello/*", "/word/*");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(String.format("%s/**", uploadUrl)).addResourceLocations(String.format("file://%s/", uploadDir));
    }
    
}
