package pl.mkuchciak.diet_app.product.nutrients;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ProperNutrientsValidator implements ConstraintValidator<ProperNutrients, MacroNutrients> {

    @Override
    public void initialize(ProperNutrients constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(MacroNutrients macroNutrients, ConstraintValidatorContext constraintValidatorContext) {
        Double fat = macroNutrients.getFat();
        Double carbohydrates = macroNutrients.getCarbohydrates();
        Double protein = macroNutrients.getProtein();
        return (fat + carbohydrates + protein) <= 100;
    }
}
