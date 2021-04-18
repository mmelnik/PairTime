package pairtime.pairs;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;

public class PairsTest {

  public static final Random FAKE_RANDOM = new Random(1);

  @Test
  void noPairsAreCreatedFromZeroPeople() {
    List<Pair<String>> pairs = Pairs.of(List.of()).generate();
    assertThat(pairs.isEmpty()).isTrue();
  }

  @Test
  void noPairsAreCreatedFromOnePeople() {
    List<Pair<String>> pairs = Pairs.of(List.of("Вася")).generate();
    assertThat(pairs.isEmpty()).isTrue();
  }

  @Test
  void onePairIsCreatedFromTwoPeople() {
    List<Pair<String>> pairs = Pairs.of(List.of("Вася", "Петя")).generate();
    assertThat(pairs.size()).isEqualTo(1);
    assertThat(pairs.get(0)).isEqualTo(new Pair<>("Вася", "Петя"));
    assertThat(pairs.get(0)).isEqualTo(new Pair<>("Петя", "Вася"));
  }

  @Test
  void createdPairsShouldConsistOfSamePerson() {
    List<Pair<String>> pairs = Pairs.of(List.of("Вася", "Петя")).generate();
    assertThat(pairs.get(0)).isNotEqualTo(new Pair<>("Вася", "Вася"));
    assertThat(pairs.get(0)).isNotEqualTo(new Pair<>("Петя", "Петя"));
  }

  @Test
  void twoPairsAreCreatedFromFourPeople() {
    List<Pair<String>> pairs = Pairs.of(List.of("Вася", "Петя", "Жора", "Саша")).generate();
    assertThat(pairs.size()).isEqualTo(2);
  }

  @Test
  void pairsCreatedFromFourPeopleAreNotEqual() {
    List<Pair<String>> pairs = Pairs.of(List.of("Вася", "Петя", "Жора", "Саша")).generate();
    assertThat(pairs.get(0)).isNotEqualTo(pairs.get(1));
  }

  @Test
  void pairsFromMoreThatTwoPeopleAreCreatedUsingRandom() {
    List<Pair<String>> pairs = Pairs.of(List.of("Вася", "Петя", "Жора", "Саша"))
        .withRandom(FAKE_RANDOM)
        .generate();
    assertThat(pairs.get(0)).isNotEqualTo(new Pair<>("Вася", "Петя"));
  }

  @Test
  void pairIsNotCreatedIfItWasInExcludeList() {
    List<Pair<String>> pairs = Pairs.of(List.of("Вася", "Петя"))
        .excludePairs(List.of(new Pair<>("Вася", "Петя")))
        .generate();

    assertThat(pairs.size()).isEqualTo(0);
  }

  @Test
  void pair_Петя_Вася_IsCreatedOnly_as_Вася_Жора_pairIsExcluded() {
    List<Pair<String>> pairs = Pairs.of(List.of("Вася", "Петя", "Жора"))
        .excludePairs(List.of(
            new Pair<>("Вася", "Жора"),
            new Pair<>("Петя", "Жора")
        )).generate();
    assertThat(pairs.size()).isEqualTo(1);
    assertThat(pairs.get(0)).isEqualTo(new Pair<>("Петя", "Вася"));
  }

  @Test
  void builtPairsShouldNotIncludeSamePersonTwice() {
    List<Pair<String>> pairs = Pairs.of(List.of("Вася", "Петя", "Жора", "Саша")).generate();

    List<String> participants = pairs.stream()
        .flatMap(pair -> pair.getParticipants().stream())
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
