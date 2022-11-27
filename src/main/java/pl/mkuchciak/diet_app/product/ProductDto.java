package pl.mkuchciak.diet_app.product;

import lombok.*;

import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDto {
    private Long id;
    private String name;
    private HashMap<String, Double> nutrients;
    private ProductCategory category;
}
