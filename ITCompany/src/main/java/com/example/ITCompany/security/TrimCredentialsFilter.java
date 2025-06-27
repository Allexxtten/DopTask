package com.example.ITCompany.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TrimCredentialsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        ServletRequest wrappedRequest = new HttpServletRequestWrapper(httpRequest) {
            @Override
            public String getParameter(String name) {
                String value = super.getParameter(name);
                if (value != null && (name.equals("username") || name.equals("password"))) {
                    return value.trim();
                }
                return value;
            }
        };
        chain.doFilter(wrappedRequest, response);
    }
}
