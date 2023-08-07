package pairtime.pairs;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;

public class PeopleTest {

  public static final Random FAKE_RANDOM = new Random(1);

  @Test
  void noPairsAreCreatedFromZeroPeople() {
    List<Pair> people = People.of(List.of()).generatePairs();
    assertThat(people.isEmpty()).isTrue();
  }

  @Test
  void noPairsAreCreatedFromOnePeople() {
    List<Pair> people = People.of(List.of("Вася")).generatePairs();
    assertThat(people.isEmpty()).isTrue();
  }

  @Test
  void onePairIsCreatedFromTwoPeople() {
    List<Pair> people = People.of(List.of("Вася", "Петя")).generatePairs();
    assertThat(people.size()).isEqualTo(1);
    assertThat(people.get(0)).isEqualTo(new Pair("Вася", "Петя"));
    assertThat(people.get(0)).isEqualTo(new Pair("Петя", "Вася"));
  }

  @Test
  void createdPairsShouldConsistOfSamePerson() {
    List<Pair> people = People.of(List.of("Вася", "Петя")).generatePairs();
    assertThat(people.get(0)).isNotEqualTo(new Pair("Вася", "Вася"));
    assertThat(people.get(0)).isNotEqualTo(new Pair("Петя", "Петя"));
  }

  @Test
  void twoPairsAreCreatedFromFourPeople() {
    List<Pair> people = People.of(List.of("Вася", "Петя", "Жора", "Саша")).generatePairs();
    assertThat(people.size()).isEqualTo(2);
  }

  @Test
  void peopleCreatedFromFourPeopleAreNotEqual() {
    List<Pair> people = People.of(List.of("Вася", "Петя", "Жора", "Саша")).generatePairs();
    assertThat(people.get(0)).isNotEqualTo(people.get(1));
  }

  @Test
  void peopleFromMoreThatTwoPeopleAreCreatedUsingRandom() {
    List<Pair> people = People.of(List.of("Вася", "Петя", "Жора", "Саша"))
        .withRandom(FAKE_RANDOM)
        .generatePairs();
    assertThat(people.get(0)).isNotEqualTo(new Pair("Вася", "Петя"));
  }

  @Test
  void pairIsNotCreatedIfItWasInExcludeList() {
    List<Pair> people = People.of(List.of("Вася", "Петя"))
        .excludePairs(List.of(new Pair("Вася", "Петя")))
        .generatePairs();

    assertThat(people.size()).isEqualTo(0);
  }

  @Test
  void pair_Петя_Вася_IsCreatedOnly_as_Вася_Жора_pairIsExcluded() {
    List<Pair> people = People.of(List.of("Вася", "Петя", "Жора"))
        .excludePairs(List.of(
            new Pair("Вася", "Жора"),
            new Pair("Петя", "Жора")
        )).generatePairs();
    assertThat(people.size()).isEqualTo(1);
    assertThat(people.get(0)).isEqualTo(new Pair("Петя", "Вася"));
  }

  @Test
  void builtPairsShouldNotIncludeSamePersonTwice() {
    List<Pair> people = People.of(List.of("Вася", "Петя", "Жора", "Саша")).generatePairs();

    List<String> participants = people.stream()
        .flatMap(pair -> pair.getPeople().stream())
        .collect(toList());

    assertAll(
        () -> assertThat(participants.size()).isEqualTo(4),
        () -> assertThat(participants.contains("Вася")).isTrue(),
        () -> assertThat(participants.contains("Петя")).isTrue(),
        () -> assertThat(participants.contains("Жора")).isTrue(),
        () -> assertThat(participants.contains("Саша")).isTrue()
    );
  }
}
