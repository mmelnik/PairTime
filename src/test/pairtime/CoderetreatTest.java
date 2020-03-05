package pairtime;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.assertj.core.api.ListAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pairtime.pairs.Pair;

public class CoderetreatTest {

  private Coderetreat coderetreat;

  @BeforeEach
  void setUp() {
    coderetreat = new Coderetreat(List.of("Вася", "Петя", "Жора", "Саша"));
  }

  @Test
  void currentPairListShouldBeEmptyForNewCoderetreat() {
    assertThat(coderetreat.getCurrentRoundPairs()).isEmpty();
  }

  @Test
  void shufflingPairsForNewCoderetreatShouldReturnEmptyList() {
    assertThat(coderetreat.shufflePairs()).isEmpty();
  }

  @Test
  void shouldBeTwoPairsForNextRoundOfFourPeople() {
    List<Pair<String>> pairs = coderetreat.buildNewRoundPairs();
    assertThat(pairs.size()).isEqualTo(2);
  }

  @Test
  void shufflingPairsShouldReturnSamePairsNumber() {
    List<Pair<String>> original = coderetreat.buildNewRoundPairs();
    List<Pair<String>> shuffled = coderetreat.shufflePairs();
    assertThat(shuffled.size()).isEqualTo(original.size());
  }

  @Test
  void shufflingCanReturnNewPairs() {
    Random fakeRandom = new Random(1);
    coderetreat = new Coderetreat(List.of("Вася", "Петя", "Жора", "Саша"), fakeRandom);
    List<Pair<String>> original = coderetreat.buildNewRoundPairs();
    List<Pair<String>> shuffled = coderetreat.shufflePairs();

    assertContainsNone(original, shuffled);
  }

  @Test
  void shufflingCanReturnSamePairs() {
    Random fakeRandom = new Random(900);
    coderetreat = new Coderetreat(List.of("Вася", "Петя", "Жора", "Саша"), fakeRandom);
    List<Pair<String>> original = coderetreat.buildNewRoundPairs();
    List<Pair<String>> shuffled = coderetreat.shufflePairs();

    assertContainsAny(original, shuffled);
  }

  private ListAssert assertContainsAny(List<Pair<String>> original, List<Pair<String>> shuffled) {
    return assertThat(original).containsAnyOf(shuffled.toArray(Pair[]::new));
  }

  @Test
  void pairsInNextRoundsShouldNotContainPairsFromPrevious() {
    List<Pair<String>> previous = new ArrayList<>();
    List<Pair<String>> next;
    while (!(next = coderetreat.buildNewRoundPairs()).isEmpty()) {
      assertContainsNone(previous, next);
      previous.addAll(next);
    }
  }

  private ListAssert assertContainsNone(List<Pair<String>> previous, List<Pair<String>> next) {
    return assertThat(previous).doesNotContain(next.toArray(Pair[]::new));
  }

  @Test
  void currentRoundPairsShouldBePrintedCorreclty() {
    Random fakeRandom = new Random(1);
    coderetreat = new Coderetreat(List.of("Вася", "Петя", "Жора", "Саша"), fakeRandom);
    coderetreat.buildNewRoundPairs();

    assertThat(coderetreat.printCurrentRoundPairs()).isEqualTo(
        "#1 Pair: Жора, Петя\n"
            + "#2 Pair: Вася, Саша\n"
    );
  }
}