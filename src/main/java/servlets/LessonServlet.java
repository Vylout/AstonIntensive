package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Lesson;
import repository.LessonRepository;
import repository.LessonRepositoryImpl;

import java.io.IOException;

@WebServlet(name = "LessonServlet", value = "/lesson")
public class LessonServlet extends HttpServlet {

    private final LessonRepository lessonRepository;

    public LessonServlet() {
        this.lessonRepository = new LessonRepositoryImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");

        Lesson newLesson = new Lesson(name);

        lessonRepository.saveLesson(newLesson);

        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/plain; charset=UTF-8");

        resp.getWriter().write("Новый урок зарегестрирован");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParameter = req.getParameter("id");

        try {
            long lessonId = Long.parseLong(idParameter);

            Lesson lesson = lessonRepository.getLessonById(lessonId);

            if (lesson == null) {
                resp.getWriter().write("Урок с указаным id: " + lessonId +" не найден");
                return;
            }

            resp.getWriter().write("Урок найден: " + lesson.getName());

        } catch (NumberFormatException e) {
            resp.getWriter().write("Недопустимый формат id");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParameter = req.getParameter("id");
        String name = req.getParameter("name");

        if (idParameter == null || name == null) {
            resp.getWriter().write("Указаны не все параметры для обновления данных");
            return;
        }

        try {
            long lessonId = Long.parseLong(idParameter);

            Lesson lessonToUpdate = lessonRepository.getLessonById(lessonId);

            if (lessonToUpdate == null) {
                resp.getWriter().write("Урок с указанным id: " + lessonId + " не найден.");
                return;
            }

            lessonToUpdate.setName(name);

            lessonRepository.updateLesson(lessonToUpdate);

            resp.getWriter().write("Данные Урока с id: " + lessonId + " успешно обновлены.");

        } catch (NumberFormatException e) {
            resp.getWriter().write("Недопустимый формат id. Id должен быть числовым.");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParameter = req.getParameter("id");

        try {
            long lessonId = Long.parseLong(idParameter);

            Lesson lessonToDelete = lessonRepository.getLessonById(lessonId);

            if (lessonToDelete == null) {
                resp.getWriter().write("Урок с указанным id: " + lessonId + " не найден.");
                return;
            }

            lessonRepository.deleteLesson(lessonToDelete);

            resp.getWriter().write("Урок с id: " + lessonId + " успешно удален.");

        } catch (NumberFormatException e) {
            resp.getWriter().write("Недопустимый формат id. Id должен быть числовым.");
        }
    }
}
