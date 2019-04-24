/*
package com.py.Interceptor;

import com.py.service.TokenService;
import com.py.service.UserService;
import com.py.util.Log;
import com.py.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

*/
/**
 * 登录拦截器
 *//*

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    TokenService tokenService;
    @Autowired
    UserService userService;

    */
/**
     * 进入url之前拦截
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     *//*

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setContentType("text/html;charset=UTF-8");
        HttpServletRequest req = (HttpServletRequest) request;
        String uri = req.getRequestURI();// 获取用户请求的地址
        if (StringUtils.isNotBlank(uri)) {
            // 如果请求登陆地址，直接放行
            if (checkPass(uri)) {
                return true;
            } else {
                String token = request.getParameter("TOKEN");
                Integer userId = Integer.parseInt(request.getParameter("userId"));
                //判断userid和 token不能为空
                if (StringUtils.isNotBlank(token) || null != userId) {
                    //再判断是否有这个用户
                    if (null == userService.selectByPrimaryKey(userId)) {
                        PrintWriter pw = response.getWriter();
                        pw.write("{\"code\":3,\"msg\":\"此用户不存在\"}");
                        pw.flush();
                        pw.close();
                        return false;
                    }
                    boolean valid = tokenService.valid(userId, token);// 验证用户传入的token是否有效
                    // 如果token有效，将token信息放入session中，并放行
                    if (valid) {
                        return true;
                    } else {
                        PrintWriter pw = response.getWriter();
                        pw.write("{\"code\":0,\"msg\":\"用户未登录或者登陆信息已过期\"}");
                        pw.flush();
                        pw.close();
                        return false;
                    }
                } else {
                    Log.error("=================token为空=====================");
                    PrintWriter pw = response.getWriter();
                    pw.write("{\"code\":0,\"msg\":\"userId和TOKEN不能为空\"}");
                    pw.flush();
                    pw.close();
                    return false;
                }
            }
        }
        return false;
    }


}
*/
