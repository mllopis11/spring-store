package mike.spring.webstore.product.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Collectors;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import mike.spring.webstore.product.domain.model.Product;

public interface ProductRepositorySupport {

    static final String TAB_NAME = "T_PRODUCT";

    static final String GET_PRODUCTS = "SELECT ID, NAME, PRICE, QUANTITY FROM T_PRODUCT";

    static final String GET_PRODUCT_BY_ID = GET_PRODUCTS + " WHERE ID = :id";

    static final String UPD_PRODUCT_BY_ID = "UPDATE T_PRODUCT SET {fields} WHERE ID = :id";

    static final RowMapper<Product> rowMapper = new RowMapper<Product>() {

        @Override
        public Product mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
            return new Product(rs.getInt(1), rs.getString(2), 
                    rs.getBigDecimal(3), rs.getInt(4));
        }
    };

    default String buildUpdateStatement(Columns colValues) {
        
        var fields = colValues.values().stream()
            .filter(ColumnValue::updatable)
            .map(col -> "%s = :%s".formatted(col.name(), col.field()))
            .collect(Collectors.joining(","));

        return UPD_PRODUCT_BY_ID.replace("{fields}", fields);
    }
}
