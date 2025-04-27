package mike.spring.webstore.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

import mike.spring.webstore.domain.Product;

public class ProductNotFoundException extends ErrorResponseException {

    private static final Logger log = LoggerFactory.getLogger(ProductNotFoundException.class); 

    public ProductNotFoundException(String name) {
        super(HttpStatus.NOT_FOUND, asProblemDetail(name), null);
    }

    private static ProblemDetail asProblemDetail(String name) {
        var problem = ProblemDetailBuilder.notFound(Product.class, name).build();
        log.debug("{}", problem);
        return problem;
    }
}
