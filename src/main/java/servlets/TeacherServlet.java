package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Teacher;
import repository.TeacherRepository;
import repository.TeacherRepositoryImpl;

import java.io.IOException;

@WebServlet(name = "TeacherServlet", value = "/teacher")
public class TeacherServlet extends HttpServlet {

    private final TeacherRepository teacherRepository;

    public TeacherServlet() {
        this.teacherRepository = new TeacherRepositoryImpl();

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");

        Teacher newTeacher = new Teacher(firstName, lastName);

        teacherRepository.saveTeacher(newTeacher);

//        resp.setCharacterEncoding("UTF-8");
//        resp.setContentType("text/plain; charset=UTF-8");

        resp.getWriter().write("Новый препозователь зарегестрирован");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParameter = req.getParameter("id");

        try {
            long teacherId = Long.parseLong(idParameter);

            Teacher teacher = teacherRepository.getTeacherById(teacherId);

            if (teacher == null) {
                resp.getWriter().write("Учитель с указаным id: " + teacherId +" не найден");
                return;
            }

            resp.getWriter().write("Учитель найден: " + teacher.getFirstName() + ", " + teacher.getLastName());

        } catch (NumberFormatException e) {
            resp.getWriter().write("Недопустимый формат id");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParameter = req.getParameter("id");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");

        if (idParameter == null || firstName == null || lastName == null) {
            resp.getWriter().write("Необходимо указать id, firstName и lastName для обновления данных преподавателя.");
            return;
        }

        try {
            long teacherId = Long.parseLong(idParameter);

            Teacher teacherToUpdate = teacherRepository.getTeacherById(teacherId);

            if (teacherToUpdate == null) {
                resp.getWriter().write("Преподаватель с указанным id: " + teacherId + " не найден.");
                return;
            }

            teacherToUpdate.setFirstName(firstName);
            teacherToUpdate.setLastName(lastName);

            teacherRepository.updateTeacher(teacherToUpdate);

            resp.getWriter().write("Данные преподавателя с id: " + teacherId + " успешно обновлены.");

        } catch (NumberFormatException e) {
            resp.getWriter().write("Недопустимый формат id. Id должен быть числовым.");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParameter = req.getParameter("id");

        try {
            long teacherId = Long.parseLong(idParameter);

            Teacher teacherToDelete = teacherRepository.getTeacherById(teacherId);

            if (teacherToDelete == null) {
                resp.getWriter().write("Преподаватель с указанным id: " + teacherId + " не найден.");
                return;
            }

            teacherRepository.deleteTeacher(teacherToDelete);

            resp.getWriter().write("Преподаватель с id: " + teacherId + " успешно удален.");

        } catch (NumberFormatException e) {
            resp.getWriter().write("Недопустимый формат id. Id должен быть числовым.");
        }
    }
}
