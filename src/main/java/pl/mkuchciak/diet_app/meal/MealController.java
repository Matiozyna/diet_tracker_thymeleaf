package pl.mkuchciak.diet_app.meal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatchException;
import com.github.fge.jsonpatch.mergepatch.JsonMergePatch;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mkuchciak.diet_app.product.Product;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@CrossOrigin
public class MealController {
    MealService mealService;
    ObjectMapper objectMapper;

    public MealController(MealService mealService, ObjectMapper objectMapper) {
        this.mealService = mealService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/meal/{id}/macros")
    Map<String, Double> getMacrosOfMeal(@PathVariable Long id){
        return  mealService.getMacrosMapByMealId(id);
    }

    @GetMapping("/meal/today")
    ResponseEntity<List<MealDto>> getAllTodayMeals(){
        return ResponseEntity.ok(mealService.getAllTodayMeals());
    }

    @GetMapping("/meal/{id}/products")
    List<Product> getProductsOfMeal(@PathVariable Long id){
        return mealService.getProductsListByMealId(id);
    }
    
    @GetMapping("/meal/{id}")
    MealDto getMealById(@PathVariable Long id){
        return mealService.getMealById(id);
    }

    @PostMapping("/meal")
    String saveMeal(@RequestBody MealDto mealDto){
        mealService.saveMeal(mealDto);
        return "added";
    }


    @PutMapping("/meal/{id}")
    ResponseEntity<MealDto> replaceMeal(@RequestBody MealDto mealDto, @PathVariable Long id){
        Optional<MealDto> mealDtoOptional = mealService.replaceMeal(mealDto, id);
        return mealDtoOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/meal/{id}")
    ResponseEntity<?> updateMeal(@RequestBody JsonMergePatch patch, @PathVariable Long id){
        try {
            MealDto mealDto = mealService.getMealById(id);
            MealDto mealPatched = applyPatch(mealDto, patch);
            mealService.updateMeal(mealPatched);
        }catch(NoSuchElementException e){
            return ResponseEntity.notFound().build();
        } catch (JsonProcessingException | JsonPatchException e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.noContent().build();
    }

    private MealDto applyPatch(MealDto mealDto, JsonMergePatch patch) throws JsonPatchException, JsonProcessingException {
        JsonNode mealNode = objectMapper.valueToTree(mealDto);
        JsonNode mealPatchedNode = patch.apply(mealNode);
        return objectMapper.treeToValue(mealPatchedNode, MealDto.class);
    }

    @DeleteMapping("/meal/{id}")
    ResponseEntity<Long> deleteMeal(@PathVariable Long id){
        boolean mealDeleted = mealService.deleteMealById(id);
        if(mealDeleted){
            return ResponseEntity.ok(id);
        }
        return ResponseEntity.notFound().build();
    }
}
