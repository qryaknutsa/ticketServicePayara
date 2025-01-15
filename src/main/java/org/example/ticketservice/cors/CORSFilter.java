package org.example.ticketservice.cors;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;

//@Configuration
//public class CORSFilter implements Filter {
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//
//        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
//        httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, PATCH");
//        httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Accept, X-Requested-With");
//        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
//
//        // Если это предварительный запрос (OPTIONS), завершаем обработку здесь
//        if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
//            httpResponse.setStatus(HttpServletResponse.SC_OK);
//            return;
//        }
//
//        // Продолжаем цепочку фильтров
//        chain.doFilter(request, response);
//    }
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {}
//
//    @Override
//    public void destroy() {}
//}
