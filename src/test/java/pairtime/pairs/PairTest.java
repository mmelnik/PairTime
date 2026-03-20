package pairtime.pairs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pair class tests")
class PairTest {

  @Nested
  @DisplayName("Constructor and getters tests")
  class ConstructorTests {

    @Test
    @DisplayName("Should create pair with driver and navigator")
    void shouldCreatePairWithDriverAndNavigator() {
      var pair = new Pair("John", "Alice");

      assertEquals("John", pair.driver());
      assertEquals("Alice", pair.navigator());
    }

    @Test
    @DisplayName("Should throw error when driver and navigator is same person")
    void pair_constructor_prevents_self_pairing() {
      assertThatThrownBy(() -> new Pair("Вася", "Вася"))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessageContaining("same person");
    }
  }

  @Nested
  @DisplayName("getPeople() method tests")
  class GetPeopleTests {

    @Test
    @DisplayName("Should return list containing both driver and navigator")
    void shouldReturnListWithBothPeople() {
      var pair = new Pair("John", "Alice");
      var people = pair.getPeople();

      assertEquals(2, people.size());
      assertTrue(people.contains("John"));
      assertTrue(people.contains("Alice"));
    }

    @Test
    @DisplayName("Should return immutable list")
    void shouldReturnImmutableList() {
      var pair = new Pair("John", "Alice");
      var people = pair.getPeople();

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
  }

  @Nested
  @DisplayName("equals() method tests")
  class EqualsTests {

    @Test
    @DisplayName("Should return true when comparing same object")
    void shouldReturnTrueWhenSameObject() {
      var pair = new Pair("John", "Alice");
      assertEquals(pair, pair);
    }

    @Test
    @DisplayName("Should return true when pairs have same order")
    void shouldReturnTrueWhenSameOrder() {
      var pair1 = new Pair("John", "Alice");
      var pair2 = new Pair("John", "Alice");

      assertEquals(pair1, pair2);
    }

    @Test
    @DisplayName("Should return true when pairs have reversed order")
    void shouldReturnTrueWhenReversedOrder() {
      var pair1 = new Pair("John", "Alice");
      var pair2 = new Pair("Alice", "John");

      assertEquals(pair1, pair2);
    }

    @Test
    @DisplayName("Should return false when pairs are different")
    void shouldReturnFalseWhenDifferent() {
      var pair1 = new Pair("John", "Alice");
      var pair2 = new Pair("John", "Bob");
      var pair3 = new Pair("Bob", "Alice");

      assertNotEquals(pair1, pair2);
      assertNotEquals(pair1, pair3);
    }

    @Test
    @DisplayName("Should return false when comparing with null")
    void shouldReturnFalseWhenComparedWithNull() {
      var pair = new Pair("John", "Alice");
      assertNotEquals(null, pair);
    }

    @Test
    @DisplayName("Should return false when comparing with different class")
    void shouldReturnFalseWhenDifferentClass() {
      var pair = new Pair("John", "Alice");
      var notAPair = "not a pair";

      assertNotEquals(notAPair, pair);
    }

    @Test
    @DisplayName("Should handle pairs with null or empty strings")
    void shouldHandlePairsWithEmptyStrings() {
      assertAll(() -> {
        assertThrows(IllegalArgumentException.class, () -> new Pair(null, "notNull"));
        assertThrows(IllegalArgumentException.class, () -> new Pair("notNull", null));
        assertThrows(IllegalArgumentException.class, () -> new Pair(null, null));
        assertThrows(IllegalArgumentException.class, () -> new Pair("", "notEmpty"));
        assertThrows(IllegalArgumentException.class, () -> new Pair("notEmpty", ""));
        assertThrows(IllegalArgumentException.class, () -> new Pair("", ""));
      });
    }
  }

  @Nested
  @DisplayName("hashCode() method tests")
  class HashCodeTests {

    @Test
    @DisplayName("Should have same hashcode for equal pairs regardless of order")
    void shouldHaveSameHashcodeForEqualPairs() {
      var pair1 = new Pair("John", "Alice");
      var pair2 = new Pair("John", "Alice");
      var pair3 = new Pair("Alice", "John");

      assertEquals(pair1.hashCode(), pair2.hashCode());
      assertEquals(pair1.hashCode(), pair3.hashCode());
    }

    @Test
    @DisplayName("Should have different hashcodes for different pairs")
    void shouldHaveDifferentHashcodesForDifferentPairs() {
      var pair1 = new Pair("John", "Alice");
      var pair2 = new Pair("John", "Bob");

      assertNotEquals(pair1.hashCode(), pair2.hashCode());
    }

    @Test
    @DisplayName("Hashcode should be sum of driver and navigator hashcodes")
    void hashcodeShouldBeSumOfDriverAndNavigatorHashcodes() {
      var pair = new Pair("John", "Alice");

      var expectedHashCode = "John".hashCode() + "Alice".hashCode();
      assertEquals(expectedHashCode, pair.hashCode());
    }
  }

  @Nested
  @DisplayName("toString() method tests")
  class ToStringTests {

    @Test
    @DisplayName("Should return formatted string with driver and navigator")
    void shouldReturnFormattedString() {
      var pair = new Pair("John", "Alice");
      var expected = "Pair: John, Alice";

      assertEquals(expected, pair.toString());
    }
  }

}