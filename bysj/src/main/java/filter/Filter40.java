/*package filter;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
//注解/过滤器名称/该过滤器对所有请求有效
@WebFilter(filterName = "Filter 40",urlPatterns = "/*")
public class Filter40 implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //打印提示信息
        System.out.println("access authorization verification starts");
        //强制类型转换成HttpServletRequest类型
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        //强制类型转换成HttpServletRequest类型
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //获得请求的url
        String path = request.getRequestURI();
        //创建JSON对象message，以便往前端响应信息
        JSONObject message = new JSONObject();
        if (!path.contains("/login.ctl")) {
            //访问权限验证
            //如果当前请求对应着服务器内存中的一个session对象，则返回该对象
            //如果服务器内存中的没有session对象与当前的请求对象对应，则返回null
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("currentUser")==null){
                message.put("message","请登录或重新登陆");
                //响应message到前端
                response.getWriter().println(message);
            }
        }
        //执行其他过滤器，若过滤器已经执行完毕，则执行原请求
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("access authorization verification ends");
    }
    @Override
    public void destroy() {}
}*/
