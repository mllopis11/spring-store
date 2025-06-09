package mike.spring.webstore.product.repository;

public record ColumnValue(String name, String field, Object value, boolean updatable) {

}
