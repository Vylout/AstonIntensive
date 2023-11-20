package repository;

import model.Lesson;
import model.Teacher;

import java.util.List;

public interface LessonRepository {
    void saveLesson(Lesson lesson);

    Lesson getLessonById(Long lessonId);

    void updateLesson(Lesson lesson);

    void deleteLesson(Lesson lesson);

    void saveLessonTeacher(long lessonId, long teacherId);

    List<Teacher> getAllTeacherByLessonId(Long lessonId);

    void updateLessonTeacher(long lessonId, long teacherId);

    void deleteLessonTeacher(long lessonId, long teacherId);
}
