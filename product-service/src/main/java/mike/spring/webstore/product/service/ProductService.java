package mike.spring.webstore.product.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import mike.spring.webstore.product.domain.model.Product;
import mike.spring.webstore.product.domain.model.ProductForm;

@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    public Collection<Product> findAll() {
        return List.of();
    }

    public Optional<Product> findById(int id) {
        log.debug("Product::findById: id={}", id);
        return Optional.empty();
    }

    public Product create(ProductForm form) {
        log.debug("Product::create: {}", form);
        var product = new Product(100, form.name(), form.price(), form.quantity());

        log.info("Product::create: id={}, name={}, status=SUCCESS", product.id(), product.name());

        return product;
    }
}
