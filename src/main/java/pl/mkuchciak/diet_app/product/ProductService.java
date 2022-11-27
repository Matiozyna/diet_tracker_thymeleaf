package pl.mkuchciak.diet_app.product;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mkuchciak.diet_app.product.nutrients.MacroNutrients;
import pl.mkuchciak.diet_app.product.nutrients.MacroNutrientsRepository;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductService {
    private ProductRepository productRepository;
    private MacroNutrientsRepository macroNutrientsRepository;
    private ProductDtoConverter productDtoConverter;
    private Validator validator;


    public ProductService(ProductRepository productRepository, MacroNutrientsRepository macroNutrientsRepository, ProductDtoConverter productDtoConverter, Validator validator) {
        this.productRepository = productRepository;
        this.macroNutrientsRepository = macroNutrientsRepository;
        this.productDtoConverter = productDtoConverter;
        this.validator = validator;
    }

    public Optional<ProductDto> saveProduct(ProductDto productDto){
        Product product = productDtoConverter.convertToEntity(productDto);
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        if(violations.isEmpty()){
            productRepository.save(product);
            Product savedProduct = productRepository.save(product);
            return Optional.of(productDtoConverter.convertToDto(savedProduct));
        }else{
            violations.forEach(System.out::println);
            macroNutrientsRepository.delete(product.getMacroNutrients());
            return Optional.empty();
        }

    }

    public Optional<ProductDto> getProductById(Long id){
        return productRepository.findById(id)
                .map(product -> productDtoConverter.convertToDto(product));
    }

    public Optional<Product> getProductByName(String name){
        return productRepository.findByNameIgnoreCase(name);
    }

    public List<ProductDto> getAllProducts() {
        List<Product> products = new ArrayList<>();
        productRepository.findAll().forEach(products::add);
        return products.stream().map(productDtoConverter::convertToDto).toList();
    }

    public Optional<ProductDto> deleteProductById(Long id) {
        if(productRepository.existsById(id)) {
            Optional<ProductDto> productDtoOptional = getProductById(id);
            productRepository.deleteById(id);
            return productDtoOptional;
        }
        return Optional.empty();
    }

    @Transactional
    public Optional<Product> createProduct(String name, MacroNutrients macroNutrients, ProductCategory category){

        if(macroNutrients.getId() != null){
            if(!macroNutrientsRepository.existsById(macroNutrients.getId())){
                macroNutrientsRepository.save(macroNutrients);
                System.out.println("Saved nutrients");
            }
        }else{
            macroNutrientsRepository.save(macroNutrients);
            System.out.println("Saved nutrients");
        }

        Product product = new Product(name, macroNutrients, category);
        Set<ConstraintViolation<Product>> violations = validator.validate(product);
        if(violations.isEmpty()){
            productRepository.save(product);
            return Optional.of(product);
        }else{
            violations.forEach(System.out::println);
            macroNutrientsRepository.delete(macroNutrients);
            return Optional.empty();
        }

    }
}
