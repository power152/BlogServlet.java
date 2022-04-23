package servlet;

import model.Blog;
import model.BlogDao;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/blogDelete")
public class BlogDeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf8");
        // 1. 先监测用户的登录状态. 未登录, 就不能删除.
        User user = Util.checkLogin(req);
        if (user == null) {
            resp.sendRedirect("blog_login.html");
            return;
        }

        // 2. 读取请求中, 要删除的 blogId
        String blogId = req.getParameter("blogId");
        if (blogId == null || "".equals(blogId)) {
            String html = "<h3>当前删除的id确实</h3>";
            resp.getWriter().write(html);
            return;
        }
        // 3. 从数据库里查一下, 看看这个 blogId 是否存在.
        BlogDao blogDao = new BlogDao();
        Blog blog = blogDao.selectOne(Integer.parseInt(blogId));
        if (blog == null) {
            String html = "<h3>当前文章不存在！！</h3>";
            resp.getWriter().write(html);
            return;
        }

        // 4. 判定当前这个博客的作者是否就是登录的用户
        if (blog.getUserId() != user.getUserId()) {
            String html = "<h3>你不能删除别人的博客</h3>";
            resp.getWriter().write(html);
            return;
        }
        // 5. 真正的去删除数据库中的博客
        blogDao.delete(Integer.parseInt(blogId));

        // 6. 重定向到博客列表页
        resp.sendRedirect("blog_list.html");

    }
}
