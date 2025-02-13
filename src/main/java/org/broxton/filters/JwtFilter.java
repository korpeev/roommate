package org.broxton.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.broxton.exceptions.UserExceptions;
import org.broxton.user.service.CustomUserDetailsService;
import org.broxton.utils.JwtUtil;
import org.broxton.utils.PublicAccess;
import org.broxton.utils.TokenType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.IOException;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;
  private final CustomUserDetailsService customUserDetailsService;
  private final RequestMappingHandlerMapping requestMappingHandlerMapping;

  private static final Pattern PUBLIC_PATHS = Pattern.compile(
          "^(?:/swagger-ui/.*|" +  // Swagger UI
                  "/v3/api-docs.*|" +     // OpenAPI Docs
                  "/swagger-ui\\.html|" + // Swagger HTML
                  "/api/v1/auth/.*)$"    // Register
  );




  @Override
  protected void doFilterInternal(
          @NonNull HttpServletRequest request,
          @NonNull HttpServletResponse response,
          @NonNull FilterChain chain) throws ServletException, IOException {

    try {
      if (isPublic(request)) {
        chain.doFilter(request, response);
        return;
      }


      final String authHeader = request.getHeader("Authorization");

      if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        throw new UserExceptions.UserUnauthorizedException();
      }

      final String token = authHeader.substring(7);
      String email = jwtUtil.getEmail(token, TokenType.ACCESS);

      if (
              email != null &&
                      SecurityContextHolder.getContext().getAuthentication() == null &&
                      jwtUtil.validateToken(token, email, TokenType.ACCESS)
      ) {
        UserDetails customUserDetails = customUserDetailsService.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                customUserDetails,
                null,
                customUserDetails.getAuthorities()
        );
        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(auth);
      }
      chain.doFilter(request, response);
    } catch (UserExceptions.UserUnauthorizedException e) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
    }
  }

  private boolean isPublic(HttpServletRequest request) {
    try {
      String path = request.getRequestURI();

      if (PUBLIC_PATHS.matcher(path).matches()) {
        return true;
      }

      ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
      if (attributes == null) return false;

      Object handler = requestMappingHandlerMapping.getHandler(request).getHandler();
      if (!(handler instanceof HandlerMethod handlerMethod)) {
        return false;
      }


      return handlerMethod.hasMethodAnnotation(PublicAccess.class) ||
              handlerMethod.getBeanType().isAnnotationPresent(PublicAccess.class);

    } catch (Exception e) {
      return false;
    }
  }

}
