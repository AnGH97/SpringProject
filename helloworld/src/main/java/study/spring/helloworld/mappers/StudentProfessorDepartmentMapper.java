package study.spring.helloworld.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import study.spring.helloworld.models.StudentProfessorDepartmentModel;

@Mapper
public interface StudentProfessorDepartmentMapper {
    @Select("<script>"
        +"SELECT studno, s.name, s.userid, s.grade, s.idnum, s.birthdate, s.tel, s.height, s.weight, s.deptno, s.profno, p.name AS pname, d.dname AS dname "
        +"FROM student AS s "
        +"INNER JOIN professor AS p "
        +"ON s.profno = p.profno "
        +"INNER JOIN department AS d "
        +"ON s.deptno = d.deptno"
        +"<where>"
        +"<if test='name != null'>s.name LIKE concat('%', #{name}, '%')</if>"
        +"</where>"
        +"</script>")

    @Results(id="myResultId", value = {
        @Result(property="studno", column="studno"),
        @Result(property="name", column="name"),
        @Result(property="userid", column="userid"),
        @Result(property="grade", column="grade"),
        @Result(property="idnum", column="idnum"),
        @Result(property="birthdate", column="birthdate"),
        @Result(property="tel", column="tel"),
        @Result(property="height", column="height"),
        @Result(property="weight", column="weight"),
        @Result(property="deptno", column="deptno"),
        @Result(property="profno", column="profno"),
        @Result(property="pname", column="pname"),
        @Result(property="dname", column="dname")})
    
    List<StudentProfessorDepartmentModel> selectList(StudentProfessorDepartmentModel input);
}
