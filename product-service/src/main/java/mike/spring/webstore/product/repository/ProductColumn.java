package mike.spring.webstore.product.repository;

public enum ProductColumn {

    ID("id", false),
    NAME("name", true),
    PRICE("price", true),
    QUANTITY("quantity", true);

    private final String field;
    private final boolean updatable; 

    private ProductColumn(String field, boolean updatable) {
        this.field = field;
        this.updatable = updatable;
    }

    public String field() {
        return field;
    }

    public boolean updatable() {
        return updatable;
    }

    public ColumnValue map(Object value) {
        return new ColumnValue(this.name(), this.field(), value, this.updatable());
    }
}
