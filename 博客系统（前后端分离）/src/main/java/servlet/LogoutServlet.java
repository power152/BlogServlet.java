package servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("text/html;charset=utf8");
        // 实现的代码就是把Session中的user删除掉
        HttpSession session = req.getSession(false);
        if (session == null) {
            String html = "<h3>当前未登录，不能注销</h3>";
            resp.getWriter().write(html);
            return;
        }
        // 移除键值对里面的 key
        session.removeAttribute("user");

        // 重定向登录页面
        resp.sendRedirect("blog_login.html");

    }
}
