package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Blog;
import model.BlogDao;
import model.User;
import model.UserDao;

import javax.jws.soap.SOAPBinding;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=utf8");
        // 先拿到blogId参数，判断是博客列表页，还是博客详情页
        User user = Util.checkLogin(req);
        if (user == null) {
            resp.setStatus(403);
            return;
        }

        String blogId = req.getParameter("blogId");
        if (blogId == null) {
            // 博客列表页
            // 获取登录用户的信息
            String jsonString = objectMapper.writeValueAsString(user);
            resp.getWriter().write(jsonString);
        } else {
            // 博客详情页
            // 获取文章作者信息
            BlogDao blogDao = new BlogDao();
            Blog blog = blogDao.selectOne(Integer.parseInt(blogId));
            UserDao userDao = new UserDao();
            User author = userDao.selectById(blog.getUserId());
            author.setIsYouBlog(author.getUserId() == user.getUserId() ? 1 : 0);
            String jsonString = objectMapper.writeValueAsString(author);

            resp.getWriter().write(jsonString);


        }
    }
}
