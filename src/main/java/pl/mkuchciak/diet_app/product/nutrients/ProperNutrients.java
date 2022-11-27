package pl.mkuchciak.diet_app.product.nutrients;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Constraint(validatedBy = ProperNutrientsValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ProperNutrients {
    String message() default "Niepoprawne makrosklaniki";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
