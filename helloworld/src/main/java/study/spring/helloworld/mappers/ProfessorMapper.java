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

import study.spring.helloworld.models.ProfessorModel;

@Mapper
public interface ProfessorMapper {
    @Insert("INSERT INTO professor (name, userid, position, sal, hiredate, comm, deptno) VALUES (#{name}, #{userid}, #{position}, #{sal}, #{hiredate}, #{comm}, #{deptno})")
    @Options(useGeneratedKeys=true, keyProperty="profno", keyColumn ="profno")
    int insert(ProfessorModel input);

    @Update("UPDATE professor SET name=#{name}, userid=#{userid}, position=#{position}, sal=#{sal}, hiredate=#{hiredate}, comm=#{comm}, deptno=#{deptno} WHERE profno=#{profno}")
    int update(ProfessorModel input);

    @Delete("DELETE FROM professor WHERE profno=#{profno}")
    int delete(ProfessorModel input);

    @Delete("DELETE FROM professor WHERE deptno = #{deptno}")
    int deleteByDeptno(ProfessorModel input);

    @Select("SELECT "+
            "profno, name, userid, position, sal, DATE_FORMAT(hiredate,'%Y-%m-%d') AS hiredate, comm, p.deptno, dname, loc " +
            "FROM professor p " +
            "INNER JOIN department d ON p.deptno=d.deptno " +
            "WHERE profno=#{profno}")
    @Results(id = "myResultId", value = {
            @Result(property = "profno", column = "profno"),
            @Result(property = "name", column = "name"),
            @Result(property = "userid", column = "userid"),
            @Result(property = "position", column = "position"),
            @Result(property = "sal", column = "sal"),
            @Result(property = "hiredate", column = "hiredate"),
            @Result(property = "comm", column = "comm"),
            @Result(property = "deptno", column = "deptno"),
            @Result(property = "dname", column = "dname"),
            @Result(property = "loc", column = "loc")})
    ProfessorModel selectItem(ProfessorModel input);
    
    @Select("<script>" + 
            "SELECT profno, name, userid, position, sal, DATE_FORMAT(hiredate,'%Y-%m-%d') AS hiredate, comm, p.deptno, dname, loc " +
            "FROM professor p " +
            "INNER JOIN department d ON p.deptno = d.deptno" +
            "<where>" +
            "<if test='name != null'>name LIKE concat('%', #{name}, '%')</if>" + 
            "</where>" +
            "<if test='listCount > 0'>LIMIT #{offset}, #{listCount}</if>" +
            "</script>")
    @ResultMap("myResultId")
    List<ProfessorModel> selectList(ProfessorModel input);

    @Select("<script>" + 
            "SELECT COUNT(*) AS cnt FROM professor" + 
            "<where>" +
            "<if test='name != null'>name LIKE concat('%', #{name}, '%')</if>" + 
            "</where>" +
            "</script>")
    public int selectCount(ProfessorModel input);
}
