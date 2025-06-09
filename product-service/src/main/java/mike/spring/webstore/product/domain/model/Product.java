package mike.spring.webstore.product.domain.model;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record Product(
    @Min(1) @Max(100000) int id,
    @NotBlank String name,
    @Schema(example = "10.25")
    @DecimalMin("0.01") @DecimalMax("100000.00") BigDecimal price,
    @Schema(example = "100")
    @Min(0) @Max(10000) int quantity
) {

}
