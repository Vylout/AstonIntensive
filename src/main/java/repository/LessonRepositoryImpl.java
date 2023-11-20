package repository;

import model.Lesson;
import model.Teacher;
import util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LessonRepositoryImpl implements LessonRepository {

    private static final String JDBC_URL = "jdbc:postgresql://localhost:5432/aston";
    private static final String USERNAME = "user";
    private static final String PASSWORD = "password";

    private static Connection connection;

    @Override
    public void saveLesson(Lesson lesson) {
        String sql = "INSERT INTO lessons (name) VALUES (?)";

        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, lesson.getName());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Lesson getLessonById(Long lessonId) {
        String sql = "SELECT * FROM lessons WHERE id = ?";
        Lesson lesson = null;

        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, lessonId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    String name = resultSet.getString("name");

                    lesson = new Lesson(id, name);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lesson;
    }

    @Override
    public void updateLesson(Lesson lesson) {
        String sql = "UPDATE lessons SET name = ? WHERE id = ?";

        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, lesson.getName());
            preparedStatement.setLong(2, lesson.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteLesson(Lesson lesson) {
        String sql = "DELETE FROM lessons WHERE id = ?";

        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, lesson.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveLessonTeacher(long lessonId, long teacherId) {
        String sql = "INSERT INTO teacher_lessons (teacher_id, lesson_id) VALUES (?, ?)";

        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, teacherId);
            preparedStatement.setLong(2, lessonId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Teacher> getAllTeacherByLessonId(Long lessonId) {
        String sql = "SELECT t.* FROM teachers t INNER JOIN teacher_lessons tl ON t.id = tl.teacher_id WHERE tl.lesson_id = ?";

        List<Teacher> teachers = new ArrayList<>();

        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, lessonId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Teacher teacher = new Teacher();
                    teacher.setFirstName(resultSet.getString("first_name"));
                    teacher.setLastName(resultSet.getString("last_name"));

                    teachers.add(teacher);
                }

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return teachers;
    }

    @Override
    public void updateLessonTeacher(long lessonId, long teacherId) {
        String sql = "UPDATE teacher_lessons SET teacher_id = ? WHERE lesson_id = ?";

        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, teacherId);
            preparedStatement.setLong(2, lessonId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteLessonTeacher(long lessonId, long teacherId) {
        String sql = "DELETE FROM teacher_lessons WHERE teacher_id = ? AND lesson_id = ?";

        try (var connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, teacherId);
            preparedStatement.setLong(2, lessonId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
