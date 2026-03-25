package pairtime.pairs;

import static java.util.Collections.emptySet;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class PeopleTest {

  @Nested
  @DisplayName("makePairs() method tests")
  class MakePairsTests {

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void makePairs_creates_max_possible_count_of_pairs(int size) {
      var actualPairs = new People(makeListOfPeople(size)).makePairs();

      assertThat(actualPairs).hasSize(size / 2);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void pairs_not_include_same_person_twice(int size) {
      var actualParticipants = new People(makeListOfPeople(size)).makePairs().stream()
              .flatMap(p -> p.getPeople().stream())
              .toList();

      assertThat(actualParticipants).doesNotHaveDuplicates();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void pairs_made_from_people_in_original_list(int size) {
      var expectedParticipants = makeListOfPeople(size);

      var actualParticipants = new People(expectedParticipants).makePairs().stream()
              .flatMap(p -> p.getPeople().stream())
              .toList();

      assertThat(actualParticipants).isSubsetOf(expectedParticipants);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void pairs_does_not_have_duplicates(int size) {
      var actualPairs = new People(makeListOfPeople(size)).makePairs();

      assertThat(actualPairs).doesNotHaveDuplicates();
    }

    @Test
    void makePairs_method_does_not_mutate_original_list() {
      var originalList = Arrays.asList("Вася", "Петя", "Жора", "Саша");
      var originalListCopy = new ArrayList<>(originalList);

      new People(originalList).makePairs();

      assertThat(originalList).isEqualTo(originalListCopy);
    }
  }

  @Nested
  @DisplayName("makePairsWithExclusions() method tests")
  class MakePairsWithExclusionsTests {

    @Test
    void makePairsWithExclusions_handles_null() {
      var people = new People(List.of("Вася", "Петя", "Жора"));
      var expectedParticipants = people.makePairs();

      var actualParticipants = people.makePairsWithExclusions(null);

      assertThat(actualParticipants).isEqualTo(expectedParticipants);
    }

    @Test
    void makePairsWithExclusions_handles_empty_list() {
      var people = new People(List.of("Вася", "Петя", "Жора"));
      var expectedParticipants = people.makePairs();

      var actualParticipants = people.makePairsWithExclusions(emptySet());

      assertThat(actualParticipants).isEqualTo(expectedParticipants);
    }

    @Test
    void _0_pairs_created_from_even_people_count_when_all_pairs_excluded() {
      var actualPairs = new People(List.of("Вася", "Петя"))
              .makePairsWithExclusions(Set.of(new Pair("Вася", "Петя")));

      assertThat(actualPairs).isEmpty();
    }

    @Test
    void makes_all_possible_pairs_from_even_people_count_when_1_pair_excluded() {
      var people = new People(List.of("Вася", "Петя", "Жора", "Саша"));
      var excludedPairs = List.of(
          new Pair("Вася", "Петя"),
          new Pair("Вася", "Жора"),
          new Pair("Вася", "Саша"),
          new Pair("Петя", "Жора"),
          new Pair("Петя", "Саша"),
          new Pair("Жора", "Саша")
      );

      excludedPairs.forEach(excludedPair -> {
        var actualParticipants = people.makePairsWithExclusions(Set.of(excludedPair));

        assertThat(actualParticipants)
                .hasSize(2)
                .doesNotContain(excludedPair);
      });
    }

    @Test
    void makes_all_possible_pairs_from_even_people_count_when_multiple_pair_excluded() {
      var people = new People(List.of("Вася", "Петя", "Жора", "Саша", "Маша", "Иван"));

      Stream.of(
          Set.of(new Pair("Вася", "Петя"), new Pair("Жора", "Саша"), new Pair("Маша", "Иван")),
          Set.of(new Pair("Вася", "Жора"), new Pair("Петя", "Маша"), new Pair("Саша", "Иван")),
          Set.of(new Pair("Вася", "Иван"), new Pair("Петя", "Саша"), new Pair("Жора", "Маша"))
      ).forEach(excludedPairs -> {
        var actualPairs = people.makePairsWithExclusions(excludedPairs);

        assertThat(actualPairs)
                .hasSize(3)
                .doesNotContainAnyElementsOf(excludedPairs);
      });
    }

    @Test
    void _0_pairs_created_from_odd_people_count_when_all_pairs_excluded() {
      var people = new People(List.of("Вася", "Петя", "Жора"));
      var allPossibleExcludedPairCombination = Set.of(
          new Pair("Вася", "Петя"),
          new Pair("Вася", "Жора"),
          new Pair("Петя", "Жора")
      );

      var actualPairs = people.makePairsWithExclusions(allPossibleExcludedPairCombination);

      assertThat(actualPairs).isEmpty();
    }

    @Test
    void makes_all_possible_pairs_from_odd_people_count_when_1_pair_excluded() {
      var people = new People(List.of("Вася", "Петя", "Жора"));

      Stream.of(
          new Pair("Вася", "Петя"),
          new Pair("Вася", "Жора"),
          new Pair("Петя", "Жора")
      ).forEach(excludedPair -> {
        var actualPairs = people.makePairsWithExclusions(Set.of(excludedPair));

        assertThat(actualPairs)
                .hasSize(1)
                .doesNotContain(excludedPair);
      });
    }

    @Test
    void makes_all_possible_pairs_from_odd_people_count_when_multiple_pair_excluded() {
      var people = new People(List.of("Вася", "Петя", "Жора"));

      Stream.of(
          Set.of(new Pair("Жора", "Вася"), new Pair("Жора", "Петя")),
          Set.of(new Pair("Вася", "Петя"), new Pair("Вася", "Жора")),
          Set.of(new Pair("Петя", "Вася"), new Pair("Петя", "Жора"))
      ).forEach(excludedPairs -> {
        var actualPairs = people.makePairsWithExclusions(excludedPairs);

        assertThat(actualPairs).doesNotContainAnyElementsOf(excludedPairs);
        assertThat(actualPairs)
            .overridingErrorMessage("Expected size 1, but was %d for excluded pairs: %s", actualPairs.size(), excludedPairs)
            .hasSize(1);
      });
    }

    @Test
    void makePairsWithExclusions_method_does_not_mutate_original_list() {
      var originalList = Arrays.asList("Вася", "Петя", "Жора", "Саша");
      var originalListCopy = new ArrayList<>(originalList);

      new People(originalList).makePairsWithExclusions(Set.of(new Pair("Вася", "Жора")));

      assertThat(originalList).isEqualTo(originalListCopy);
    }
  }

  @Nested
  @DisplayName("shuffle() method tests")
  class ShuffleTests {

    @Test
    void shuffle_randomly_reorders_people() {
      var fixedRandom = new Random(42);
      var people = new People(List.of("Вася", "Петя", "Жора", "Саша", "Маша", "Иван"), fixedRandom);

      for (int tryNumber = 0; tryNumber < 10; tryNumber++) {
        var expectedParticipants = people.getValue();
        people.shuffle();
        var actualParticipants = people.getValue();

        assertThat(actualParticipants)
                .isNotEqualTo(expectedParticipants)
                .containsExactlyInAnyOrderElementsOf(expectedParticipants);
      }
    }

    @Test
    void shuffle_with_fixed_random_produces_deterministic_order() {
      var people1 = new People(List.of("Вася", "Петя", "Жора", "Саша"), new Random(42));
      var people2 = new People(List.of("Вася", "Петя", "Жора", "Саша"), new Random(42));

      people1.shuffle();
      people2.shuffle();

      assertThat(people1.getValue()).isEqualTo(people2.getValue());
    }

    @Test
    void shuffle_method_does_not_mutate_original_list() {
      var originalList = Arrays.asList("Вася", "Петя", "Жора", "Саша");
      var originalListCopy = new ArrayList<>(originalList);

      new People(originalList).shuffle();

      assertThat(originalList).isEqualTo(originalListCopy);
    }
  }

  private List<String> makeListOfPeople(int size) {
    var result = new ArrayList<String>();
    for (int number = 0; number < size; number++) {
      result.add("Person" + number);
    }
    return result;
  }

}