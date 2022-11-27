package pl.mkuchciak.diet_app.meal;

import org.springframework.data.repository.CrudRepository;
import pl.mkuchciak.diet_app.user.User;

import java.time.LocalDate;
import java.util.List;

public interface MealRepository extends CrudRepository<Meal, Long> {
    void deleteAllByUserId(Long id);
    void deleteAllByUser(User user);

    List<Meal> findAllByDate(LocalDate localDate);
}
