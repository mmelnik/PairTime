package pairtime.pairs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
      var round = new Round(expectedNumber, Set.of(new Pair("A", "B")));

      assertEquals(expectedNumber, round.number());
    }

    @Test
    void pairsListShouldBeStoredCorrectly() {
      var expectedPairs = new HashSet<>(Set.of(
          new Pair("Alice", "Bob"),
          new Pair("Charlie", "Diana")
      ));

      var actual = new Round(1, expectedPairs).pairs();

      assertThat(actual)
          .isEqualTo(expectedPairs)
          .isNotSameAs(expectedPairs);
    }

    @Test
    void roundCannotHaveNullPairs() {
      assertThrows(IllegalArgumentException.class, () -> new Round(2, null));
    }

    @Test
    void roundCannotHaveEmptyPairs() {
      var pairs = Set.<Pair>of();
      assertThrows(IllegalArgumentException.class, () -> new Round(2, pairs));
    }

    @Test
    void roundPairsShouldNotReactOnOriginalListMutation() {
      var originalPairs = new HashSet<>(Set.of(new Pair("A", "B")));
      var round = new Round(1, originalPairs);

      originalPairs.add(new Pair("C", "D"));

      assertThat(round.pairs())
              .hasSize(1)
              .doesNotContain(new Pair("C", "D"));
    }
  }

  @Nested
  @DisplayName("toString() method tests")
  class ToStringTests {

    @Test
    void roundWithSinglePairShouldBePrintedCorrectly() {
      var round = new Round(3, Set.of(new Pair("Один", "Два")));

      assertThat(round).hasToString("#1 Pair: Один, Два\n");
    }

    @Test
    void roundWithManyPairsShouldBePrintedCorrectly() {
      var manyPairs = new HashSet<Pair>();
      var expectedStrings = new ArrayList<String>();

      for (int pairIndex = 0; pairIndex < 10; pairIndex++) {
        manyPairs.add(new Pair("User" + pairIndex, "Partner" + pairIndex));
        expectedStrings.add("#" + (pairIndex + 1));
        expectedStrings.add("Pair: " + "User" + pairIndex + ", Partner" + pairIndex);
      }

      var actualString = new Round(4, manyPairs).toString();

      assertThat(actualString).contains(expectedStrings);
      assertThat(actualString.split("\n")).hasSize(10);
    }

    @Test
    void roundToStringWithSpecialCharacters() {
      var round = new Round(6, Set.of(
          new Pair("Имя с пробелами", "Фамилия-с-дефисом"),
          new Pair("O'Connor", "Smith-Johnson"))
      );

      var result = round.toString();

      assertAll(
          () -> assertThat(result).contains("Pair: Имя с пробелами, Фамилия-с-дефисом"),
          () -> assertThat(result).contains("Pair: O'Connor, Smith-Johnson")
      );
    }
  }

}