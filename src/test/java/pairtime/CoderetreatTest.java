package pairtime;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pairtime.pairs.Pair;

class CoderetreatTest {

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
  void shouldBeTwoPairsForNextRoundOfFourPeople() {
    List<Pair> pairs = coderetreat.buildNewRoundPairs();
    assertThat(pairs.size()).isEqualTo(2);
  }

  @Test
  void shufflingPairsForNewCoderetreatShouldReturnNewPairs() {
    assertThat(coderetreat.shufflePairs()).hasSize(2);
  }

  @Test
  void shufflingPairsShouldReturnSamePairsNumber() {
    List<Pair> original = coderetreat.buildNewRoundPairs();
    List<Pair> shuffled = coderetreat.shufflePairs();
    assertThat(shuffled.size()).isEqualTo(original.size());
  }

  @Test
  void shufflingCanReturnNewPairs() {
    Random fakeRandom = new Random(1);
    coderetreat = new Coderetreat(List.of("Вася", "Петя", "Жора", "Саша"), fakeRandom);
    List<Pair> original = coderetreat.buildNewRoundPairs();
    List<Pair> shuffled = coderetreat.shufflePairs();

    assertContainsNone(original, shuffled);
  }

  @Test
  void shufflingCanReturnSamePairs() {
    Random fakeRandom = new Random(900);
    coderetreat = new Coderetreat(List.of("Вася", "Петя", "Жора", "Саша"), fakeRandom);
    coderetreat.setDoNotRepeatPairs(false);
    List<Pair> original = coderetreat.buildNewRoundPairs();
    List<Pair> shuffled = coderetreat.shufflePairs();

    assertContainsAny(original, shuffled);
  }

  @Test
  void pairsInNextRoundsShouldNotContainPairsFromPrevious() {
    List<Pair> previous = new ArrayList<>();
    List<Pair> next;
    while (!(next = coderetreat.buildNewRoundPairs()).isEmpty()) {
      assertContainsNone(previous, next);
      previous.addAll(next);
    }
  }

  @Test
  void currentRoundPairsShouldBePrintedCorrectly() {
    Random fakeRandom = new Random(1);
    coderetreat = new Coderetreat(List.of("Вася", "Петя", "Жора", "Саша"), fakeRandom);
    coderetreat.buildNewRoundPairs();

    assertThat(coderetreat.printCurrentRoundPairs()).isEqualTo(
        "#1 Pair: Жора, Петя\n"
            + "#2 Pair: Вася, Саша\n"
    );
  }

  private void assertContainsAny(List<Pair> original, List<Pair> shuffled) {
    assertThat(original).containsAnyOf(shuffled.toArray(Pair[]::new));
  }

  private void assertContainsNone(List<Pair> previous, List<Pair> next) {
    assertThat(previous).doesNotContain(next.toArray(Pair[]::new));
  }
}