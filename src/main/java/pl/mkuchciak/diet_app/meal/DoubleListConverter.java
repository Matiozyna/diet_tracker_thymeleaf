package pl.mkuchciak.diet_app.meal;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Converter
public class DoubleListConverter implements AttributeConverter<List<Double>, String> {

    @Override
    public String convertToDatabaseColumn(List<Double> list) {
        // Java 8
        List<String> convertedToStringList = list.stream().
                map(Object::toString)
                .toList();
        return String.join(",", convertedToStringList);
    }

    @Override
    public List<Double> convertToEntityAttribute(String joined) {
        List<String> listOfStrings = new ArrayList<>(Arrays.asList(joined.split(",")));
        return listOfStrings.stream()
                .map(Double::valueOf)
                .toList();
    }

}