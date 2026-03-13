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
    var pairs = coderetreat.nextRound().pairs();
    assertThat(pairs).hasSize(2);
  }

  @Test
  void roundRerollForNewCoderetreatShouldReturnNewPairs() {
    assertThat(coderetreat.reRollRound().pairs()).hasSize(2);
  }

  @Test
  void roundRerollShouldReturnSamePairsNumber() {
    var originalRoundPairs = coderetreat.nextRound().pairs();
    var rerolledRoundPairs = coderetreat.reRollRound().pairs();
    assertThat(rerolledRoundPairs).hasSize(originalRoundPairs.size());
  }

  @Test
  void roundRerollCanReturnNewPairs() {
    var fakeRandom = new Random(1);
    coderetreat = new Coderetreat(List.of("Вася", "Петя", "Жора", "Саша"), fakeRandom);
    var originalRoundPairs = coderetreat.nextRound().pairs();
    var rerolledRoundPairs = coderetreat.reRollRound().pairs();

    assertContainsNone(originalRoundPairs, rerolledRoundPairs);
  }

  @Test
  void roundRerollCanReturnSamePairs() {
    var fakeRandom = new Random(3);
    coderetreat = new Coderetreat(List.of("Вася", "Петя", "Жора", "Саша"), fakeRandom);
    coderetreat.setDoNotRepeatPairs(false);
    var originalRoundPairs = coderetreat.nextRound().pairs();
    var rerolledRoundPairs = coderetreat.reRollRound().pairs();

    assertContainsAny(originalRoundPairs, rerolledRoundPairs);
  }

  @Test
  void pairsInNextRoundsShouldNotContainPairsFromPrevious() {
    var previousRoundsPairs = new ArrayList<Pair>();
    List<Pair> nextRoundPairs;
    while (!(nextRoundPairs = coderetreat.nextRound().pairs()).isEmpty()) {
      assertContainsNone(previousRoundsPairs, nextRoundPairs);
      previousRoundsPairs.addAll(nextRoundPairs);
    }
  }

  @Test
  void currentRoundPairsShouldBePrintedCorrectly() {
    var fakeRandom = new Random(1);
    coderetreat = new Coderetreat(List.of("Вася", "Петя", "Жора", "Саша"), fakeRandom);
    coderetreat.nextRound();

    assertThat(coderetreat.printCurrentRound()).isEqualTo(
        "#1 Pair: Жора, Петя\n"
            + "#2 Pair: Вася, Саша\n"
    );
  }

  private void assertContainsAny(List<Pair> list1, List<Pair> list2) {
    assertThat(list1).containsAnyOf(list2.toArray(Pair[]::new));
  }

  private void assertContainsNone(List<Pair> list1, List<Pair> list2) {
    assertThat(list1).doesNotContain(list2.toArray(Pair[]::new));
  }
}