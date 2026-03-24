package pairtime.pairs;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
    void _0_pairs_created_from_even_people_count_when_all_pairs_excluded() {
      var actualPairs = new People(List.of("Вася", "Петя"))
              .makePairsWithExclusions(List.of(new Pair("Вася", "Петя")));

      assertThat(actualPairs).isEmpty();
    }

    @Test
    void makes_all_possible_pairs_from_even_people_count_when_1_pair_excluded() {
      var pairs = new People(List.of("Вася", "Петя", "Жора", "Саша"))
              .makePairsWithExclusions(List.of(new Pair("Вася", "Саша")));

      assertThat(pairs)
              .hasSize(2)
              .doesNotContain(new Pair("Вася", "Саша"));
    }

    @Test
    void makes_all_possible_pairs_from_odd_people_count_when_1_pair_excluded() {
      var pairs = new People(List.of("Вася", "Петя", "Жора"))
          .makePairsWithExclusions(List.of(
              new Pair("Вася", "Жора"),
              new Pair("Петя", "Жора")
          ));

      assertThat(pairs).hasSize(1);
      assertThat(pairs.getFirst()).isEqualTo(new Pair("Петя", "Вася"));
    }

    @Test
    void result_does_not_contain_excluded_pairs() {
      var excludedPairs = List.of(
          new Pair("Вася", "Жора"),
          new Pair("Петя", "Жора")
      );
      var pairs = new People(List.of("Вася", "Петя", "Жора")).makePairsWithExclusions(excludedPairs);

      assertThat(pairs).doesNotContainAnyElementsOf(excludedPairs);
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