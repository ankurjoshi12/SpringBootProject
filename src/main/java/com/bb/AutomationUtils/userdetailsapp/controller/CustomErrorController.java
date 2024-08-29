package com.bb.AutomationUtils.userdetailsapp.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class CustomErrorController implements ErrorController {
    @RequestMapping("/error")
    public ResponseEntity<Map<String, Object>> handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object errorException = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);

        Map<String, Object> body = new HashMap<>();
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                httpStatus = HttpStatus.NOT_FOUND;
                body.put("error", "Not Found");
                body.put("message", "The resource you requested could not be found.");
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                httpStatus = HttpStatus.FORBIDDEN;
                body.put("error", "Forbidden");
                body.put("message", "You do not have permission to access this resource.");
            } else if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
                httpStatus = HttpStatus.UNAUTHORIZED;
                body.put("error", "Unauthorized");
                body.put("message", "You are not authorized to access this resource.");
            } else {
                httpStatus = HttpStatus.valueOf(statusCode);
                body.put("error", httpStatus.getReasonPhrase());
                body.put("message", "An unexpected error occurred.");
                body.put("message", errorException);
            }

            body.put("status", statusCode);
        }

        return new ResponseEntity<>(body, httpStatus);
    }
}
