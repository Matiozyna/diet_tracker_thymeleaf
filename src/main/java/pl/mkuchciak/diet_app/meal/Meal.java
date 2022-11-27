package pl.mkuchciak.diet_app.meal;


import lombok.Getter;
import lombok.Setter;
import pl.mkuchciak.diet_app.product.Product;
import pl.mkuchciak.diet_app.user.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @ManyToMany
    private List<Product> products;
    @Convert(converter = DoubleListConverter.class)
    private List<Double> quantities;

    private LocalDate date;

    private MealCategory category;

    public Meal(User user) {
        this.user = user;
        products = new ArrayList<>();
        quantities = new ArrayList<>();
        date = LocalDate.now();
    }

    public Meal() {
        products = new ArrayList<>();
        quantities = new ArrayList<>();
    }

    public Meal(User user, List<Product> products, List<Double> quantities, MealCategory category) {
        this.user = user;
        this.products = products;
        this.quantities = quantities;
        date = LocalDate.now();
        this.category = category;
    }

    public void addProductToMeal(Product product, double quantity){
        products.add(product);
        quantities.add(quantity);
    }

    public void deleteProductFromMeal(Product product){
        int index = products.indexOf(product);
        products.remove(product);
        quantities.remove(index);
    }
}
