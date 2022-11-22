package pl.mkuchciak.diet_app.meal;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mkuchciak.diet_app.product.Product;
import pl.mkuchciak.diet_app.product.ProductRepository;
import pl.mkuchciak.diet_app.product.nutrients.Nutrients;
import pl.mkuchciak.diet_app.user.UserRepository;


import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MealService {
    MealRepository mealRepository;
    UserRepository userRepository;
    ProductRepository productRepository;

    MealDtoConverter mealDtoConverter;


    public MealDto findMealById(Long id){
        Optional<Meal> mealOpt = mealRepository.findById(id);
        MealDto mealDto = mealOpt.map(mealDtoConverter::convertToDto)
                .orElseGet(MealDto::new);
        return mealDto;
    }
    private Double getAllFatByMeal(Meal meal){
        Double fat = 0.0;
        List<Product> products = meal.getProducts();
        List<Double> quantities = meal.getQuantities();
        for(int i = 0; i <products.size(); i++){
            Product product = products.get(0);
            Nutrients nutrients = product.getNutrients();
            fat += (nutrients.getFat()*quantities.get(i)/100);
        }
        return fat;
    }

    private Double getAllProteinByMeal(Meal meal){
        Double protein = 0.0;
        List<Product> products = meal.getProducts();
        List<Double> quantities = meal.getQuantities();
        for(int i = 0; i <products.size(); i++){
            Product product = products.get(0);
            Nutrients nutrients = product.getNutrients();
            protein += (nutrients.getProtein()*quantities.get(i)/100);
        }
        return protein;
    }

    private Double getAllCarbohydratesByMeal(Meal meal){
        Double carbohydrates = 0.0;
        List<Product> products = meal.getProducts();
        List<Double> quantities = meal.getQuantities();
        for(int i = 0; i <products.size(); i++){
            Product product = products.get(0);
            Nutrients nutrients = product.getNutrients();
            carbohydrates += (nutrients.getCarbohydrates()*quantities.get(i)/100);
        }
        return carbohydrates;
    }

    private Double getAllFiberByMeal(Meal meal){
        Double fiber = 0.0;
        List<Product> products = meal.getProducts();
        List<Double> quantities = meal.getQuantities();
        for(int i = 0; i <products.size(); i++){
            Product product = products.get(0);
            Nutrients nutrients = product.getNutrients();
            fiber += (nutrients.getFiber()*quantities.get(i)/100);
        }
        return fiber;
    }

    private Double getAllCaloriesByMeal(Meal meal){
        Double calories = 0.0;
        List<Product> products = meal.getProducts();
        List<Double> quantities = meal.getQuantities();
        for(int i = 0; i <products.size(); i++){
            Product product = products.get(0);
            Nutrients nutrients = product.getNutrients();
            calories += (nutrients.getCalories()*quantities.get(i)/100);
        }
        return calories;
    }

    public Map<String, Double> getMacrosMapByMeal(Meal meal){
        return Map.of(
                "fat", getAllFatByMeal(meal),
                "carbohydrates", getAllCarbohydratesByMeal(meal),
                "protein", getAllProteinByMeal(meal),
                "fiber", getAllFiberByMeal(meal),
                "calories", getAllCaloriesByMeal(meal)
        );
    }
    public Map<String, Double> getMacrosMapByMealId(Long id){
        Optional<Meal> mealOtp = mealRepository.findById(id);
        return mealOtp.map(this::getMacrosMapByMeal).orElse(null);
    }
    public List<Product> getProductsListByMealId(Long id){
        return mealRepository.findById(id)
                .map(Meal::getProducts)
                .orElse(List.of());
    }

    public void saveMeal(MealDto mealDto) {
        Meal meal = mealDtoConverter.convertToEntity(mealDto);
        mealRepository.save(meal);
    }

    public Optional<MealDto> replaceMeal(MealDto mealDto, Long id) {
        if(mealRepository.existsById(id)){
            Meal meal = mealDtoConverter.convertToUpdatedEntity(mealDto, id);
            mealRepository.save(meal);
            return Optional.of(mealDtoConverter.convertToDto(meal));
        }else
            return Optional.empty();
    }
}
