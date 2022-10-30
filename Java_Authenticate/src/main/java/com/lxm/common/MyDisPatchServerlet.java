package com.lxm.common;

import com.lxm.auth.bean.ReturnMsg;
import com.lxm.common.exception.BusinessException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;

public class MyDisPatchServerlet implements Servlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        SystemInit.init();
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        UrlMapInfo urlMapInfo  = SystemInit.urlMappingMap.get(((HttpServletRequest) servletRequest).getRequestURI());
        Object result = null;
        if (urlMapInfo == null) {
            return;
        }
        try {
            result = urlMapInfo.getUrlMethod().invoke(urlMapInfo.getBeanInstance(), servletRequest);
            servletResponse.setContentType("text/html;charset=utf-8");
            PrintWriter out=servletResponse.getWriter();
            out.print(result);
            out.flush();
            out.close();
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                result = e;
            } else {
                result = ReturnMsg.SYSTEM_ERROR;
            }
        } finally {
            servletResponse.setContentType("text/html;charset=utf-8");
            PrintWriter out=servletResponse.getWriter();
            out.print(result);
            out.flush();
            out.close();
        }

    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {
        SystemInit.destroy();

    }

}
