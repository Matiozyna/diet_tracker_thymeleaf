package pl.mkuchciak.diet_app.meal;

import org.springframework.stereotype.Service;
import pl.mkuchciak.diet_app.product.Product;
import pl.mkuchciak.diet_app.product.ProductService;
import pl.mkuchciak.diet_app.user.UserService;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class MealDtoConverter {

    ProductService productService;
    UserService userService;

    public MealDtoConverter(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    public MealDto convertToDto(Meal meal){
        MealDto mealDto = new MealDto();
        mealDto.setId(meal.getId());
        mealDto.setUserId(meal.getUser().getId());
        mealDto.setProducts(
                meal.getProducts().stream()
                        .map(Product::getName)
                        .toList()
        );
        mealDto.setQuantities(meal.getQuantities());
        mealDto.setDate(meal.getDate());
        mealDto.setCategory(meal.getCategory());
        return mealDto;
    }

    public Meal convertToEntity(MealDto mealDto){
        System.out.println("Convert To entity");
        Meal meal = new Meal();
        if(mealDto.getId()!=null){
            meal.setId(mealDto.getId());
        }
        if(mealDto.getDate()!=null){
            meal.setDate(meal.getDate());
        }
        meal.setDate(LocalDate.now());
        meal.setUser(
                userService.getUserById(mealDto.getUserId())
                        .orElseThrow()
        );

        for (int i = 0; i < mealDto.getProducts().size(); i++) {
            Optional<Product> productByName = productService.getProductByName(mealDto.getProducts().get(i));
            if(productByName.isPresent()){
                meal.addProductToMeal(productByName.get(), mealDto.getQuantities().get(i));
            }
        }
        meal.setCategory(mealDto.getCategory());
        return meal;
    }
}
