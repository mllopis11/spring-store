package mike.spring.webstore.product.service;

import java.util.Collection;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import mike.spring.webstore.bootstrap.api.EntityNotFoundException;
import mike.spring.webstore.product.domain.ProductDto;
import mike.spring.webstore.product.domain.model.Product;
import mike.spring.webstore.product.domain.model.ProductForm;
import mike.spring.webstore.product.domain.model.ProductUpdate;
import mike.spring.webstore.product.repository.ProductRepository;

@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Collection<Product> findAll() {
        return this.productRepository.findAll();
    }

    public Optional<Product> findById(int id) {
        log.debug("Product::findById: id={}", id);
        return this.productRepository.findById(id);
    }

    public Product create(ProductForm form) {

        var columns = ProductDto.mapColumnValues(form);

        log.debug("Product::create: {}", columns.values());

        var product = this.productRepository.insert(columns)
            .orElseThrow(() -> new EntityNotFoundException(Product.class, 0));

        log.info("Product::create: id={}, name={}, status=SUCCESS", product.id(), product.name());

        return product;
    }

    public Optional<Product> update(int id, ProductUpdate form) {

        var product = this.findById(id);

        if (product.isEmpty()) {
            return product;
        }

        var columns = ProductDto.mapColumnValues(id, form);

        log.info("Product::update: values={}", columns.values());

        int rows = this.productRepository.update(columns);

        return switch(rows) {
            case 1 -> this.productRepository.findById(id);
            case 0 -> Optional.empty();
            default -> throw new IllegalStateException(
                "product %s - too many rows (%s) updated".formatted(id, rows));
        };
    }
}
