package mike.spring.webstore.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import mike.spring.webstore.domain.Product;
import mike.spring.webstore.web.model.ProductForm;

@Service
public class ProductService {

    public Collection<Product> findAll() {
        return List.of();
    }

    public Optional<Product> findByName(String name) {
        return Optional.empty();
    }

    public Product create(ProductForm form) {
        return new Product(100, form.name(), form.price(), form.quantity());
    }
}
