package com.http.demo.serlet;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

public class ServletX  extends GenericServlet {

    public ServletX() {
        System.out.println("0000000000");
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("11111111111111");
    }

    @Override
    public ServletConfig getServletConfig() {
        System.out.println("2222222");
        return null;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        System.out.println("33333");
    }

    @Override
    public String getServletInfo() {
        System.out.println("4444");
        return null;
    }

    @Override
    public void destroy() {
        System.out.println("55555");
    }
}
