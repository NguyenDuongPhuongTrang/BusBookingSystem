package prj.projectjavawebapplication.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest req,
                             HttpServletResponse res,
                             Object handler) throws Exception {

        String uri = req.getRequestURI();

        if (uri.startsWith("/login") || uri.startsWith("/register") || uri.startsWith("/css"))
            return true;

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("USER") == null) {
            res.sendRedirect("/login");
            return false;
        }

        return true;
    }
}