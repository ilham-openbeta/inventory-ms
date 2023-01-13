package id.web.ilham.inventory.common.exception;

import id.web.ilham.inventory.common.model.CommonResponse;
import id.web.ilham.inventory.common.model.ErrorResponse;
import id.web.ilham.inventory.common.model.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@Log4j2
@RequiredArgsConstructor
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    private ResponseEntity<Object> handleException(HttpStatus status) {
        String message = "error." + status.value();
        return handleException(status, message);
    }

    private ResponseEntity<Object> handleException(HttpStatus status, String message) {
        String localizedMessage = messageSource.getMessage(message, null, message, LocaleContextHolder.getLocale());
        ResponseMessage<CommonResponse> body = ResponseMessage.error(status.value(), localizedMessage);
        return ResponseEntity.ok(body);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Object> handleApplicationException(ApplicationException e) {
        return handleException(e.getStatus(), e.getMessage());
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<Object> handleBadCredentialsException(FileNotFoundException e) {
        return handleException(HttpStatus.NOT_FOUND, "File Not Found");
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException e) {
        return handleException(HttpStatus.BAD_REQUEST, "Incorrect username or password");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException e) {
        return handleException(HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUnknownException(Exception e) {
        log.error("Unknown Exception", e);
        return handleException(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> handleBindingResult(BindingResult result, HttpStatusCode statusCode) {
        Map<String, List<String>> errors = new HashMap<>();
        result.getFieldErrors().forEach(fieldError -> {
            String name = fieldError.getField();
            String value = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            List<String> messages = errors.get(name);
            if (CollectionUtils.isEmpty(messages)) {
                messages = new ArrayList<>();
                errors.put(name, messages);
            }
            messages.add(value);
        });

        String message = messageSource.getMessage("error." + statusCode, null,
                LocaleContextHolder.getLocale());

        ResponseMessage<CommonResponse> body = ResponseMessage.error(statusCode.value(), message, ErrorResponse.builder().errors(errors).build());
        return ResponseEntity.ok(body);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        return handleBindingResult(ex.getBindingResult(), statusCode);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        return handleException(HttpStatus.valueOf(statusCode.value()));
    }
}
