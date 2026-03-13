package pairtime.pairs;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

public class PeopleTest {

  public static final Random FAKE_RANDOM = new Random(1);

  @Test
  void _0_pairs_created_from_0_people() {
    List<Pair> people = new People(List.of()).makePairs();
    assertThat(people).isEmpty();
  }

  @Test
  void _0_pairs_created_from_1_person() {
    List<Pair> people = new People(List.of("Вася")).makePairs();
    assertThat(people).isEmpty();
  }

  @Test
  void _1_pair_created_from_2_people() {
    List<Pair> people = new People(List.of("Вася", "Петя")).makePairs();
    assertThat(people)
        .hasSize(1)
        .contains(new Pair("Вася", "Петя"));
  }

  @Test
  void pairs_not_consist_of_1_person() {
    List<Pair> people = new People(List.of("Вася", "Петя")).makePairs();
    assertThat(people)
        .isNotEmpty()
        .doesNotContain(new Pair("Вася", "Вася"), new Pair("Петя", "Петя"));
  }

  @Test
  void _2_pairs_created_from_4_people() {
    List<Pair> people = new People(List.of("Вася", "Петя", "Жора", "Саша")).makePairs();
    assertThat(people).hasSize(2);
  }

  @Test
  void pairs_created_from_4_people_are_not_equal() {
    List<Pair> people = new People(List.of("Вася", "Петя", "Жора", "Саша")).makePairs();
    assertThat(people.get(0)).isNotEqualTo(people.get(1));
  }

  @Test
  void pairs_created_using_random() {
    List<Pair> people = new People(List.of("Вася", "Петя", "Жора", "Саша"), FAKE_RANDOM)
        .makePairs();
    assertThat(people.get(0)).isNotEqualTo(new Pair("Вася", "Петя"));
  }

  @Test
  void _0_pairs_created_from_2_people_when_their_pair_excluded() {
    List<Pair> people = new People(List.of("Вася", "Петя"))
        .makePairsWithExclusions(List.of(new Pair("Вася", "Петя")));

    assertThat(people).isEmpty();
  }

  @Test
  void _1_pair_from_3_people_created_when_1_pair_excluded() {
    List<Pair> people = new People(List.of("Вася", "Петя", "Жора"))
        .makePairsWithExclusions(List.of(
            new Pair("Вася", "Жора"),
            new Pair("Петя", "Жора")
        ));
    assertThat(people).hasSize(1);
    assertThat(people.get(0)).isEqualTo(new Pair("Петя", "Вася"));
  }

  @Test
  void _2_pairs_created_from_4_people_when_1_pair_excluded() {
    List<Pair> people = new People(List.of("Вася", "Петя", "Жора", "Саша"), FAKE_RANDOM)
        .makePairsWithExclusions(List.of(new Pair("Вася", "Саша")));

    assertThat(people)
        .hasSize(2)
        .doesNotContain(new Pair("Вася", "Саша"));
  }

  @Test
  void pairs_not_include_same_person_twice() {
    List<Pair> people = new People(List.of("Вася", "Петя", "Жора", "Саша")).makePairs();

    List<String> participants = people.stream()
        .flatMap(pair -> pair.getPeople().stream())
        .collect(toList());

    assertThat(participants)
        .hasSize(4)
        .containsExactlyInAnyOrder("Вася", "Петя", "Жора", "Саша");
  }
}
