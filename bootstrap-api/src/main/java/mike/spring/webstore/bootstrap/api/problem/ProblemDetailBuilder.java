package mike.spring.webstore.bootstrap.api.problem;

import java.net.URI;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class ProblemDetailBuilder {

    private final ProblemDetail problemDetail;

    private ProblemDetailBuilder(HttpStatus status, String message, String type) {
        this.problemDetail = ProblemDetail.forStatusAndDetail(status, message);
        this.problemDetail.setProperty("timestamp", Instant.now().truncatedTo(ChronoUnit.MICROS));
        this.problemDetail.setType(URI.create(type));
    }

    public static ProblemDetailBuilder badRequest() {
        return ProblemDetailBuilder.badRequest("invalid request content");
    }

    public static ProblemDetailBuilder badRequest(String message) {
        return new ProblemDetailBuilder(HttpStatus.BAD_REQUEST, message, "about:bad-request");
    }

    public static ProblemDetailBuilder notFound(String message) {
        return new ProblemDetailBuilder(HttpStatus.NOT_FOUND, message, "about:not-found");
    }

    public static ProblemDetailBuilder notFound(Class<?> clazz, Object value) {
        var message = String.format("%s not found: %s", clazz.getSimpleName(), value);

        return ProblemDetailBuilder.notFound(message);
    }

    public static ProblemDetailBuilder serverError() {
        var message = "oups, something went wrong (contact the support !!!)";
        return new ProblemDetailBuilder(HttpStatus.INTERNAL_SERVER_ERROR, message, "about:server-error");
    }

    public ProblemDetailBuilder withErrors(Collection<String> errors) {
        this.problemDetail.setProperty("errors", errors);
        return this;
    }

    public ProblemDetail build() {
        return this.problemDetail;
    }
}
