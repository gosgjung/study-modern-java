package io.study.lang.javastudy2022ty1.effectivejava_temp.item55;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class OptionalFlatMapTest {
    @Test
    public void TEST_OPTIONAL_FLATMAP_CASE1(){
        Optional<String> hello1 = Optional.of("Hello1");
        Optional<Optional<String>> hello2 = Optional.of(hello1);

        Optional<String> result = hello2.flatMap(optHello -> optHello.map(String::toUpperCase));
        System.out.println(result);

        assertThat(result).isNotEmpty();
    }

    @Test
    public void TEST_OPTIONAL_FLATMAP_CASE2(){
        Optional<Double> calorie = Optional.of(1.1D);
        Optional<Optional<Double>> calorie2 = Optional.of(calorie);
        Optional<Optional<Optional<Double>>> calorie3 = Optional.of(calorie2);

        System.out.println(calorie3);

        Optional<Double> calorieData1 = calorie3.flatMap(optOptCalorie -> optOptCalorie.flatMap(
                optCalorie -> {
                    if (optCalorie.isPresent()) {
                        return Optional.of(optCalorie.get() * 1000);
                    }
                    return Optional.empty();
                })
        );

        System.out.println(calorieData1);

        Optional<Double> calorieData2 = calorie3.flatMap(optOptCalorie -> optOptCalorie.flatMap(
                optCalorie -> {
                    return optCalorie.map(aDouble -> aDouble * 1000);
                })
        );

        System.out.println(calorieData2);
    }

    @Test
    @DisplayName("null 값을 flatMap 으로 돌릴 경우의 예제")
    public void TEST_OPTIONAL_FLATMAP_NULL_VALUE(){
        Optional<Double> emptyCalorie = Optional.ofNullable(null);
        Optional<Double> flatMappedEmptyCalorie = emptyCalorie.flatMap(calorie -> Optional.of(Double.MAX_VALUE));

        System.out.println(flatMappedEmptyCalorie);
    }
}
