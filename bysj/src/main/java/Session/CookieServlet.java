package Session;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/showCookies")
public class CookieServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //得到所有的cookie信息
        Cookie [] allCookies = request.getCookies();
        if(allCookies==null){
            //如果cookie是null，就向客服端发送null
            response.getWriter().println("no cookies");
        }else{
            for(Cookie cookie:allCookies){
                response.getWriter().println(cookie.getName()+"m"+cookie.getValue());
            }
        }
    }
}
