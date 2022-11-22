package pl.mkuchciak.diet_app.product;

import org.springframework.stereotype.Service;
import pl.mkuchciak.diet_app.product.nutrients.Nutrients;
import pl.mkuchciak.diet_app.product.nutrients.NutrientsRepository;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProductDtoConverter {

    NutrientsRepository nutrientsRepository;

    public ProductDtoConverter(NutrientsRepository nutrientsRepository) {
        this.nutrientsRepository = nutrientsRepository;
    }

    public ProductDto convertToDto(Product product){
        ProductDto productDto = new ProductDto();

        HashMap<String, Double> nutrients = new HashMap<>();
        nutrients.put("fat", product.getNutrients().getFat());
        nutrients.put("carbohydrates", product.getNutrients().getCarbohydrates());
        nutrients.put("protein", product.getNutrients().getProtein());
        nutrients.put("fiber", product.getNutrients().getFiber());
        nutrients.put("calories", product.getNutrients().getCalories());
        productDto.setName(product.getName());

        productDto.setId(product.getId());
        productDto.setNutrients(nutrients);
        return productDto;
    }

    public Product convertToEntity(ProductDto productDto){
        Product product = new Product();
        product.setName(productDto.getName());
        Nutrients nutrients = generateNutrients(productDto.getNutrients());
        product.setNutrients(nutrients);
        return product;
    }

    private Nutrients generateNutrients(Map<String, Double> nutrientsMap){
        Nutrients nutrients = new Nutrients();
        nutrients.setFat(nutrientsMap.get("fat"));
        nutrients.setCarbohydrates(nutrientsMap.get("carbohydrates"));
        nutrients.setProtein(nutrientsMap.get("protein"));
        nutrients.setFiber(nutrientsMap.get("fiber"));
        nutrients.setCalories(nutrientsMap.get("calories"));
        nutrientsRepository.save(nutrients);
        return nutrients;
    }
}
