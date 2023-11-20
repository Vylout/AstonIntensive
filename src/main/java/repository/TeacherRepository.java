package repository;

import model.Teacher;

public interface TeacherRepository {
    void saveTeacher(Teacher teacher);

    Teacher getTeacherById(Long teacherId);

    void updateTeacher(Teacher teacher);

    void deleteTeacher(Teacher teacher);
}
