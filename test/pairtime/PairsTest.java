package pairtime;

import static java.util.Collections.singleton;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author Mihail Melnik
 */
public class PairsTest {

    public static final int FIRST = 0;

    @Test
    public void onePairIsCreatedFromTwoPeople() {
        List<Pair> actual = Pairs.from(Participants.of("Петя", "Федя"), new HashSet<>());
        assertThat(actual.size(), is(1));
    }

    @Test
    @Ignore
    public void pairIsNotAddedToResultIfItIsSkipped() {
        Set<Pair<String>> skipped = singleton(
                new Pair<>("Петя", "Федя")
        );
        List<Pair> actual = Pairs.from(Participants.of("Петя", "Федя"), skipped);
        assertThat(actual.size(), is(0));
    }

    @Test
    @Ignore
    public void pairIsNotAddedToResultIfItIsSkippedInDifferentOrder() {
        Set<Pair<String>> skipped = singleton(
                new Pair<>("Федя", "Петя")
        );
        List<Pair> actual = Pairs.from(Participants.of("Петя", "Федя"), skipped);
        assertThat(actual.size(), is(0));
    }

    @Test
    public void onePairIsCreatedFromThreePeople() {
        List<Pair> actual = Pairs.from(Participants.of("Петя", "Федя", "Вася", "Миша"), new HashSet<>());
        assertThat(actual.size(), is(2));
    }

    @Test
    @Ignore
    public void onlyOnePairIsPossibleFromThreePeopleWhenTwoAreSkipped() {
        Set<Pair<String>> skipped = new HashSet<>(Arrays.asList(
                new Pair<>("Петя", "Федя"),
                new Pair<>("Петя", "Вася")
        ));
        List<Pair> actual = Pairs.from(Participants.of("Петя", "Федя", "Вася"), skipped);
        assertThat(actual.size(), is(1));
        assertThat(actual.get(FIRST), is(new Pair<>("Федя", "Вася")));
    }

}
