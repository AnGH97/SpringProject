package study.spring.helloworld.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentProfessorDepartmentModel {
    private String name; // 이름
    private String userid; // 아이디
    private int grade; // 학년
    private String idnum; // 주민번호
    private String birthdate; // 생년월일
    private String tel; // 전화번호
    private int height; // 키
    private int weight; // 몸무게
    private int deptno; // 학과번호
    private int profno; // 담당교수의 일련번호
    private int studno; // 학생번호

    private String pname;   //담당교수 이름
    private String dname;   // 소속학과 이름

    
}
