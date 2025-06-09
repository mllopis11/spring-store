package mike.spring.webstore.product.domain.model;

import java.math.BigDecimal;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import mike.spring.webstore.bootstrap.utilities.PreConditions;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProductUpdate(
    @JsonProperty("name")
    @Schema(example = "foo")
    Optional<String> name,
    @JsonProperty("price")
    @Schema(example = "1.23")
    Optional<BigDecimal> price,
    @JsonProperty("quantity")
    @Schema(example = "100")
    Optional<Integer> quantity
) {

    public ProductUpdate {

        Optional.ofNullable(name).ifPresent(value -> 
            PreConditions.notBlank(value.get(), "name must be not blank")
        );

        Optional.ofNullable(price).ifPresent(value -> {
            var val = value.get().doubleValue();
            var test = val >= 0.01 && val <= 100000;
            PreConditions.test(test, "price out of bound (min.: 1, max.: 100000)");
        });

        Optional.ofNullable(quantity).ifPresent(value -> {
            var test = value.get() >= 0 && value.get() <= 10000;
            PreConditions.test(test, "quantity out of bound (min.: 0, max.: 10000)");
        });
    }

}
