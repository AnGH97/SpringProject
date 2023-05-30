package study.spring.helloworld.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import study.spring.helloworld.mappers.StudentMapper;
import study.spring.helloworld.models.StudentModel;
import study.spring.helloworld.services.StudentService;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService{
    private final StudentMapper studentMapper;

    @Override
    public StudentModel insert(StudentModel input) throws NullPointerException, Exception {
        // insert문 수행
        // 리턴되는 값은 저장된 데이터의 수
        // 생성된 PK는 파라미터로 전달된 parmas 객체의 적절한 멤버변수에 설정된다. --> getter를 통해 취득 가능함
        int rows = studentMapper.insert(input);

        // 저장된 데이터가 없다면?
        if (rows == 0) {
            // 객체가 없다는 내용의 에러를 강제 발생시킴
            throw new NullPointerException("저장된 데이터가 없습니다.");
        }
        return studentMapper.selectItem(input);
    }

    @Override
    public StudentModel update(StudentModel input) throws NullPointerException, Exception {

        // update문 수행 -> 리턴되는 값은 수정된 데이터의 수
        int rows = studentMapper.update(input);

        // 수정된 데이터가 없다면?
        if (rows == 0) {
            // 객체가 없다는 내용의 에러를 강제 발생시킴
            throw new NullPointerException("수정된 데이터가 없습니다.");
        }

        return studentMapper.selectItem(input);
    }

    @Override
    public void delete(StudentModel input) throws NullPointerException, Exception {
        // delete문 수행 -> 리턴되는 값은 수정된 데이터의 수
        int rows = studentMapper.delete(input);

        // 삭제된 데이터가 없다면?
        if (rows == 0) {
            // 객체가 없다는 내용의 에러를 강제 발생시킴
            throw new NullPointerException("삭제된 데이터가 없습니다.");
        }
    }

    @Override
    public StudentModel selectItem(StudentModel input) throws NullPointerException, Exception {
        return studentMapper.selectItem(input);
    }

    @Override
    public List<StudentModel> selectList(StudentModel input) throws NullPointerException, Exception {
        return studentMapper.selectList(input);
    }

    @Override
    public int selecCount(StudentModel input) throws NullPointerException, Exception {
        return studentMapper.selectCount(input);
    }

}
