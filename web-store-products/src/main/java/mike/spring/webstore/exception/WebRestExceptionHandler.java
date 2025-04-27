package mike.spring.webstore.exception;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class WebRestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(WebRestExceptionHandler.class);

    @ExceptionHandler
    protected ProblemDetail handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {

        var problems = Optional.of(ex.getConstraintViolations())
                .map(sv -> sv.stream().map(ConstraintViolation::getMessage).toList())
                .orElseGet(List::of);

        var problem = ProblemDetailBuilder.badRequest().withErrors(problems).build();

        log.debug("{}", problem);

        return problem;
    }

    /*
     * Overrides default MethodArgumentNotValidException handler when validation exception @Valid fails
     */
    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(
			@NonNull MethodArgumentNotValidException ex, @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status, @NonNull WebRequest request) {

        var problems = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> String.format("%s: %s", fe.getField(), fe.getDefaultMessage()))
                .toList();

        var problem = ProblemDetailBuilder.badRequest().withErrors(problems).build();

        log.debug("{}", problem);

        return ResponseEntity.of(problem).build();
    }

    @ExceptionHandler
    protected ProblemDetail handleInternalServerError(Throwable ex, WebRequest request) {

        var problem = ProblemDetailBuilder.serverError().build();

        log.error("{}, causedBy:", problem, ex);

        return problem;
    }
}
