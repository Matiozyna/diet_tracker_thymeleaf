package pl.mkuchciak.diet_app.meal;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.mkuchciak.diet_app.product.Product;
import pl.mkuchciak.diet_app.product.ProductRepository;
import pl.mkuchciak.diet_app.product.nutrients.MacroNutrients;
import pl.mkuchciak.diet_app.user.UserRepository;

import java.time.LocalDate;
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


    public MealDto getMealById(Long id){
        Optional<Meal> mealOpt = mealRepository.findById(id);
        return mealOpt.map(mealDtoConverter::convertToDto)
                .orElseThrow();
    }
    private Double getAllFatByMeal(Meal meal){
        Double fat = 0.0;
        List<Product> products = meal.getProducts();
        List<Double> quantities = meal.getQuantities();
        for(int i = 0; i <products.size(); i++){
            Product product = products.get(0);
            MacroNutrients macroNutrients = product.getMacroNutrients();
            fat += (macroNutrients.getFat()*quantities.get(i)/100);
        }
        return fat;
    }

    private Double getAllProteinByMeal(Meal meal){
        Double protein = 0.0;
        List<Product> products = meal.getProducts();
        List<Double> quantities = meal.getQuantities();
        for(int i = 0; i <products.size(); i++){
            Product product = products.get(0);
            MacroNutrients macroNutrients = product.getMacroNutrients();
            protein += (macroNutrients.getProtein()*quantities.get(i)/100);
        }
        return protein;
    }

    private Double getAllCarbohydratesByMeal(Meal meal){
        Double carbohydrates = 0.0;
        List<Product> products = meal.getProducts();
        List<Double> quantities = meal.getQuantities();
        for(int i = 0; i <products.size(); i++){
            Product product = products.get(0);
            MacroNutrients macroNutrients = product.getMacroNutrients();
            carbohydrates += (macroNutrients.getCarbohydrates()*quantities.get(i)/100);
        }
        return carbohydrates;
    }

    private Double getAllFiberByMeal(Meal meal){
        Double fiber = 0.0;
        List<Product> products = meal.getProducts();
        List<Double> quantities = meal.getQuantities();
        for(int i = 0; i <products.size(); i++){
            Product product = products.get(0);
            MacroNutrients macroNutrients = product.getMacroNutrients();
            fiber += (macroNutrients.getFiber()*quantities.get(i)/100);
        }
        return fiber;
    }

    private Double getAllCaloriesByMeal(Meal meal){
        Double calories = 0.0;
        List<Product> products = meal.getProducts();
        List<Double> quantities = meal.getQuantities();
        for(int i = 0; i <products.size(); i++){
            Product product = products.get(0);
            MacroNutrients macroNutrients = product.getMacroNutrients();
            calories += (macroNutrients.getCalories()*quantities.get(i)/100);
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
            mealDto.setId(id);
            Meal meal = mealDtoConverter.convertToEntity(mealDto);
            mealRepository.save(meal);
            return Optional.of(mealDtoConverter.convertToDto(meal));
        }else
            return Optional.empty();
    }

    void updateMeal(MealDto mealDto){
        System.out.println(mealDto.getId());
        Meal meal = mealDtoConverter.convertToEntity(mealDto);
        System.out.println(meal.getId());
        mealRepository.save(meal);
    }

    public boolean deleteMealById(Long id) {
        if (mealRepository.existsById(id)){
            mealRepository.deleteById(id);
            return true;
        }else{
            return false;
        }
    }

    public List<MealDto> getAllTodayMeals() {
        return mealRepository.findAllByDate(LocalDate.now()).stream()
                    .map(mealDtoConverter::convertToDto)
                    .toList();
    }
}
