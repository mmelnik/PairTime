package pairtime.pairs;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

public class PeopleTest {

  @Test
  void _0_pairs_created_from_0_people() {
    var pairs = new People(List.of()).makePairs();

    assertThat(pairs).isEmpty();
  }

  @Test
  void _0_pairs_created_from_1_person() {
    var pairs = new People(List.of("Вася")).makePairs();

    assertThat(pairs).isEmpty();
  }

  @Test
  void _1_pair_created_from_2_people() {
    var pairs = new People(List.of("Вася", "Петя")).makePairs();

    assertThat(pairs)
        .hasSize(1)
        .contains(new Pair("Вася", "Петя"));
  }

  @Test
  void _2_pairs_created_from_4_people() {
    var pairs = new People(List.of("Вася", "Петя", "Жора", "Саша")).makePairs();

    assertThat(pairs).hasSize(2);
  }

  @Test
  void _2_pairs_created_from_4_people_when_1_pair_excluded() {
    var pairs = new People(List.of("Вася", "Петя", "Жора", "Саша"))
            .makePairsWithExclusions(List.of(new Pair("Вася", "Саша")));

    assertThat(pairs)
            .hasSize(2)
            .doesNotContain(new Pair("Вася", "Саша"));
  }

  @Test
  void pairs_created_from_4_people_are_not_equal() {
    var pairs = new People(List.of("Вася", "Петя", "Жора", "Саша")).makePairs();

    assertThat(pairs.get(0)).isNotEqualTo(pairs.get(1));
  }

  @Test
  void _0_pairs_created_from_2_people_when_their_pair_excluded() {
    var pairs = new People(List.of("Вася", "Петя"))
        .makePairsWithExclusions(List.of(new Pair("Вася", "Петя")));

    assertThat(pairs).isEmpty();
  }

  @Test
  void _1_pair_from_3_people_created_when_1_pair_excluded() {
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

  @Test
  void pairs_not_include_same_person_twice() {
    var pairs = new People(List.of("Вася", "Петя", "Жора", "Саша")).makePairs();
    var participants = pairs.stream()
        .flatMap(pair -> pair.getPeople().stream())
        .collect(toList());

    assertThat(participants)
        .hasSize(4)
        .containsExactlyInAnyOrder("Вася", "Петя", "Жора", "Саша");
  }
}
