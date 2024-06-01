package se.issuetrackingsystem.common.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

    // 허용할 도메인 리스트를 설정합니다.
    private static final List<String> allowedOrigins = Arrays.asList(
            "http://localhost:3000",
            "https://your-deployed-app-url.com"
    );

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 초기화 코드가 필요하지 않다면 비워둡니다.
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        // 요청의 Origin 헤더를 가져옵니다.
        String origin = request.getHeader("Origin");

        // 요청의 Origin이 허용된 도메인 리스트에 있는지 확인합니다.
        if (allowedOrigins.contains(origin)) {
            response.setHeader("Access-Control-Allow-Origin", origin);
        }

        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers",
                "Origin, X-Requested-With, Content-Type, Accept, Authorization");

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {
        // 정리할 코드가 필요하지 않다면 비워둡니다.
    }
}