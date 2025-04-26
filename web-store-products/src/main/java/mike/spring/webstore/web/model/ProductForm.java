package mike.spring.webstore.web.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema
public record ProductForm(
    @NotBlank
    @Size(min = 5, max = 30)
    @Pattern(regexp = "^\\w{5,30}", message = "must only contains characters")
    String name,
    @Min(1) @Max(100000) double price,
    @Schema(example = "100")
    @Min(0) @Max(10000) int quantity) {

}
