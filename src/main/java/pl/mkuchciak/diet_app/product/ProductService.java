package pl.mkuchciak.diet_app.product;

import org.springframework.stereotype.Service;
import pl.mkuchciak.diet_app.product.nutrients.NutrientsRepository;

import java.util.Optional;

@Service
public class ProductService {
    ProductRepository productRepository;
    NutrientsRepository nutrientsRepository;
    ProductDtoConverter productDtoConverter;

    public ProductService(ProductRepository productRepository, NutrientsRepository nutrientsRepository, ProductDtoConverter productDtoConverter) {
        this.productRepository = productRepository;
        this.nutrientsRepository = nutrientsRepository;
        this.productDtoConverter = productDtoConverter;
    }

    public void saveProduct(ProductDto productDto){
        Product product = productDtoConverter.convertToEntity(productDto);
        productRepository.save(product);
    }

    public Optional<ProductDto> getProductById(Long id){
        return productRepository.findById(id)
                .map(product -> productDtoConverter.convertToDto(product));
    }

    public Optional<Product> getProductByName(String name){
        return productRepository.findByName(name);
    }
}
