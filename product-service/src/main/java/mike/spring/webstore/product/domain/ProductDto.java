package mike.spring.webstore.product.domain;

import java.util.ArrayList;
import java.util.List;

import mike.spring.webstore.product.domain.model.ProductForm;
import mike.spring.webstore.product.domain.model.ProductUpdate;
import mike.spring.webstore.product.repository.ColumnValue;
import mike.spring.webstore.product.repository.Columns;
import mike.spring.webstore.product.repository.ProductColumn;

public class ProductDto {

    private ProductDto() {}

    /**
     * Map product form to datasource column.
     * 
     * @param form product create form.
     * @return product columns mapped with the corresponding values.
     */
    public static Columns mapColumnValues(ProductForm form) {

        var columnValues = List.of(
            ProductColumn.NAME.map(form.name()),
            ProductColumn.PRICE.map(form.price()),
            ProductColumn.QUANTITY.map(form.quantity())
        );

        return new Columns(columnValues);
    }

    public static Columns mapColumnValues(int id, ProductUpdate updates) {
        
        List<ColumnValue> columnValues = new ArrayList<>();

        columnValues.add(ProductColumn.ID.map(id));

        updates.name()
            .ifPresent(name -> columnValues.add(ProductColumn.NAME.map(name)));
        
        updates.price()
            .ifPresent(price ->columnValues.add(ProductColumn.PRICE.map(price)));

        updates.quantity()
            .ifPresent(quantity -> columnValues.add(ProductColumn.QUANTITY.map(quantity)));

        return new Columns(columnValues);
    }
}
