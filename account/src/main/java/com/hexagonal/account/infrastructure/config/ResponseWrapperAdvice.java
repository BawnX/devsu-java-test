package com.hexagonal.account.infrastructure.config;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.hexagonal.account.domain.models.ErrorOr;

@SuppressWarnings("rawtypes")
@ControllerAdvice
public class ResponseWrapperAdvice implements ResponseBodyAdvice {
    @Override
    public Object beforeBodyWrite(@Nullable Object body,
            @Nullable MethodParameter returnType,
            @Nullable MediaType selectedContentType,
            @Nullable Class selectedConverterType,
            @Nullable ServerHttpRequest request,
            @Nullable ServerHttpResponse response) {

        try {
            @SuppressWarnings("unchecked")
            ResponseWrapper<Object> responseWrapper = ResponseWrapper
                    .createTry((ErrorOr<Object, RuntimeException>) body);
            HttpMethod httpMethod = request != null ? request.getMethod() : null;

            if (httpMethod == null) {
                throw new RuntimeException("HttpMethod is null");
            }

            if (response == null) {
                throw new RuntimeException("Respone is null");
            }

            HttpStatus httpStatus;
            if (body instanceof ErrorOr && ((ErrorOr) body).isFailure()) {
                httpStatus = HttpStatus.BAD_REQUEST;
                response.setStatusCode(httpStatus);
                return responseWrapper;
            }

            switch (httpMethod.toString()) {
                case "GET" -> httpStatus = HttpStatus.OK;
                case "POST" -> httpStatus = HttpStatus.CREATED;
                case "PUT" -> httpStatus = HttpStatus.OK;
                case "PATCH" -> httpStatus = HttpStatus.OK;
                case "DELETE" -> httpStatus = HttpStatus.OK;
                default -> httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
            }
            response.setStatusCode(httpStatus);
            return responseWrapper;
        } catch (Exception e) {
            HttpStatus httpStatus;
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            response.setStatusCode(httpStatus);
            return ResponseWrapper.createCatch(e);
        }
    }

    @Override
    public boolean supports(
            @Nullable MethodParameter returnType,
            @Nullable Class converterType) {
        return true;
    }
}