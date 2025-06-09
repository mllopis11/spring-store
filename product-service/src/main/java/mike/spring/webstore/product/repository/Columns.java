package mike.spring.webstore.product.repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record Columns(List<ColumnValue> values) {

    public Map<String, Object> paramValues() {
        return values.stream().collect(Collectors.toMap(v -> v.field(), v -> v.value()));
    }

    public Map<String, Object> columnValues() {
        return values.stream().collect(Collectors.toMap(v -> v.name(), v -> v.value()));
    }
}
