package servlet;

import model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Util {
    public static User checkLogin(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session == null){
            // 说明没有登录
            return null;
        }
        // 登录成功
        User user = (User) session.getAttribute("user");
        return user;
    }
}
