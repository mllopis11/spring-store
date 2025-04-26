package mike.spring.webstore.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String name) {
        super(String.format("no such product: %s", name));
    }
}
