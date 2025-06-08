package mike.spring.webstore.product.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import mike.spring.webstore.product.domain.model.Product;

@Repository
class ProductRepositoryJdbcImpl implements ProductRepository {

    private static final Logger log = LoggerFactory.getLogger(ProductRepositoryJdbcImpl.class);

    private static final String TAB_NAME = "T_PRODUCTS";

    private static final String GET_PRODUCTS = """
            SELECT ID, NAME, PRICE, QUANTITY FROM T_PRODUCTS
            """;

    private static final String GET_PRODUCT_BY_ID = GET_PRODUCTS + " WHERE ID = :id";

    private final JdbcTemplate jdbc;
    private final NamedParameterJdbcTemplate namedJdbc;

    public ProductRepositoryJdbcImpl(JdbcTemplate jdbc, NamedParameterJdbcTemplate namedJdbc) {
        this.jdbc = jdbc;
        this.namedJdbc = namedJdbc;
    }

    @Override
    public List<Product> findAll() {
        return this.jdbc.query(GET_PRODUCTS, productMapper);
    }

    @Override
    public Optional<Product> findById(int id) {
        var args = Map.of("id", id);
        return this.namedJdbc.query(GET_PRODUCT_BY_ID, args, productMapper).stream().findFirst();
    }

    @Override
    public Optional<Product> update(Map<String, ?> colValues) {
        var stmt = buildUpdateStatement.apply(colValues);

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public Optional<Product> insert(Map<String, ?> colValues) {

        var stmt = new SimpleJdbcInsert(jdbc).withTableName(TAB_NAME)
            .usingGeneratedKeyColumns("ID");

        log.debug("Product::insert: values[{}]={}", colValues.size(), colValues);

        var id = stmt.executeAndReturnKey(colValues).intValue();
        
        return this.findById(id);
    }

    private static final RowMapper<Product> productMapper = new RowMapper<Product>() {

        @Override
        public Product mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
            return new Product(rs.getInt(1), rs.getString(2), 
                    rs.getDouble(3), rs.getInt(4));
        }
    };

    private static final Function<Map<String, ?>, String> buildUpdateStatement = cols -> {
        var stmt = new StringBuilder("UPDATE ").append(TAB_NAME).append(" SET ");

        var updateCols = cols.keySet().stream().filter(col -> !col.equals("id"))
            .map(col -> String.format("%s = :%s", col.toUpperCase(), col))
            .collect(Collectors.joining(","));

        return stmt.append(updateCols).append(" WHERE ID = :id").toString();
    };
}
