import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@WebFilter(filterName = "EncodingFilter")
public class EncodingFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        chain.doFilter(req, resp);
        HttpServletRequest request=(HttpServletRequest)req;
        Class[] interfaces={HttpServletRequest.class};
        //动态代理加强request的getParameter方法处理中文乱码
        HttpServletRequest myreq =  (HttpServletRequest) Proxy.newProxyInstance(EncodingFilter.class.getClassLoader(), interfaces, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //判断是否执行到request.getParameter方法
                String s=method.getName();
                if ("getParameter".equalsIgnoreCase(s)){
                    //判断提交方式
                    String m1=request.getMethod();
                    if ("get".equalsIgnoreCase(m1)){
                        String s2=(String)method.invoke(request,args);
                       s = new String(s2.getBytes("iso8859-1"),"utf-8");
                       return s;
                    }else if ("post".equalsIgnoreCase(m1)){
                        request.setCharacterEncoding("utf-8");

                    }
                }
                return  method.invoke(request,args);
            }
        });
        //放行
        chain.doFilter(myreq,resp);
    }


    public void init(FilterConfig config) throws ServletException {

    }

}
