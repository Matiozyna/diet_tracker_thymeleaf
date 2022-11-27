package pl.mkuchciak.diet_app.product;

import org.springframework.stereotype.Service;
import pl.mkuchciak.diet_app.product.nutrients.MacroNutrients;
import pl.mkuchciak.diet_app.product.nutrients.MacroNutrientsRepository;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProductDtoConverter {

    MacroNutrientsRepository macroNutrientsRepository;

    public ProductDtoConverter(MacroNutrientsRepository macroNutrientsRepository) {
        this.macroNutrientsRepository = macroNutrientsRepository;
    }

    public ProductDto convertToDto(Product product){
        ProductDto productDto = new ProductDto();

        HashMap<String, Double> nutrients = new HashMap<>();
        nutrients.put("fat", product.getMacroNutrients().getFat());
        nutrients.put("carbohydrates", product.getMacroNutrients().getCarbohydrates());
        nutrients.put("protein", product.getMacroNutrients().getProtein());
        nutrients.put("fiber", product.getMacroNutrients().getFiber());
        nutrients.put("calories", product.getMacroNutrients().getCalories());
        productDto.setName(product.getName());

        productDto.setCategory(productDto.getCategory());
        productDto.setId(product.getId());
        productDto.setNutrients(nutrients);
        return productDto;
    }

    public Product convertToEntity(ProductDto productDto){
        Product product = new Product();
        product.setName(productDto.getName());
        MacroNutrients macroNutrients = generateNutrients(productDto.getNutrients());
        product.setMacroNutrients(macroNutrients);
        product.setCategory(productDto.getCategory());
        return product;
    }

    private MacroNutrients generateNutrients(Map<String, Double> nutrientsMap){
        MacroNutrients macroNutrients = new MacroNutrients();
        macroNutrients.setFat(nutrientsMap.get("fat"));
        macroNutrients.setCarbohydrates(nutrientsMap.get("carbohydrates"));
        macroNutrients.setProtein(nutrientsMap.get("protein"));
        macroNutrients.setFiber(nutrientsMap.get("fiber"));
        macroNutrients.setCalories(nutrientsMap.get("calories"));
        macroNutrientsRepository.save(macroNutrients);
        return macroNutrients;
    }
}
