package pl.mkuchciak.diet_app.meal;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MealDto {
    private Long id;
    private Long userId;
    private List<String> products;
    private List<Double> quantities;
    private LocalDateTime date;
}
