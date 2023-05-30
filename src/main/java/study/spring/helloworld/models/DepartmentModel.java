package study.spring.helloworld.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//모든 멤버변수를 파라미터로 갖는 생성자 추가 옵션
@AllArgsConstructor
//기본 생성자 추가 옵션(AllArgsConstructor 사용시 기본 생성자를 추가하려면 필수)
@NoArgsConstructor
public class DepartmentModel {
    private int deptno;
    private String dname;
    private String loc;

    private static int offset;
    private static int listCount;

    public static int getOffset(){
        return offset;
    }

    public static void setOffset(int offset){
        DepartmentModel.offset = offset;
    }

    public static int getListCount(){
        return listCount;
    }

    public static void setListCount(int listCount){
        DepartmentModel.listCount = listCount;
    }


}
