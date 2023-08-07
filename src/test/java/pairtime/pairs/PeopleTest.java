package pairtime.pairs;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

public class PeopleTest {

  public static final Random FAKE_RANDOM = new Random(1);

  @Test
  void noPairsAreCreatedFromZeroPeople() {
    List<Pair> people = new People(List.of()).makePairs();
    assertThat(people).isEmpty();
  }

  @Test
  void noPairsAreCreatedFromOnePeople() {
    List<Pair> people = new People(List.of("Вася")).makePairs();
    assertThat(people).isEmpty();
  }

  @Test
  void onePairIsCreatedFromTwoPeople() {
    List<Pair> people = new People(List.of("Вася", "Петя")).makePairs();
    assertThat(people)
        .hasSize(1)
        .contains(new Pair("Вася", "Петя"));
  }

  @Test
  void createdPairShouldNotConsistOfSamePerson() {
    List<Pair> people = new People(List.of("Вася", "Петя")).makePairs();
    assertThat(people)
        .isNotEmpty()
        .doesNotContain(new Pair("Вася", "Вася"), new Pair("Петя", "Петя"));
  }

  @Test
  void twoPairsAreCreatedFromFourPeople() {
    List<Pair> people = new People(List.of("Вася", "Петя", "Жора", "Саша")).makePairs();
    assertThat(people).hasSize(2);
  }

  @Test
  void peopleCreatedFromFourPeopleAreNotEqual() {
    List<Pair> people = new People(List.of("Вася", "Петя", "Жора", "Саша")).makePairs();
    assertThat(people.get(0)).isNotEqualTo(people.get(1));
  }

  @Test
  void peopleFromMoreThatTwoPeopleAreCreatedUsingRandom() {
    List<Pair> people = new People(List.of("Вася", "Петя", "Жора", "Саша"), FAKE_RANDOM)
        .makePairs();
    assertThat(people.get(0)).isNotEqualTo(new Pair("Вася", "Петя"));
  }

  @Test
  void pairIsNotCreatedIfItWasInExcludeList() {
    List<Pair> people = new People(List.of("Вася", "Петя"))
        .makePairsExcluding(List.of(new Pair("Вася", "Петя")));

    assertThat(people).isEmpty();
  }

  @Test
  void pair_Петя_Вася_IsCreatedOnly_as_Вася_Жора_pairIsExcluded() {
    List<Pair> people = new People(List.of("Вася", "Петя", "Жора"))
        .makePairsExcluding(List.of(
            new Pair("Вася", "Жора"),
            new Pair("Петя", "Жора")
        ));
    assertThat(people).hasSize(1);
    assertThat(people.get(0)).isEqualTo(new Pair("Петя", "Вася"));
  }

  @Test
  void twoPairsAreCreatedFromFourPeople_Вася_Саша_pairIsExcluded() {
    List<Pair> people = new People(List.of("Вася", "Петя", "Жора", "Саша"), FAKE_RANDOM)
        .makePairsExcluding(List.of(new Pair("Вася", "Саша")));

    assertThat(people)
        .hasSize(2)
        .doesNotContain(new Pair("Вася", "Саша"));
  }

  @Test
  void builtPairsShouldNotIncludeSamePersonTwice() {
    List<Pair> people = new People(List.of("Вася", "Петя", "Жора", "Саша")).makePairs();

    List<String> participants = people.stream()
        .flatMap(pair -> pair.getPeople().stream())
        .collect(toList());

    assertThat(participants)
        .hasSize(4)
        .containsExactlyInAnyOrder("Вася", "Петя", "Жора", "Саша");
  }
}
