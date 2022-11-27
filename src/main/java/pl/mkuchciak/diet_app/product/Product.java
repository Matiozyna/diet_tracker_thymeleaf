package pl.mkuchciak.diet_app.product;

import lombok.*;
import pl.mkuchciak.diet_app.product.nutrients.MacroNutrients;
import pl.mkuchciak.diet_app.product.nutrients.ProperNutrients;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;


@Getter
@Setter
@NoArgsConstructor
@Entity
@EqualsAndHashCode
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String name;
    @OneToOne
    @ProperNutrients
    private MacroNutrients macroNutrients;

    private ProductCategory category;

    public Product(String name, MacroNutrients macroNutrients, ProductCategory category) {
        this.name = name;
        this.macroNutrients = macroNutrients;
        this.category = category;
    }
}
