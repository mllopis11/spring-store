package mike.spring.webstore.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import mike.spring.webstore.domain.Product;
import mike.spring.webstore.web.model.ProductForm;

@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class); 

    public Collection<Product> findAll() {
        return List.of();
    }

    public Optional<Product> findByName(String name) {
        log.debug("Product::findByName: name={}", name);
        return Optional.empty();
    }

    public Product create(ProductForm form) {
        log.debug("Product::create: {}", form);
        var product = new Product(100, form.name(), form.price(), form.quantity());

        log.info("Product::create: id={}, name={}, status=SUCCESS", product.id(), product.name());

        return product;
    }
}
