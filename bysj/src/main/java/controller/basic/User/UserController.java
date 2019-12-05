package controller.basic.User;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
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
import java.util.Collection;

@WebServlet("/user.ctl")
public class UserController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //读取参数id和username
        String id_str = request.getParameter("id");
        String  name_str = request.getParameter("username");

        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        try {
            //访问权限验证
            //如果当前请求对应着服务器内存中的一个session对象，则返回该对象
            //如果服务器内存中的没有session对象与当前的请求对象对应，则返回null
            HttpSession session = request.getSession(false);
            System.out.println(session);
            if (session == null || session.getAttribute("currentUser") == null) {
                return;
            } else {
                if (id_str != null) {
                    //如果id_str的值不是空的，调用responseUsers(id,response)方法
                    int id = Integer.parseInt(id_str);
                    responseUsers(id, response);
                } else if (name_str != null) {
                    //如果name_str不是空的，调用responseUsers(name_str,response)方法
                    responseUsers(name_str, response);
                } else {
                    //剩下的调用responseUsers(response)方法
                    responseUsers(response);
                }
            }
        }catch(Exception e){
            message.put("message", "网络异常");
            //响应message到前端
            response.getWriter().println(message);
        }
    }
    private void responseUsers(HttpServletResponse response)
            throws  IOException {
        //创建一个collection类型的集合，调用UserService中的getInstance()方法，在调用userDao方法中的findAll()方法
        Collection<User> users = UserService.getInstance().findAll();
        //将对象转换成json类型的字符串
        String users_json = JSON.toJSONString(users);
        //加入数据信息
        response.getWriter().println(users_json);
    }
    private void responseUsers(int id, HttpServletResponse response) {
        try{
            //根据id查找用户
            User user= UserService.getInstance().find(id);
            String user_json = JSON.toJSONString(user);
            //响应数据到前端
            response.getWriter().println(user_json);
        }catch(Exception e){
            e.getStackTrace();}
    }
    private void responseUsers( String name_str,HttpServletResponse response) throws IOException, SQLException {
        Collection<User>users = UserService.getInstance().findByUserName(name_str);
        String users_json = JSON.toJSONString(users, SerializerFeature.DisableCircularReferenceDetect);
        //响应到前端
        response.getWriter().println(users_json);
    }
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        //根据request对象获得代表参数的json字串
        String user_json = JSONUtil.getJSON(request);
        //将json字串解析成user对象
        User userToUpdate = JSON.parseObject(user_json, User.class);
        System.out.println("degreeToUpdate="+userToUpdate);
        //创建JSON对象
        JSONObject message = new JSONObject();
        //到数据库表修改Degree对象对应的记录
        try {
            HttpSession session = request.getSession(false);
            System.out.println(session);
            if (session == null || session.getAttribute("currentUser") == null) {
                return;
            } else {
                UserService.getInstance().update(userToUpdate);
                message.put("message", "修改密码成功");
            }
        }catch (SQLException e){
            message.put("message", "数据库操作异常");
        }catch(Exception e){
            message.put("message", "网络异常");
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws  IOException {
        String password_str = request.getParameter("password");
        String  name_str = request.getParameter("username");
        //创建JSON对象
        JSONObject message = new JSONObject();
        try { HttpSession session = request.getSession(false);
            System.out.println(session);
            if(session==null||session.getAttribute("currentUser")==null){
                return;
            }else {
            }
            User user = UserService.getInstance().login(name_str, password_str);
            String user_str = JSON.toJSONString(user);
            response.getWriter().println(user_str);
        }catch(Exception e){
            message.put("message", "网络异常");
            e.printStackTrace();
        }
    }
}
