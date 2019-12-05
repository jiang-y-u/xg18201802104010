package controller.login;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import domain.User;
import service.UserService;
import util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login.ctl")
public class LoginController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;chareset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String user_json = JSONUtil.getJSON(request);
        //将JSON字串解析为User对象
        User userToCheck = JSON.parseObject(user_json,User.class);
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
            User loggedUser = UserService.getInstance().login(userToCheck.getUsername(),userToCheck.getPassword());
            if(loggedUser!=null){
                message.put("message","success");
                response.getWriter().println(message);
                HttpSession session  = request.getSession();
                //10分钟没有操作，则使session失效
                session.setMaxInactiveInterval(10*60);
                session.setAttribute("currentUser",loggedUser);
            }else{
                message.put("message","用户名或者密码错误");
                response.getWriter().println(message);
            }
        }catch(SQLException e){
            message.put("message","数据库操作异常");
            response.getWriter().println(message);
        } catch (Exception e) {
        message.put("message", "网络异常");
        e.printStackTrace();
    }
    }
}
