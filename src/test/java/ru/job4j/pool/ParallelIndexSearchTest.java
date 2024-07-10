package ru.job4j.pool;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class ParallelIndexSearchTest {

    @Test
    void whenValueMThenIndex12() {
        String[] list = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N"};
        int left = 0;
        int right = list.length - 1;
        assertThat(ParallelIndexSearch.findIndex(left, right, list, "M")).isEqualTo(12);
    }

    @Test
    void whenValueIsNotInArray() {
        String[] list = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N"};
        int left = 0;
        int right = list.length - 1;
        assertThat(ParallelIndexSearch.findIndex(left, right, list, "Z")).isEqualTo(-1);
    }

    @Test
    void whenArrayLengthIsLessThen10ThenLineSearchAndResultIs6() {
        String[] list = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        int left = 0;
        int right = list.length - 1;
        assertThat(ParallelIndexSearch.findIndex(left, right, list, "G")).isEqualTo(6);
    }

    @Test
    void whenArrayUsersAndValueUserThen5() {
        User user = new User("F", "a@mail.ru");
        User[] list = new User[]{new User("A", "a@mail.ru"),
                new User("B", "a@mail.ru"),
                new User("C", "a@mail.ru"),
                new User("D", "a@mail.ru"),
                new User("E", "a@mail.ru"),
                user,
                new User("G", "a@mail.ru"),
                new User("H", "a@mail.ru"),
                new User("I", "a@mail.ru"),
                new User("J", "a@mail.ru"),
                new User("K", "a@mail.ru"),
                new User("L", "a@mail.ru")
        };
        int left = 0;
        int right = list.length - 1;
        assertThat(ParallelIndexSearch.findIndex(left, right, list, user)).isEqualTo(5);
    }

    @Test
    void whenArrayOfObjects() {
        User user = new User("F", "a@mail.ru");
        Object[] list = new Object[]{"A",
                new User("B", "a@mail.ru"),
                2,
                "D",
                new User("E", "a@mail.ru"),
                user,
                6,
                "H",
                8,
                9,
                "K",
                11,
                12
        };
        int left = 0;
        int right = list.length - 1;
        assertThat(ParallelIndexSearch.findIndex(left, right, list, 11)).isEqualTo(11);
    }
}