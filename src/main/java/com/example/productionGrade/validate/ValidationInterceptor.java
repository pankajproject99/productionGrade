package com.example.productionGrade.validate;

import com.example.productionGrade.dto.CreateTaskRequest;
import com.example.productionGrade.exception.BadRequestException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.Validator;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Component
@Slf4j
public class ValidationInterceptor implements HandlerInterceptor {
    private Validator validator;

    public ValidationInterceptor(Validator validator) {
        this.validator = validator;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // Field Validation
            Method method = handlerMethod.getMethod();
            Object[] args = handlerMethod.getMethodParameters();
            for (int i = 0; i < args.length; i++) {
                Parameter parameter = method.getParameters()[i];
                if (parameter.isAnnotationPresent(Valid.class)) {
                    Object arg = args[i];
                    BindException errors = new BindException(request, "request");
                    // TOBE Done, validator is not working
                    validator.validate(arg, errors);
                    if (errors.hasErrors()) {
                        throw new MethodArgumentNotValidException((MethodParameter) arg, errors);
                    }
                    // TOBE Can Run custom application validation
                }
            }

            //POST body should not be null
            // Log the request time
            log.info("Request received at {}", request.getUserPrincipal());
            // Log the request body
//            log.info("Request body: {}", request.getReader().readLine());
            String requestMethod = request.getMethod();
            if(requestMethod.equals("POST")){
                int contentLength = request.getContentLength();
                if(contentLength <= 0){
                    throw new BadRequestException("POST Request body should not be null");
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
        // Log the response status code
        log.info("Response status code: {}", response.getStatus());

        // Log the response body
//        log.info("Response body: {}", response.getWriter());
    }
}
