package com.tangzhiye.wj.interceptor;

import com.tangzhiye.wj.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 放行 options 请求，否则无法让前端带上自定义的 header 信息，导致 sessionID 改变，shiro 验证失败
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())){
            response.setStatus(HttpStatus.NO_CONTENT.value());
            return true;
        }

        // 如果是要验证的URI，进行session验证
        // 首次进行登录时不拦截，之后每次前端发送请求时都会带上cookie进行拦截验证
        String[] requireAuthPages = new String[]{
                "index","authentication"
        };
        boolean isRequireAuthPages = false;
        String url = request.getRequestURL().toString();
        for (String s : requireAuthPages){
            if (url.contains(s)){
                isRequireAuthPages = true;
                break;
            }
        }
        if (isRequireAuthPages){
            // 使用 shiro 验证
            Subject subject = SecurityUtils.getSubject();
//            System.out.println("拦截器验证结果:"+subject.isAuthenticated());
            System.out.println("subject.isRemembered(): "+subject.isRemembered());
            System.out.println("subject.isAuthenticated(): "+subject.isAuthenticated());
            if (!subject.isAuthenticated() && !subject.isRemembered()){
                return false;
            }
        }
//        System.out.println("没有进行session验证");
        return true;


        /**
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
         **/
    }
}
