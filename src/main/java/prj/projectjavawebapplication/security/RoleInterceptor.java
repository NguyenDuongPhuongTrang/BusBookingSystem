package prj.projectjavawebapplication.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import prj.projectjavawebapplication.enums.Role;
import prj.projectjavawebapplication.model.entity.User;

@Component
public class RoleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest req,
                             HttpServletResponse res,
                             Object handler) throws Exception {

        User user = (User) req.getSession().getAttribute("USER");
        String uri = req.getRequestURI();

        if (uri.startsWith("/admin") && user.getRole() != Role.ADMIN) {
            res.sendRedirect("/access-denied");
            return false;
        }

        if (uri.startsWith("/staff") && user.getRole() != Role.STAFF) {
            res.sendRedirect("/access-denied");
            return false;
        }

        return true;
    }
}
