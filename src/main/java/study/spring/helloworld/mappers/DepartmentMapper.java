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

import study.spring.helloworld.models.DepartmentModel;

//이 Interface가 MyBatis의 Mapper임을 선언
@Mapper
public interface DepartmentMapper {
    //INSERT문을 수행하는 메서드 정의
    @Insert("INSERT INTO department (dname, loc) VALUES (#{dname}, #{loc})")
    //INSERT문에서 필요한 PK에 대한 옵션 정의
    // --> userGeneratedKeys: AUTO_INCREMENT가 적용된 테이블인 경우 사용
    // --> keyProperty: 파라미터로 전달되는 DTO객체에서 PK에 대응되는 멤버변수
    // --> keyColumn: 테이블의 Primary Key 칼럼명
    @Options(useGeneratedKeys=true, keyProperty="deptno", keyColumn = "deptno")
    //INSERT, UPDATE, DELET용 메서드는 반드시 int형 리턴 --> 적용된 행의 수를 반환
    int insert(DepartmentModel input);

    //UPDATE문을 수행하는 메서드 정의
    @Update("UPDATE department SET dname=#{dname}, loc=#{loc} WHERE deptno=#{deptno}")
    int update(DepartmentModel input);

    //DELETE문을 수행하는 메서드 정의
    @Delete("DELETE FROM department WHERE deptno=#{deptno}")
    int delete(DepartmentModel input);

    //SELECT문(단일행 조회)을 수행하는 메서드 정의
    @Select("SELECT deptno, dname, loc FROM department WHERE deptno=#{deptno}")
    //조회 결과와 리턴할 DTO객체를 연결하기 위한 규칙 정의
    // --> property : DTO 객체의 멤버변수 이름
    // --> column : SELECT문에 명시된 필드 이름(AS 옵션을 사용할 경우 별칭으로 명시)
    @Results(id = "myResultId", value={
        @Result( property="deptno", column="deptno"),
        @Result(property="dname", column="dname" ),
        @Result(property="loc", column="loc")})
    DepartmentModel selectItem(DepartmentModel input);

    //SELECT문(다중행 조회)을 수행하는 메서드 정의
    @Select("<script>" + // <-- Dynamic SQL이 시작됨을 알림
        "SELECT deptno, dname, loc FROM department" + 
        "<where>" + // <-- 검색 조건 동적 구성 시작
        "<if test='dname != null'>dname LIKE concat('%', #{dname}, '%')</if>" + 
        "<if test='loc != null'>OR loc LIKE concat('%', #{loc}, '%')</if>" + 
        "</where>" + 
        "<if test='listCount > 0'>LIMIT #{offset}, #{listCount}</if>" +
        "</script>") // <-- Dynamic SQL이 종료됨을 알림
   @ResultMap("myResultId")
   List<DepartmentModel> selectList(DepartmentModel input);

   @Select("<script>" + // <-- Dynamic SQL이 시작됨을 알림
        "SELECT COUNT(*) AS cnt FROM department" + 
        "<where>" + // <-- 검색 조건 동적 구성 시작
        "<if test='dname != null'>dname LIKE concat('%', #{dname}, '%')</if>" + 
        "<if test='loc != null'>OR loc LIKE concat('%', #{loc}, '%')</if>" + 
        "</where>" + 
        "</script>") // <-- Dynamic SQL이 종료됨을 알림
    public int selectCount(DepartmentModel input);
}
