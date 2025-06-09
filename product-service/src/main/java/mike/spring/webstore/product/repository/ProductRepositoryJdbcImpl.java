package mike.spring.webstore.product.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import mike.spring.webstore.product.domain.model.Product;

@Repository
class ProductRepositoryJdbcImpl implements ProductRepository, ProductRepositorySupport {

    private static final Logger log = LoggerFactory.getLogger(ProductRepositoryJdbcImpl.class);

    private final NamedParameterJdbcTemplate namedJdbc;
    private final SimpleJdbcInsert jdbcInsert;

    public ProductRepositoryJdbcImpl(JdbcTemplate jdbc, NamedParameterJdbcTemplate namedJdbc) {
        this.namedJdbc = namedJdbc;

        this.jdbcInsert = new SimpleJdbcInsert(jdbc).withTableName(TAB_NAME)
                    .usingGeneratedKeyColumns(ProductColumn.ID.name());

        this.jdbcInsert.compile();
    }

    @Override
    public List<Product> findAll() {
        return this.namedJdbc.query(GET_PRODUCTS, rowMapper);
    }

    @Override
    public Optional<Product> findById(int id) {
        
        var args = Map.of(ProductColumn.ID.field(), id);
        var stmt = GET_PRODUCT_BY_ID;

        log.debug("Product::find: values[{}]={}, stmt={}", args.size(), args, stmt);

        return this.namedJdbc.query(stmt, args, rowMapper).stream().findFirst();
    }

    @Override
    public int update(Columns columns) {

        var args = columns.paramValues();
        var stmt = buildUpdateStatement(columns);

        log.debug("Product::update: values[{}]={}, stmt={}", args.size(), args, stmt);

        return this.namedJdbc.update(stmt, args);
    }

    @Override
    public Optional<Product> insert(Columns columns) {

        var stmt = this.jdbcInsert.getInsertString();
        var args = columns.columnValues();

        log.debug("Product::insert: values[{}]={}, stmt={}", args.size(), args, stmt);

        var id = this.jdbcInsert.executeAndReturnKey(args).intValue();
        
        return this.findById(id);
    }
}
