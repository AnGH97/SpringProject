package study.spring.helloworld.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import study.spring.helloworld.mappers.ProfessorMapper;
import study.spring.helloworld.mappers.StudentMapper;
import study.spring.helloworld.models.ProfessorModel;
import study.spring.helloworld.models.StudentModel;
import study.spring.helloworld.services.ProfessorService;

@Service
@RequiredArgsConstructor
public class ProfessorServiceImpl implements ProfessorService{
    private final StudentMapper studentMapper;
    private final ProfessorMapper professorMapper;

    @Override
    public ProfessorModel insert(ProfessorModel input) throws NullPointerException, Exception {
        // insert문 수행
        // 리턴되는 값은 저장된 데이터의 수
        // 생성된 PK는 파라미터로 전달된 parmas 객체의 적절한 멤버변수에 설정된다. --> getter를 통해 취득 가능함
        int rows = professorMapper.insert(input);

        // 저장된 데이터가 없다면?
        if (rows == 0) {
            // 객체가 없다는 내용의 에러를 강제 발생시킴
            throw new NullPointerException("저장된 데이터가 없습니다.");
        }
        return professorMapper.selectItem(input);
    }

    @Override
    public ProfessorModel update(ProfessorModel input) throws NullPointerException, Exception {

        // update문 수행 -> 리턴되는 값은 수정된 데이터의 수
        int rows = professorMapper.update(input);

        // 수정된 데이터가 없다면?
        if (rows == 0) {
            // 객체가 없다는 내용의 에러를 강제 발생시킴
            throw new NullPointerException("수정된 데이터가 없습니다.");
        }

        return professorMapper.selectItem(input);
    }

    @Override
    public void delete(ProfessorModel input) throws NullPointerException, Exception {
        // 학과 데이터 삭제를 위해 참조관계에 있는 자식 데이터를 순서대로 삭제
        StudentModel studentModel = new StudentModel();
        studentModel.setDeptno(input.getDeptno());
        studentModel.setProfno(null);
        studentMapper.update(studentModel);

        ProfessorModel professorModel = new ProfessorModel();
        professorModel.setDeptno(input.getDeptno());
        professorMapper.delete(professorModel);

        // delete문 수행 -> 리턴되는 값은 수정된 데이터의 수
        int rows = professorMapper.delete(input);

        // 삭제된 데이터가 없다면?
        if (rows == 0) {
            // 객체가 없다는 내용의 에러를 강제 발생시킴
            throw new NullPointerException("삭제된 데이터가 없습니다.");
        }
    }

    @Override
    public ProfessorModel selectItem(ProfessorModel input) throws NullPointerException, Exception {
        return professorMapper.selectItem(input);
    }

    @Override
    public List<ProfessorModel> selectList(ProfessorModel input) throws NullPointerException, Exception {
        return professorMapper.selectList(input);
    }

	@Override
	public int selectCount(ProfessorModel input) throws NullPointerException, Exception {
        return professorMapper.selectCount(input);
	}
}
