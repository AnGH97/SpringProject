package study.spring.helloworld.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import study.spring.helloworld.models.StudentModel;

@Mapper
public interface StudentMapper {
    @Insert("INSERT INTO student (name, userid, grade, idnum, birthdate, tel, height, weight, deptno, profno) VALUES (#{name}, #{userid}, #{grade}, #{idnum}, #{birthdate}, #{tel}, #{height}, #{weight}, #{deptno}, #{profno})")
    @Options(useGeneratedKeys=true, keyProperty="studno", keyColumn = "studno")
    int insert(StudentModel input);

    @Update("UPDATE student SET name=#{name}, userid=#{userid}, grade=#{grade}, idnum=#{idnum}, birthdate=#{birthdate}, tel=#{tel}, height=#{height}, weight=#{weight}, deptno=#{deptno}, profno=#{profno} WHERE studno=#{studno}")
    int update(StudentModel input);

    @Delete("DELETE FROM student WHERE studno=#{studno}")
    int delete(StudentModel input);

    @Select("SELECT name, userid, grade, idnum, birthdate, tel, height, weight, deptno, profno FROM student WHERE studno=#{studno}")
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
		@Result(property="profno", column="profno")})
    StudentModel selectItem(StudentModel input);

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
    @ResultMap("myResultId")
    List<StudentModel> selectList(StudentModel input);

    @Select("<script>" + 
    "SELECT COUNT(*) AS cnt FROM student" + 
    "<where>" +
    "<if test='name != null'>name LIKE concat('%', #{name}, '%')</if>" + 
    "</where>" +
    "</script>")
public int selectCount(StudentModel input);
}
