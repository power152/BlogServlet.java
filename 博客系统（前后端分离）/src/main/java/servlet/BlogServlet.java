package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Blog;
import model.BlogDao;
import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@WebServlet("/blog")
public class BlogServlet extends HttpServlet {
    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=utf8");
        // 1、实现博客列表页
        // 2、读取请求中的blogId参数，实现博客详情页

        // 判断当前是否是登录的状态
        User user = Util.checkLogin(req);
        if (user == null) {
//            resp.sendRedirect("blog_login.html");
            // 这里是ajax来触发的不能返回重定向，此处使用403表示登录失败，然后通过其他的js代码来跳转页面
            resp.setStatus(403);
            return;
        }


        // 从数据库里面查询当前的博客列表
        BlogDao blogDao = new BlogDao();
        String blogId = req.getParameter("blogId");
        if (blogId == null || "".equals(blogId)) {
            // 获取博客列表
            List<Blog> blogs = blogDao.selectAll();
            // 把数据库中的数据转换成JSON字符串，然后返回给浏览器，页面拿到数据就要进行页面渲染，页面渲染就要写前端的代码。
            String jsonString = objectMapper.writeValueAsString(blogs);
            resp.getWriter().write(jsonString);
        } else {
            // 获取博客详情页
            Blog blog = blogDao.selectOne(Integer.parseInt(blogId));
            String jsonString = objectMapper.writeValueAsString(blog);
            resp.getWriter().write(jsonString);
        }

    }


    // 这个方法处理 提交博客 请求
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /**
         * 1、检测用户是否是登录状态，如果是未登录的话，还是要跳转到 登录页面
         * 2、读取请求中的数据
         * 3、构造blog对象，插入的数据库中
         * 4、重定向博客列表
         */


        resp.setContentType("char/html;charset=utf8");
        req.setCharacterEncoding("utf8");

        // 1
        User user = Util.checkLogin(req);
        if (user == null) {
            resp.sendRedirect("blog_login.html");
            return;
        }
        // 2
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        if (title == null || "".equals(title) || content == null || "".equals(content)) {
            String html = "<h3>标题和正文都是空的，不能发布博客！</h3>";
            resp.getWriter().write(html);
            return;
        }
        // 3
        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setContent(content);
        blog.setUserId(user.getUserId());
        blog.setPostTime(new Timestamp(System.currentTimeMillis()));
        BlogDao blogDao = new BlogDao();
        blogDao.insert(blog);

        // 4
        resp.sendRedirect("blog_list.html");

    }
}
