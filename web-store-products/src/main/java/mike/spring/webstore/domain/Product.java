package mike.spring.webstore.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Schema
public record Product(
    @Min(1) @Max(999999) int id,
    @NotBlank 
    String name,
    @Min(1) double price,
    @Schema(example = "100")
    @Min(0) int quantity) {

}
