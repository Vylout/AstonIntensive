package repository;

import model.Teacher;
import util.ConnectionManager;

import java.sql.*;

public class TeacherRepositoryImpl implements TeacherRepository {

    public void saveTeacher(Teacher teacher) {
        String sql = "INSERT INTO teachers (first_name, last_name) VALUES (?, ?)";

        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, teacher.getFirstName());
            preparedStatement.setString(2, teacher.getLastName());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Teacher getTeacherById(Long teacherId) {
        String sql = "SELECT * FROM teachers WHERE id = ?";
        Teacher teacher = null;

        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, teacherId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");

                    teacher = new Teacher(id, firstName, lastName);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return teacher;
    }

    public void updateTeacher(Teacher teacher) {
        String sql = "UPDATE teachers SET first_name = ?, last_name = ? WHERE id = ?";

        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, teacher.getFirstName());
            preparedStatement.setString(2, teacher.getLastName());
            preparedStatement.setLong(3, teacher.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void deleteTeacher(Teacher teacher) {
        String sql = "DELETE FROM teachers WHERE id = ?";

        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, teacher.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
