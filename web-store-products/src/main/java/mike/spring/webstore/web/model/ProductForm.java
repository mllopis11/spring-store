package mike.spring.webstore.web.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import mike.spring.webstore.domain.ProductName;

@Schema
public record ProductForm(
    @ProductName
    String name,
    @Min(1) @Max(100000) double price,
    @Schema(example = "100")
    @Min(0) @Max(10000) int quantity) {

}
