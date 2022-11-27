package pl.mkuchciak.diet_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.mkuchciak.diet_app.meal.Meal;
import pl.mkuchciak.diet_app.meal.MealCategory;
import pl.mkuchciak.diet_app.meal.MealRepository;
import pl.mkuchciak.diet_app.meal.MealService;
import pl.mkuchciak.diet_app.product.Product;
import pl.mkuchciak.diet_app.product.ProductCategory;
import pl.mkuchciak.diet_app.product.ProductRepository;
import pl.mkuchciak.diet_app.product.ProductService;
import pl.mkuchciak.diet_app.product.nutrients.MacroNutrients;
import pl.mkuchciak.diet_app.product.nutrients.MacroNutrientsRepository;
import pl.mkuchciak.diet_app.user.User;
import pl.mkuchciak.diet_app.user.UserRepository;

import java.util.Map;

@SpringBootApplication
public class DietApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DietApplication.class, args);
        ProductRepository productRepository = context.getBean(ProductRepository.class);
        MacroNutrientsRepository macroNutrientsRepository = context.getBean(MacroNutrientsRepository.class);
        MealRepository mealRepository = context.getBean(MealRepository.class);
        UserRepository userRepository = context.getBean(UserRepository.class);
        MealService mealService = context.getBean(MealService.class);
        ProductService productService = context.getBean(ProductService.class);

        Product maslo = productService.createProduct("Masło", new MacroNutrients(83.0, 1.0, 1.0, 0.0), ProductCategory.FAT).orElseThrow();
        Product szynka = productService.createProduct("Szynka", new MacroNutrients(7.0, 1.0, 20.0, 0.0), ProductCategory.MEAT).orElseThrow();
        Product cheddar = productService.createProduct("Ser Cheddar", new MacroNutrients(25.0, 10.0, 23.0, 0.0), ProductCategory.CHEESE).orElseThrow();


        User user = new User("Mateusz", "Kuchciak", "TheMatioZyniak", "mateusz.kuchciakpl@gmail.com", "Grotto2002", 20);
        userRepository.save(user);



        Meal meal = new Meal(user);
        meal.setCategory(MealCategory.DINNER);
        meal.addProductToMeal(maslo, 120);
        meal.addProductToMeal(szynka, 10);
        meal.addProductToMeal(cheddar, 40);
        mealRepository.save(meal);

        Meal meal1 = new Meal(user);
        meal1.setCategory(MealCategory.BREAKFAST);
        meal1.addProductToMeal(szynka, 150);
        mealRepository.save(meal1);


        for (Meal foundMeal : mealRepository.findAll()) {
            System.out.println(foundMeal.getQuantities());
        }

        Map<String, Double> macrosMapByMeal = mealService.getMacrosMapByMeal(meal);
        System.out.println(macrosMapByMeal);

    }

//    @Bean
//    public javax.validation.Validator localValidatorFactoryBean() {
//        return new LocalValidatorFactoryBean();
//    }

}
