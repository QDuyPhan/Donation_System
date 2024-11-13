package com.donation.donation_system.Interceptor;

import com.donation.donation_system.annotation.AuthRequire;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;


public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            AuthRequire authRequireAnnotation = handlerMethod.getMethodAnnotation(AuthRequire.class);

            if (authRequireAnnotation != null) {
                if (!isUserAuthenticated(request)) {
                    response.sendRedirect("/register");
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isUserAuthenticated(HttpServletRequest request) {
        return request.getSession().getAttribute("user") != null;
    }

}
