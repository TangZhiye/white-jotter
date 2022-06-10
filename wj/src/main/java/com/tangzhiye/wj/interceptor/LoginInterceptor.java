package com.tangzhiye.wj.interceptor;

import com.tangzhiye.wj.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String[] requireAuthPages = new String[]{
                "index",
        };
        boolean isRequireAuthPages = false;
        String url = request.getRequestURL().toString();
        for (String s : requireAuthPages){
            if (url.contains(s)){
                isRequireAuthPages = true;
                break;
            }
        }
        //是要验证的URI，进行session验证
        if (isRequireAuthPages){
//            System.out.println("进行session验证");
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if (user==null){//未登录
                response.sendRedirect("login");
                return false;
            }
        }
//        System.out.println("没有进行session验证");
        return true;
    }
}
