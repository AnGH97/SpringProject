package study.spring.helloworld.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import study.spring.helloworld.mappers.DepartmentMapper;
import study.spring.helloworld.models.DepartmentModel;

@Controller
@RequiredArgsConstructor
public class DepartmentControllerrr {
    private final DepartmentMapper departmentMapper;

    @ResponseBody
    @GetMapping("/home")
    public String home(){
        DepartmentModel input = new DepartmentModel();
        input.setDeptno(102);       
        List<DepartmentModel> output = departmentMapper.selectList(input);

        String result = "";
        for(DepartmentModel item : output){
            result += "<h3>" + item.toString() + "</h3>" + "<hr />";
        }

        return result;
    }
}
