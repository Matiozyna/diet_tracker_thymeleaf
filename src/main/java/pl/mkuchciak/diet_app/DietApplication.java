package pl.mkuchciak.diet_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.mkuchciak.diet_app.meal.Meal;
import pl.mkuchciak.diet_app.meal.MealRepository;
import pl.mkuchciak.diet_app.meal.MealService;
import pl.mkuchciak.diet_app.product.nutrients.Nutrients;
import pl.mkuchciak.diet_app.product.nutrients.NutrientsRepository;
import pl.mkuchciak.diet_app.product.Product;
import pl.mkuchciak.diet_app.product.ProductRepository;
import pl.mkuchciak.diet_app.user.User;
import pl.mkuchciak.diet_app.user.UserRepository;

import java.util.Map;

@SpringBootApplication
public class DietApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DietApplication.class, args);
        ProductRepository productRepository = context.getBean(ProductRepository.class);
        NutrientsRepository nutrientsRepository = context.getBean(NutrientsRepository.class);
        MealRepository mealRepository = context.getBean(MealRepository.class);
        UserRepository userRepository = context.getBean(UserRepository.class);
        MealService mealService = context.getBean(MealService.class);

        Nutrients nutrients = new Nutrients(10.0, 12.0, 30.0);
        nutrientsRepository.save(nutrients);

        Product product1 = new Product("Produkt testowy 1", nutrients);
        Product product2 = new Product("Produkt testowy 2", nutrients);
        Product product3 = new Product("Produkt testowy 3", nutrients);
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);

        Nutrients undefindedNutritients = new Nutrients(10.0, 12.0, 30.0);
        nutrientsRepository.save(undefindedNutritients);
        Product undefinedMeal = new Product("undefined", undefindedNutritients);

        User user = new User("Mateusz", "Kuchciak", 20);
        userRepository.save(user);


        Meal meal = new Meal(user);
        meal.addProductToMeal(product1, 120);
        meal.addProductToMeal(product2, 10);
        meal.addProductToMeal(product3, 40);
        mealRepository.save(meal);

        Meal meal1 = new Meal(user);
        meal1.addProductToMeal(product1, 2);
        meal1.addProductToMeal(product2, 3);
        meal1.addProductToMeal(product3, 4);
        mealRepository.save(meal1);

        meal.deleteProductFromMeal(product2);
        mealRepository.save(meal);

        for (Meal foundMeal : mealRepository.findAll()) {
            System.out.println(foundMeal.getQuantities());
        }

        Map<String, Double> macrosMapByMeal = mealService.getMacrosMapByMeal(meal);
        System.out.println(macrosMapByMeal);

    }

}
