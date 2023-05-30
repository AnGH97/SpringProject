package study.spring.helloworld.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorModel {
    private int profno; // 교수번호
    private String name; // 이름
    private String userid; // 아이디
    private String position; // 직급
    private int sal; // 급여
    private String hiredate; // 입사일
    private int comm; // 보직수당
    private int deptno; // 부서번호(학과번호)
    //-----여기는 Department 목록 -----
    private String dname;
    private String loc;
    
    private static int offset;
    private static int listCount;

    public static int getOffset(){
        return offset;
    }

    public static void setOffset(int offset){
        ProfessorModel.offset = offset;
    }

    public static int getListCount(){
        return listCount;
    }

    public static void setListCount(int listCount){
        ProfessorModel.listCount = listCount;
    }

}
