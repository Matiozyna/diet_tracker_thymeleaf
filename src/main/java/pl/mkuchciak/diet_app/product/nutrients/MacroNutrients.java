package pl.mkuchciak.diet_app.product.nutrients;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class MacroNutrients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double fat = 0.0;
    private Double carbohydrates = 0.0;
    private Double protein = 0.0;
    private Double calories = 0.0;
    private Double fiber = 0.0;


    public MacroNutrients(Double fat, Double carbohydrates, Double protein) {
        this.fat = fat;
        this.carbohydrates = carbohydrates;
        this.protein = protein;
        generateCalories();
    }

    public MacroNutrients(Double fat, Double carbohydrates, Double protein, Double fiber) {
        this(fat,carbohydrates,protein);
        this.fiber = fiber;
    }

    public MacroNutrients(Double fat, Double carbohydrates, Double protein, Double calories, Double fiber) {
        this.fat = fat;
        this.carbohydrates = carbohydrates;
        this.protein = protein;
        this.calories = calories;
        this.fiber = fiber;
    }

    private void generateCalories(){
        this.calories = this.fat*9 + this.carbohydrates*4 + this.protein*4;
    }


}
