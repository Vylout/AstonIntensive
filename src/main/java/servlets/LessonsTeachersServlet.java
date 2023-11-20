package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Lesson;
import model.Teacher;
import repository.LessonRepository;
import repository.LessonRepositoryImpl;
import repository.TeacherRepository;
import repository.TeacherRepositoryImpl;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "LessonsTeachersServlet", value = "/lesson/teacher")
public class LessonsTeachersServlet extends HttpServlet {

    private final LessonRepository lessonRepository;
    private final TeacherRepository teacherRepository;

    public LessonsTeachersServlet() {
        this.lessonRepository = new LessonRepositoryImpl();
        this.teacherRepository = new TeacherRepositoryImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String lessonIdParameter = req.getParameter("lessonId");
        String teacherIdParameter = req.getParameter("teacherId");

        try {
            long lessonId = Long.parseLong(lessonIdParameter);
            long teacherId = Long.parseLong(teacherIdParameter);

            Lesson lesson = lessonRepository.getLessonById(lessonId);
            Teacher thread = teacherRepository.getTeacherById(teacherId);

            if (lesson == null || thread == null) {
                resp.getWriter().write("Урок или преподаватель не найдены");
                return;
            }

            lessonRepository.saveLessonTeacher(lessonId, teacherId);

            resp.getWriter().write("Сохранено успешно");

        } catch (NumberFormatException e) {
            resp.getWriter().write("Ошибка в формате id урока или преподавателя");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParameter = req.getParameter("lessonId");

        try {
            long lessonId = Long.parseLong(idParameter);

            Lesson lesson = lessonRepository.getLessonById(lessonId);

            if (lesson == null) {
                resp.getWriter().write("Урок с указаным id: " + lessonId +" не найден");
                return;
            }
            List<Teacher> teachers = lessonRepository.getAllTeacherByLessonId(lessonId);

            resp.getWriter().write(teachers.toString());

        } catch (NumberFormatException e) {
            resp.getWriter().write("Недопустимый формат id");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String lessonIdParameter = req.getParameter("lessonId");
        String teacherIdParameter = req.getParameter("teacherId");

        if (lessonIdParameter == null || teacherIdParameter == null) {
            resp.getWriter().write("Указаны не все параметры для обновления данных");
            return;
        }

        try {
            long lessonId = Long.parseLong(lessonIdParameter);
            long teacherId = Long.parseLong(teacherIdParameter);

            Lesson lessonToUpdate = lessonRepository.getLessonById(lessonId);
            Teacher teacherToUpdate = teacherRepository.getTeacherById(teacherId);

            if (lessonToUpdate == null || teacherToUpdate == null) {
                resp.getWriter().write("Урок или преподаватель не найдены");
                return;
            }

            lessonRepository.updateLessonTeacher(lessonId, teacherId);

            resp.getWriter().write("Данные обновлены");

        } catch (NumberFormatException e) {
            resp.getWriter().write("Недопустимый формат id. Id должен быть числовым.");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String lessonIdParameter = req.getParameter("lessonId");
        String teacherIdParameter = req.getParameter("teacherId");

        try {
            long lessonId = Long.parseLong(lessonIdParameter);
            long teacherId = Long.parseLong(teacherIdParameter);

            Lesson lessonToUpdate = lessonRepository.getLessonById(lessonId);
            Teacher teacherToUpdate = teacherRepository.getTeacherById(teacherId);

            if (lessonToUpdate == null || teacherToUpdate == null) {
                resp.getWriter().write("Урок или преподаватель не найдены");
                return;
            }

            lessonRepository.deleteLessonTeacher(lessonId, teacherId);

            resp.getWriter().write("Данные удалены");

        } catch (NumberFormatException e) {
            resp.getWriter().write("Недопустимый формат id. Id должен быть числовым.");
        }
    }
}