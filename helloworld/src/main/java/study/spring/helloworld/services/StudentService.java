package study.spring.helloworld.services;

import java.util.List;

import study.spring.helloworld.models.StudentModel;

/**
 * 학생 관리 기능과 관련된 MyBatis Mapper를 간접적으로 호출하기 위한 기능 명세.
 * 하나의 처리를 위해서 두 개 이상의 기능을 연동할 필요가 있을 경우,
 * 이 인터페이스의 구현체(Impl)을 통해서 처리한다.
 */
public interface StudentService {
    
    /**
     * 학생 정보를 새로 저장하고 저장된 정보를 조회하여 리턴한다.
     * 저장된 행의 수가 0이거나, SQL에 에러가 있는 경우는
     * 예외를 발생시켜서, 이 메서드를 호출하는 위치에서 try~catch
     * 구문을 강제적으로 사용하도록 throws를 명시한다.
     * 
     * @param params - 저장될 정보를 담고 있는 Beans
     * @return - 저장된 데이터
     * @throws NullPointerException - 저장된 데이터가 없는 경우
     * @throws Exception            - SQL처리에 실패한 경우
     */
    public StudentModel insert(StudentModel input) throws NullPointerException, Exception;

    /**
     * 학생 정보를 수정하고, 수정된 정보를 조회하여 리턴한다.
     * 수정 후 영향을 받은 행 수가 없거나, SQL에 에러가 있는 경우는
     * 예외를 발생시켜서, 이 메서드를 호출하는 위치에서 try~catch
     * 구문을 강제적으로 사용하도록 throws를 명시한다.
     * 
     * @param params - 수정될 정보를 담고 있는 Beans
     * @return - 수정된 데이터
     * @throws NullPointerException - 수정된 데이터가 없는 경우
     * @throws Exception            - SQL처리에 실패한 경우
     */
    public StudentModel update(StudentModel input) throws NullPointerException, Exception;

    /**
     * 학생 정보를 삭제한다.
     * 이 때, 학생에 소속되어 있는 학생과 학생도 일괄 삭제한다.
     * 삭제 후 Student 테이블에서 영향을 받은 행 수가 없거나,
     * SQL에 에러가 있는 경우는 예외를 발생시켜서
     * 이 메서드를 호출하는 위치에서 try~catch 구문을 강제적으로
     * 사용하도록 throws를 명시한다.
     * 
     * @param params - 삭제될 학생의 정보를 담고 있는 Beans
     * @return - Void
     * @throws NullPointerException - 삭제된 데이터가 없는 경우
     * @throws Exception            - SQL처리에 실패한 경우
     */
    public void delete(StudentModel input) throws NullPointerException, Exception;

    /**
     * 학생 하나의 정보를 조회하여 리턴한다.
     * 
     * @param params - 조회할 대상의 정보를 담고 있는 Beans
     * @return - 조회결과를 담고 있는 Beans
     * @throws NullPointerException - 조회된 데이터가 없는 경우
     * @throws Exception            - SQL처리에 실패한 경우
     */
    public StudentModel selectItem(StudentModel input) throws NullPointerException, Exception;

    /**
     * 학생 목록을 조회하여 리턴한다.
     * 
     * @param params - 검색이 필요한 경우 검색어를 저장하고 있는 beans
     * @return - 조회 결과를 담고 있는 컬렉션
     * @throws NullPointerException - 조회된 데이터가 없는 경우
     * @throws Exception            - SQL처리에 실패한 경우
     */
    public List<StudentModel> selectList(StudentModel input) throws NullPointerException, Exception;

    public int selecCount(StudentModel input) throws NullPointerException, Exception;
}
