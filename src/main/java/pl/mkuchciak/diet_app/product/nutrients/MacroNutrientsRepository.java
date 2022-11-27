package pl.mkuchciak.diet_app.product.nutrients;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MacroNutrientsRepository extends CrudRepository<MacroNutrients, Long> {
}
