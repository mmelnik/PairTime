package pairtime.pairs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashMap;
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
      var expectedNumber = 5;

      var actualNumber = new Round(expectedNumber, Set.of(new Pair("A", "B"))).number();

      assertThat(actualNumber).isEqualTo(expectedNumber);
    }

    @Test
    void roundNumberCannotBeZeroOrNegativeIntegerNumber() {
      var pairs = Set.of(new Pair("A", "B"));
      assertAll(
          () -> assertThrows(IllegalArgumentException.class, () -> new Round(0, pairs)),
          () -> assertThrows(IllegalArgumentException.class, () -> new Round(-1, pairs)),
          () -> assertThrows(IllegalArgumentException.class, () -> new Round(Integer.MIN_VALUE, pairs))
      );
    }

    @Test
    void roundNumberCanBeAnyPositiveIntegerNumber() {
      assertDoesNotThrow(() -> new Round(1, Set.of(new Pair("A", "B"))));
      assertDoesNotThrow(() -> new Round(Integer.MAX_VALUE, Set.of(new Pair("A", "B"))));
    }

    @Test
    void roundWithVeryLargeNumberShouldWork() {
      var actual = new Round(Integer.MAX_VALUE, Set.of(new Pair("A", "B"))).number();

      assertThat(actual).isEqualTo(Integer.MAX_VALUE);
    }

    @Test
    void pairsListShouldBeStoredCorrectly() {
      var expectedPairs = new HashSet<>(Set.of(
          new Pair("Alice", "Bob"),
          new Pair("Charlie", "Diana")
      ));

      var actualPairs = new Round(1, expectedPairs).pairs();

      assertThat(actualPairs)
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
    void roundWithLotOfPairsShouldWork() {
      var manyPairs = new HashSet<Pair>();
      for (int number = 0; number < 1000; number++) {
        manyPairs.add(new Pair("Driver" + number, "Navigator" + number));
      }

      assertDoesNotThrow(() -> new Round(1, manyPairs));
    }

    @Test
    void roundWithNullInPairsSetShouldThrow() {
      var pairsWithNull = new HashSet<Pair>();
      pairsWithNull.add(new Pair("A", "B"));
      pairsWithNull.add(null);

      assertThrows(NullPointerException.class, () -> new Round(1, pairsWithNull));
    }

    @Test
    void innerPairsShouldNotReactOnOriginalListMutation() {
      var originalPairs = new HashSet<>(Set.of(new Pair("A", "B")));
      var round = new Round(1, originalPairs);

      originalPairs.add(new Pair("C", "D"));
      var actualPairs = round.pairs();

      assertThat(actualPairs)
              .hasSize(1)
              .doesNotContain(new Pair("C", "D"));
    }
  }

  @Nested
  @DisplayName("Round behavior tests")
  class RoundBehaviorTests {

    @Test
    void roundsWithSameNumberAndDifferentPairsShouldBeDifferent() {
      var sameNumber = 1;

      var round1 = new Round(sameNumber, Set.of(new Pair("A", "B")));
      var round2 = new Round(sameNumber, Set.of(new Pair("C", "D")));

      assertThat(round1).isNotEqualTo(round2);
    }

    @Test
    void roundsWithDifferentNumbersAndSamePairsShouldBeDifferent() {
      var samePairs = Set.of(new Pair("A", "B"));

      var round1 = new Round(1, samePairs);
      var round2 = new Round(2, samePairs);

      assertThat(round1).isNotEqualTo(round2);
    }

    @Test
    void roundsWithSameNumberAndEqualPairsShouldBeEqual() {
      var sameNumber = 1;
      var samePairs = Set.of(new Pair("A", "B"));

      var round1 = new Round(sameNumber, samePairs);
      var round2 = new Round(sameNumber, new HashSet<>(samePairs));

      assertThat(round1).isEqualTo(round2);
      assertThat(round1.hashCode()).hasSameHashCodeAs(round2.hashCode());
    }

    @Test
    void recordHashCodeShouldBeConsistent() {
      var actual = new Round(1, Set.of(new Pair("A", "B")));

      assertThat(actual).hasSameHashCodeAs(actual);
    }

    @Test
    void recordShouldBeUsableAsMapKey() {
      var round1 = new Round(1, Set.of(new Pair("A", "B")));
      var round2 = new Round(1, Set.of(new Pair("A", "B")));
      var round3 = new Round(2, Set.of(new Pair("A", "B")));

      var actual = new HashMap<>();
      actual.put(round1, "First");

      assertAll(
          () -> assertThat(actual).containsEntry(round2, "First"),
          () -> assertThat(actual).doesNotContainKey(round3)
      );
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

    @Test
    void roundToStringWithUnicodeCharacters() {
      var pairs = Set.of(
          new Pair("🐱", "🐶"),
          new Pair("😊", "🎉")
      );

      var actual = new Round(1, pairs).toString();

      assertThat(actual).contains("🐱", "🐶", "😊", "🎉");
    }

    @Test
    void toStringShouldNotModifyRoundState() {
      var round = new Round(1, Set.of(new Pair("A", "B")));
      var originalToString = round.toString();

      round.toString(); // Call again

      assertThat(round).hasToString(originalToString);
    }

    @Test
    void toStringShouldNumberPairsSequentially() {
      var pairs = Set.of(
          new Pair("1", "2"),
          new Pair("3", "4"),
          new Pair("5", "6")
      );

      var actual = new Round(1, pairs).toString();

      assertThat(actual).contains("#1 ", "#2 ", "#3 ");
    }
  }

  @Nested
  @DisplayName("Pairs integrity tests")
  class PairsIntegrityTests {

    @Test
    void pairsSetReturnedByGetterShouldBeUnmodifiable() {
      var round = new Round(1, Set.of(new Pair("A", "B")));

      var actual = round.pairs();

      var pair = new Pair("C", "D");
      assertThrows(UnsupportedOperationException.class, () -> actual.add(pair));
    }

    @Test
    void multipleCallsToGetterShouldReturnConsistentViews() {
      var round = new Round(1, Set.of(new Pair("A", "B")));

      var pairs1 = round.pairs();
      var pairs2 = round.pairs();

      assertThat(pairs1).isSameAs(pairs2);
    }
  }
}