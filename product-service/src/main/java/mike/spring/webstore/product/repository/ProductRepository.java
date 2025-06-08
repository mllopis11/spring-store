package mike.spring.webstore.product.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import mike.spring.webstore.product.domain.model.Product;

public interface ProductRepository {

    List<Product> findAll();

    Optional<Product> findById(int id);

    Optional<Product> insert(Map<String, ?> colValues);

    Optional<Product> update(Map<String, ?> colValues);
}
