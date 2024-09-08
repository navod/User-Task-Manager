package com.assignment.task_manager.exception;

import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
@AllArgsConstructor
public class ExceptionHandler extends ResponseEntityExceptionHandler {


    /**
     * Handle constraint violation response entity.
     *
     * @param exception the exception
     * @param request   the request
     * @return the response entity
     */
    @org.springframework.web.bind.annotation.ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException exception, WebRequest request) {
        final List<String> validationErrors = exception.getConstraintViolations().stream().
                map(violation ->
                        violation.getPropertyPath() + ExceptionConstant.FIELD_ERROR_SEPARATOR.toString() + violation.getMessage())
                .collect(Collectors.toList());
        return getExceptionResponseEntity(exception, HttpStatus.BAD_REQUEST, request, validationErrors);
    }


    /**
     * A general handler for all uncaught exceptions
     *
     * @param exception the exception
     * @param request   the request
     * @return the response entity
     */
    @org.springframework.web.bind.annotation.ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAllExceptions(Exception exception, WebRequest request) {
        ResponseStatus responseStatus =
                exception.getClass().getAnnotation(ResponseStatus.class);
        final HttpStatus status =
                responseStatus != null ? responseStatus.value() : HttpStatus.INTERNAL_SERVER_ERROR;
        final String localizedMessage = exception.getLocalizedMessage();
        final String path = request.getDescription(false);
        String message = (StringUtils.isNotEmpty(localizedMessage) ? localizedMessage : status.getReasonPhrase());
        logger.error(String.format(ExceptionConstant.ERROR_MESSAGE_TEMPLATE.toString(), message, path), exception);
        return getExceptionResponseEntity(exception, status, request, Collections.singletonList(message));
    }

    /**
     * Build a detailed information about the exception in the response
     */
    private ResponseEntity<Object> getExceptionResponseEntity(final Exception exception,
                                                              final HttpStatus status,
                                                              final WebRequest request,
                                                              final List<String> errors) {
        final Map<String, Object> body = new LinkedHashMap<>();
        final String path = request.getDescription(false);
        body.put(ExceptionConstant.TIMESTAMP.toString(), Instant.now());
        body.put(ExceptionConstant.STATUS.toString(), status.value());
        body.put(ExceptionConstant.ERRORS.toString(), errors);
        body.put(ExceptionConstant.MESSAGE.toString(), getMessageForStatus(status));


        final String errorsMessage = CollectionUtils.isNotEmpty(errors) ?
                errors.stream().filter(StringUtils::isNotEmpty).collect(Collectors.joining(ExceptionConstant.LIST_JOIN_DELIMITER.toString()))
                : status.getReasonPhrase();
        log.error(ExceptionConstant.ERRORS_FOR_PATH.toString(), errorsMessage, path);
        return new ResponseEntity<>(body, status);
    }

    private String getMessageForStatus(HttpStatus status) {
        switch (status) {
            case UNAUTHORIZED:
                return ExceptionConstant.ACCESS_DENIED.toString();
            case BAD_REQUEST:
                return ExceptionConstant.INVALID_REQUEST.toString();
            default:
                return status.getReasonPhrase();
        }
    }
}