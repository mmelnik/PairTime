package pairtime.pairs;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

class RoundTest {

  @Test
  void currentRoundPairsShouldBePrintedCorrectly() {

    var round = new Round(0, List.of(
        new Pair("Вася", "Петя"),
        new Pair("Жора", "Саша"))
    );

    assertThat(round).hasToString(
        "#1 Pair: Вася, Петя\n"
            + "#2 Pair: Жора, Саша\n"
    );
  }
}