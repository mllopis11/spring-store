package mike.spring.webstore.bootstrap.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

import mike.spring.webstore.bootstrap.web.problem.ProblemDetailBuilder;

public class EntityNotFoundException extends ErrorResponseException {

    private static final Logger log = LoggerFactory.getLogger(EntityNotFoundException.class); 

    public EntityNotFoundException(Class<?> clazz, String value) {
        super(HttpStatus.NOT_FOUND, asProblemDetail(clazz, value), null);
    }

    public EntityNotFoundException(Class<?> clazz, int value) {
        super(HttpStatus.NOT_FOUND, asProblemDetail(clazz, value), null);
    }

    public EntityNotFoundException(Class<?> clazz, long value) {
        super(HttpStatus.NOT_FOUND, asProblemDetail(clazz, value), null);
    }

    private static ProblemDetail asProblemDetail(Class<?> clazz, Object value) {
        var problem = ProblemDetailBuilder.notFound(clazz, value).build();
        log.debug("{}", problem);
        return problem;
    }
}
