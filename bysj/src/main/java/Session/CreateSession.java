package Session;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
@WebServlet("/createSession")
public class CreateSession  extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //如果当前请求对应着服务器内存中的一个session对象，则返回该对象
        // 如果服务器内存中的没有session对象与当前请求对应，则创建一个session对象，并立刻返回该对象
        HttpSession session = request.getSession();
        //如果session不活跃间隔大于30秒，则该session失效
        session.setMaxInactiveInterval(30);
        response.getWriter().println("sesseion will last ten seconds");
    }
}
