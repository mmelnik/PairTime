package pairtime.pairs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Round class tests")
class RoundTest {

  @Nested
  @DisplayName("Constructor and getters tests")
  class ConstructorTests {

    @Test
    void roundNumberShouldBeStoredCorrectly() {
      int expectedNumber = 5;
      var round = new Round(expectedNumber, List.of(new Pair("A", "B")));

      assertEquals(expectedNumber, round.number());
    }
  }

  @Nested
  @DisplayName("toString() method tests")
  class ToStringTests {

    @Test
    void currentRoundPairsShouldBePrintedCorrectly() {
      var round = new Round(0, List.of(
          new Pair("Вася", "Петя"),
          new Pair("Жора", "Саша"))
      );

      assertThat(round).hasToString(
          "#1 Pair: Вася, Петя\n"
              + "#2 Pair: Жора, Саша\n"
      );
    }

    @Test
    void roundWithSinglePairShouldBePrintedCorrectly() {
      var round = new Round(3, List.of(new Pair("Один", "Два")));

      assertThat(round).hasToString("#1 Pair: Один, Два\n");
    }

    @Test
    void roundWithManyPairsShouldBePrintedCorrectly() {
      var manyPairs = new ArrayList<Pair>();
      for (int i = 0; i < 10; i++) {
        manyPairs.add(new Pair("User" + i, "Partner" + i));
      }

      var result = new Round(4, manyPairs).toString();
      var lines = result.split("\n");

      assertAll(
          () -> assertEquals(10, lines.length),
          () -> assertThat(lines[0]).startsWith("#1 Pair: User0, Partner0"),
          () -> assertThat(lines[5]).startsWith("#6 Pair: User5, Partner5"),
          () -> assertThat(lines[9]).startsWith("#10 Pair: User9, Partner9")
      );
    }

    @Test
    void roundToStringWithSpecialCharacters() {
      var round = new Round(6, List.of(
          new Pair("Имя с пробелами", "Фамилия-с-дефисом"),
          new Pair("O'Connor", "Smith-Johnson"))
      );

      var result = round.toString();

      assertAll(
          () -> assertThat(result).contains("#1 Pair: Имя с пробелами, Фамилия-с-дефисом"),
          () -> assertThat(result).contains("#2 Pair: O'Connor, Smith-Johnson")
      );
    }
  }

}