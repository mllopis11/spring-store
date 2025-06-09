package mike.spring.webstore.test;

import static org.assertj.core.api.Assertions.assertThat;

import mike.spring.webstore.product.domain.ProductDto;
import mike.spring.webstore.product.domain.model.ProductForm;
import mike.spring.webstore.product.domain.model.ProductUpdate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;

class ProductDtoTest {

    @Test
    void should_return_product_when_create_form() {

        var price = BigDecimal.valueOf(10.2);

        var form = new ProductForm("foo", price, 120);
        var columns = ProductDto.mapColumnValues(form);

        Map<String, Object> expected = new HashMap<>();
        expected.put("NAME", "foo");
        expected.put("PRICE", price);
        expected.put("QUANTITY", 120);

        assertThat(columns).isNotNull();
        assertThat(columns.values()).hasSize(3);
        assertThat(columns.columnValues()).containsExactlyEntriesOf(expected);
    }

    @Test
    void should_return_updated_price_when_update_form() {

        var price = BigDecimal.valueOf(12.5);

        var updates = new ProductUpdate(Optional.empty(), Optional.of(price), Optional.empty());
        var columns = ProductDto.mapColumnValues(1, updates);

        Map<String, Object> expected = new HashMap<>();
        expected.put("id", 1);
        expected.put("price", price);

        assertThat(columns).isNotNull();
        assertThat(columns.values()).hasSize(2);
        assertThat(columns.paramValues()).containsExactlyEntriesOf(expected);
    }
}
