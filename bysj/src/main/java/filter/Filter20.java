package filter;

        import javax.servlet.*;
        import javax.servlet.annotation.WebFilter;
        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;
        import java.io.IOException;

@WebFilter(filterName = "Filter 20",urlPatterns = {"/*"})
public class Filter20 implements Filter {
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
        System.out.println("Filter 20 -encodingg begins");
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)res;
        //获得path
        String path = request.getRequestURI();
        if(!path.contains("/login")) {
            //设置响应字符编码为UTF-8
            response.setContentType("text/html;charset=UTF-8");
            System.out.println("设置响应字符编码为UTF-8");
            String method = request.getMethod();
            if("POST-PUT".contains(method)){
                //if ("POST".equals(request.getMethod()) || "PUT".equals(request.getMethod())) {
                //设置请求字符编码为UTF-8
                request.setCharacterEncoding("UTF-8");
                System.out.println("设置请求字符编码格式为UTF-8");
            }
        }
        chain.doFilter(req,res);
        System.out.println("Filter 20 -encoding ends");
    }
}
