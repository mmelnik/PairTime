package pairtime.pairs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pair class tests")
class PairTest {

  @Nested
  @DisplayName("Constructor and getters tests")
  class ConstructorTests {

    @Test
    @DisplayName("Should create pair with driver and navigator")
    void shouldCreatePairWithDriverAndNavigator() {
      Pair pair = new Pair("John", "Alice");

      assertEquals("John", pair.driver());
      assertEquals("Alice", pair.navigator());
    }
  }

  @Nested
  @DisplayName("getPeople() method tests")
  class GetPeopleTests {

    @Test
    @DisplayName("Should return list containing both driver and navigator")
    void shouldReturnListWithBothPeople() {
      Pair pair = new Pair("John", "Alice");
      List<String> people = pair.getPeople();

      assertEquals(2, people.size());
      assertTrue(people.contains("John"));
      assertTrue(people.contains("Alice"));
    }

    @Test
    @DisplayName("Should return immutable list")
    void shouldReturnImmutableList() {
      Pair pair = new Pair("John", "Alice");
      List<String> people = pair.getPeople();

      assertThrows(UnsupportedOperationException.class, () -> people.add("Bob"));
    }
  }

  @Nested
  @DisplayName("contains() method tests")
  class ContainsTests {
    private final Pair pair = new Pair("John", "Alice");

    @Test
    @DisplayName("Should return true when person is driver")
    void shouldReturnTrueWhenPersonIsDriver() {
      assertTrue(pair.contains("John"));
    }

    @Test
    @DisplayName("Should return true when person is navigator")
    void shouldReturnTrueWhenPersonIsNavigator() {
      assertTrue(pair.contains("Alice"));
    }

    @Test
    @DisplayName("Should return false when person is not in pair")
    void shouldReturnFalseWhenPersonNotInPair() {
      assertFalse(pair.contains("Bob"));
    }

    @Test
    @DisplayName("Should return false when person is null")
    void shouldReturnFalseWhenPersonIsNull() {
      assertFalse(pair.contains(null));
    }

    @Test
    @DisplayName("Should handle empty strings correctly")
    void shouldHandleEmptyStrings() {
      Pair emptyNamesPair = new Pair("", "");
      assertTrue(emptyNamesPair.contains(""));
      assertFalse(emptyNamesPair.contains(" "));
    }
  }

  @Nested
  @DisplayName("equals() method tests")
  class EqualsTests {

    @Test
    @DisplayName("Should return true when comparing same object")
    void shouldReturnTrueWhenSameObject() {
      Pair pair = new Pair("John", "Alice");
      assertEquals(pair, pair);
    }

    @Test
    @DisplayName("Should return true when pairs have same order")
    void shouldReturnTrueWhenSameOrder() {
      Pair pair1 = new Pair("John", "Alice");
      Pair pair2 = new Pair("John", "Alice");

      assertEquals(pair1, pair2);
    }

    @Test
    @DisplayName("Should return true when pairs have reversed order")
    void shouldReturnTrueWhenReversedOrder() {
      Pair pair1 = new Pair("John", "Alice");
      Pair pair2 = new Pair("Alice", "John");

      assertEquals(pair1, pair2);
    }

    @Test
    @DisplayName("Should return false when pairs are different")
    void shouldReturnFalseWhenDifferent() {
      Pair pair1 = new Pair("John", "Alice");
      Pair pair2 = new Pair("John", "Bob");
      Pair pair3 = new Pair("Bob", "Alice");

      assertNotEquals(pair1, pair2);
      assertNotEquals(pair1, pair3);
    }

    @Test
    @DisplayName("Should return false when comparing with null")
    void shouldReturnFalseWhenComparedWithNull() {
      Pair pair = new Pair("John", "Alice");
      assertNotEquals(null, pair);
    }

    @Test
    @DisplayName("Should return false when comparing with different class")
    void shouldReturnFalseWhenDifferentClass() {
      Pair pair = new Pair("John", "Alice");
      String notAPair = "not a pair";

      assertNotEquals(pair, notAPair);
    }

    @Test
    @DisplayName("Should handle pairs with empty strings")
    void shouldHandlePairsWithEmptyStrings() {
      Pair pair1 = new Pair("", "");
      Pair pair2 = new Pair("", "");
      Pair pair3 = new Pair("", "value");

      assertEquals(pair1, pair2);
      assertNotEquals(pair1, pair3);
    }
  }

  @Nested
  @DisplayName("hashCode() method tests")
  class HashCodeTests {

    @Test
    @DisplayName("Should have same hashcode for equal pairs regardless of order")
    void shouldHaveSameHashcodeForEqualPairs() {
      Pair pair1 = new Pair("John", "Alice");
      Pair pair2 = new Pair("John", "Alice");
      Pair pair3 = new Pair("Alice", "John");

      assertEquals(pair1.hashCode(), pair2.hashCode());
      assertEquals(pair1.hashCode(), pair3.hashCode());
    }

    @Test
    @DisplayName("Should have different hashcodes for different pairs")
    void shouldHaveDifferentHashcodesForDifferentPairs() {
      Pair pair1 = new Pair("John", "Alice");
      Pair pair2 = new Pair("John", "Bob");

      assertNotEquals(pair1.hashCode(), pair2.hashCode());
    }

    @Test
    @DisplayName("Hashcode should be sum of driver and navigator hashcodes")
    void hashcodeShouldBeSumOfDriverAndNavigatorHashcodes() {
      Pair pair = new Pair("John", "Alice");

      int expectedHashCode = "John".hashCode() + "Alice".hashCode();
      assertEquals(expectedHashCode, pair.hashCode());
    }

    @Test
    @DisplayName("Should handle empty strings in hashcode calculation")
    void shouldHandleEmptyStringsInHashcode() {
      Pair pair1 = new Pair("", "");
      Pair pair2 = new Pair("", "");

      assertEquals(pair1.hashCode(), pair2.hashCode());
      assertEquals(0, "".hashCode() + "".hashCode());
    }
  }

  @Nested
  @DisplayName("toString() method tests")
  class ToStringTests {

    @Test
    @DisplayName("Should return formatted string with driver and navigator")
    void shouldReturnFormattedString() {
      Pair pair = new Pair("John", "Alice");
      String expected = "Pair: John, Alice";

      assertEquals(expected, pair.toString());
    }

    @Test
    @DisplayName("Should handle empty strings in toString")
    void shouldHandleEmptyStrings() {
      Pair pair = new Pair("", "");
      String expected = "Pair: , ";

      assertEquals(expected, pair.toString());
    }
  }
}