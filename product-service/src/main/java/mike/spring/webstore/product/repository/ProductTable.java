package mike.spring.webstore.product.repository;

public enum ProductTable {

    ID("id", true),
    NAME("name", false),
    PRICE("price", false),
    QUANTITY("quantity", false);

    public static final String TAB_NAME = "T_PRODUCTS";

    private final String field;
    private final boolean key;

    private ProductTable(String field, boolean key) {
        this.field = field;
        this.key = key;
    }

    public String fieldName() {
        return field;
    }

    public String colName() {
        return this.name();
    }

    public boolean isKey() {
        return key;
    } 
}
