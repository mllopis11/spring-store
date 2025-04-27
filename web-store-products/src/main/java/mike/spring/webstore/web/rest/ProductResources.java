package mike.spring.webstore.web.rest;

import java.util.Collection;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import mike.spring.webstore.domain.Product;
import mike.spring.webstore.domain.ProductName;
import mike.spring.webstore.exception.ProductNotFoundException;
import mike.spring.webstore.service.ProductService;
import mike.spring.webstore.web.model.ProductForm;

@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Products", description = "Product operations")
@Validated
public class ProductResources {

    private final ProductService productService;

    public ProductResources(ProductService productService) {
        this.productService = productService;
    }

    @Operation(
        method = "GET",
        summary = "Retrieve all products",
        description = "Returns all products or an empty array if any.")
    @ApiResponse(
        responseCode = "200",
        description = "Products",
        content = @Content(
            array = @ArraySchema(schema = @Schema(implementation = Product.class))))
    @GetMapping
    public Collection<Product> findAll() {
        return this.productService.findAll();
    }

    @Operation(
            method = "GET",
            summary = "Retrieve company by its name",
            description = "Returns the company if exists otherwise raise a 404 (NOT_FOUND) error.")
    @ApiResponse(
            responseCode = "200",
            description = "Product",
            content = @Content(schema = @Schema(implementation = Product.class)))
    @GetMapping(value = "/{name}")
    public Product findByName(
            @Parameter(required = true, description = "Product name")
            @PathVariable("name") @ProductName String name) {
        
        return this.productService.findByName(name).orElseThrow(() -> new ProductNotFoundException(name));
    }

    @Operation(
        method = "PUT",
        summary = "Create a new product in stock",
        description = "Returns the created product. Raise a 409 (CONFLICT) if the product already exists.")
    @ApiResponse(
        responseCode = "200",
        description = "Product",
        content = @Content(schema = @Schema(implementation = Product.class)))
    @PutMapping(value = "/create")
    public Product create(@NotNull @RequestBody @Valid ProductForm form) {
        return this.productService.create(form);
    }
}
