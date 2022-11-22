package pl.mkuchciak.diet_app.product;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {
    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        Optional<ProductDto> productOpt = productService.getProductById(id);
        return productOpt.orElseThrow();
    }

    @PostMapping
    public String saveProduct(@RequestBody ProductDto productDto){
        productService.saveProduct(productDto);
        return "xd";
    }

}
