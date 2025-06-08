package mike.spring.webstore.product.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import mike.spring.webstore.bootstrap.web.EntityNotFoundException;
import mike.spring.webstore.product.domain.model.Product;
import mike.spring.webstore.product.domain.model.ProductForm;
import mike.spring.webstore.product.repository.ProductRepository;
import mike.spring.webstore.product.repository.ProductTable;

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
        log.debug("Product::create: {}", form);

        var colValues = Map.of(
            ProductTable.NAME.colName(), form.name(), 
            ProductTable.PRICE.colName(), form.price(),
            ProductTable.QUANTITY.colName(), form.quantity());

        var product = this.productRepository.insert(colValues)
            .orElseThrow(() -> new EntityNotFoundException(Product.class, 0));

        log.info("Product::create: id={}, name={}, status=SUCCESS", product.id(), product.name());

        return product;
    }
}
