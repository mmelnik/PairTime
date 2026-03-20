package pairtime;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.HashSet;
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

    assertContainsNone(rerolledRoundPairs, originalRoundPairs);
  }

  @Test
  void roundRerollCanReturnSamePairs() {
    var fakeRandom = new Random(3);
    coderetreat = new Coderetreat(List.of("Вася", "Петя", "Жора", "Саша"), fakeRandom);
    coderetreat.setDoNotRepeatPairs(false);
    var originalRoundPairs = coderetreat.nextRound().pairs();
    var rerolledRoundPairs = coderetreat.reRollRound().pairs();

    assertContainsAny(rerolledRoundPairs, originalRoundPairs);
  }

  @Test
  void pairsInNextRoundsShouldNotContainPairsFromPrevious() {
    coderetreat.setDoNotRepeatPairs(true);

    var maxRoundCount = 3; //TODO: replace 4 with people.size(), based on formula max round count = n! / (k! * (n - k)!) / (n / 2)

    var previousRoundsPairs = new ArrayList<Pair>();
    for (int roundNumber = 0; roundNumber < maxRoundCount; roundNumber++) {
      var nextRoundPairs = coderetreat.nextRound().pairs();
      assertContainsNone(previousRoundsPairs, nextRoundPairs);
      previousRoundsPairs.addAll(nextRoundPairs);
    }
  }

  @Test
  void currentRoundPairsShouldBePrintedCorrectly() {
    coderetreat = new Coderetreat(List.of("Вася", "Петя", "Жора", "Саша"));
    coderetreat.nextRound();

    assertThat(coderetreat.printCurrentRound()).isEqualTo(
        """
        #1 Pair: Вася, Петя
        #2 Pair: Жора, Саша
        """
    );
  }

  private void assertContainsAny(List<Pair> list1, List<Pair> list2) {
    assertThat(list1).containsAnyElementsOf(list2);
  }

  private void assertContainsNone(List<Pair> list1, List<Pair> list2) {
    assertThat(list1).doesNotContainAnyElementsOf(list2);
  }
}