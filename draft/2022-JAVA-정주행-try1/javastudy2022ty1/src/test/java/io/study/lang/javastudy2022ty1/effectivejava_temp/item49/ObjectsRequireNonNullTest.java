package io.study.lang.javastudy2022ty1.effectivejava_temp.item49;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Objects;

public class ObjectsRequireNonNullTest {

    @Test
    @DisplayName("테스트 1")
    public void test1(){
        Objects.requireNonNull(null,"[1] 안녕하세요. test1() 에서 입력하신 null 은 허용되지 않습니다.");
    }

    @Test
    @DisplayName("테스트 2")
    public void test2(){
        Objects.requireNonNull(null,()->{return "[2] 안녕하세요. test2() 에서 입력하신 null 은 허용되지 않습니다.";});
    }
}
