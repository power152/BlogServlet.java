package servlet;


import model.User;
import model.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /**
         * 1、读取请求中的参数
         * 2、从数据库读一下用户信息，判定时候登录成功
         * 3、登录成功，创建会话
         * 4、重定向博客列表页
         */
        resp.setContentType("text/html;charset=utf8");
        req.setCharacterEncoding("utf8");
        // 1、
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (username == null || "".equals(username) || password == null || "".equals(password)) {

            String html = "<h3>用户或者密码缺失</h3>";
            resp.getWriter().write(html);
            return;
        }
        // 2、
        UserDao userDao= new UserDao();
        User user = userDao.selectByName(username);
        if (user == null ) {
            // 用户不存在
            String html = "<h3>用户或者密码错误</h3>";
            resp.getWriter().write(html);
            return;
        }
        // 用户存在就检查密码
        if (!user.getPassword().equals(password)) {
            // 密码错误
            String html = "<h3>用户或者密码错误</h3>";
            resp.getWriter().write(html);
            return;
        }

        // 3、
        HttpSession session = req.getSession(true);
        session.setAttribute("user",user);

        // 4、
        resp.sendRedirect("blog_list.html");
    }
}
