package pl.mkuchciak.diet_app.product;

import lombok.*;
import pl.mkuchciak.diet_app.product.nutrients.Nutrients;

import javax.persistence.*;


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
    private String name;
    @OneToOne
    private Nutrients nutrients;


    public Product(String name, Nutrients nutrients) {
        this.name = name;
        this.nutrients = nutrients;
    }
}
