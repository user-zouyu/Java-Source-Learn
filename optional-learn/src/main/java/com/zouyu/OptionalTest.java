package com.zouyu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * @author ZouYu 2023/4/1 16:32
 * @version 1.0.0
 */

@SuppressWarnings("all")
public class OptionalTest {

    private static final int DefaultValue = 1;
    Optional<Integer> nonNullOptional = Optional.of(DefaultValue);
    Optional<Integer> nullOptional = Optional.ofNullable(null);

    @Test
    void emptyTest() {
        Optional<Object> optional = Optional.empty();

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            optional.get();
        });
    }

    @Test
    void ofTest() {
        Optional<Integer> optional = Optional.of(DefaultValue);
        Assertions.assertEquals(DefaultValue, optional.get());

        Assertions.assertThrows(NullPointerException.class,() -> {
            Optional.of(null);
        });
    }

    @Test
    void ofNullableTest() {
        Optional<Object> optional = Optional.ofNullable(null);

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            optional.get();
        });
    }

    @Test
    void getTest() {
        Assertions.assertEquals(DefaultValue, nonNullOptional.get());
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            nullOptional.get();
        });
    }

    @Test
    void orElseTest() {
        Assertions.assertEquals(DefaultValue, nonNullOptional.orElse(100));
        Assertions.assertEquals(100, nullOptional.orElse(100));
    }

    @Test
    void orElseGetTest() {
        Assertions.assertEquals(DefaultValue, nonNullOptional.orElseGet(() -> 100));
        Assertions.assertEquals(100, nullOptional.orElseGet(() -> 100));
    }

    @Test
    void orElseThrowTest() throws IllegalAccessException {
        Assertions.assertEquals(DefaultValue, nonNullOptional.orElseThrow(IllegalAccessException::new));
        Assertions.assertThrows(IllegalAccessException.class, () -> {
            nullOptional.orElseThrow(IllegalAccessException::new);
        });
    }

    @Test
    void isPresentTest() {
        Assertions.assertTrue(nonNullOptional.isPresent());
        Assertions.assertFalse(nullOptional.isPresent());
    }

    @Test
    void ifPresentTest() {
        nonNullOptional.ifPresent((o) -> {
            System.out.println(o);
        });

        nullOptional.ifPresent((o) -> {
            System.out.println(o);
        });
    }

    @Test
    void filterTest() {
        Assertions.assertEquals(DefaultValue, nonNullOptional.filter(o -> o == DefaultValue).get());
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            nonNullOptional.filter(o -> o != DefaultValue).get();
        });

    }


    @Test
    void mapTest() {
        Assertions.assertEquals(DefaultValue*2, nonNullOptional.map(o -> o * 2).get());
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            nullOptional.map(o -> o * 2).get();
        });
    }

    @Test
    void flatMapTest() {
        Assertions.assertEquals(DefaultValue*2, nonNullOptional.flatMap(o -> Optional.of(o * 2)).get());

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            nonNullOptional.flatMap(o -> Optional.empty()).get();
        });

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            nullOptional.flatMap(o -> Optional.of(o * 2)).get();
        });
    }

}
