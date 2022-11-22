package pl.mkuchciak.diet_app.meal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mkuchciak.diet_app.product.Product;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class MealController {
    MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @GetMapping("/meal/{id}/macros")
    Map<String, Double> getMacrosOfMeal(@PathVariable Long id){
        return  mealService.getMacrosMapByMealId(id);
    }

    @GetMapping("/meal/{id}/products")
    List<Product> getProductsOfMeal(@PathVariable Long id){
        return mealService.getProductsListByMealId(id);
    }

    @GetMapping("/meal/{id}")
    MealDto getMealById(@PathVariable Long id){
        return mealService.findMealById(id);
    }

    @PostMapping("/meal")
    String saveMeal(@RequestBody MealDto mealDto){
        mealService.saveMeal(mealDto);
        return "added";
    }

    @PutMapping("/meal/{id}")
    ResponseEntity<MealDto> replaceMeal(@RequestBody MealDto mealDto, @PathVariable Long id){
        Optional<MealDto> mealDtoOptional = mealService.replaceMeal(mealDto, id);
        return mealDtoOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
