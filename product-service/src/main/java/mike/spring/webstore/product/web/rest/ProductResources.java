package mike.spring.webstore.product.web.rest;

import java.util.Collection;

import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import mike.spring.webstore.bootstrap.api.EntityNotFoundException;
import mike.spring.webstore.product.domain.model.Product;
import mike.spring.webstore.product.domain.model.ProductForm;
import mike.spring.webstore.product.domain.model.ProductUpdate;
import mike.spring.webstore.product.service.ProductService;

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
            summary = "Retrieve product by its code",
            description = "Returns the product if exists otherwise raise a 404 (NOT_FOUND) error.")
    @ApiResponse(
            responseCode = "200",
            description = "Product",
            content = @Content(schema = @Schema(implementation = Product.class)))
    @GetMapping(value = "/{id}")
    public Product findByName(
            @Parameter(required = true, description = "Product id.")
            @PathVariable("id") @Min(1) @Max(999999) int id) {
        
        return this.productService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Product.class, id));
    }

    @Operation(
        method = "PUT",
        summary = "Create a product",
        description = "Returns the created product. Raise 409 (CONFLICT) if the product already exists.")
    @ApiResponse(
        responseCode = "200",
        description = "Product",
        content = @Content(schema = @Schema(implementation = Product.class)))
    @PutMapping(value = "/create")
    public Product create(@NotNull @RequestBody @Valid ProductForm form) {
        return this.productService.create(form);
    }

    @Operation(
        method = "PATCH",
        summary = "Update a product",
        description = "Returns the updated product. Raise 404 (NOT_FOUND) if the product does not exists.")
    @ApiResponse(
        responseCode = "200",
        description = "Product",
        content = @Content(schema = @Schema(implementation = Product.class)))
    @PatchMapping(value = "/{id}/update")
    public Product create(
            @Parameter(required = true, description = "Product id.")
            @PathVariable("id") @Min(1) @Max(999999) int id,
            @NotNull @RequestBody ProductUpdate form) {

        return this.productService.update(id, form)
                .orElseThrow(() -> new EntityNotFoundException(Product.class, id));
    }
}
