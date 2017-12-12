package pairtime;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Mihail Melnik
 */
public class Pairs {

    private Pairs() {
        // use static method instead
    }

    public static <T> List<Pair> from(Participants<T> participants, Set<Pair<T>> skipped) {
        List<Pair> pairs = new ArrayList<>();

        if (participants.countIsOdd()) {
            // TODO: add some exception or logic here
        }

        while (participants.hasMore()) {
            Pair<T> pair = new Pair<>(participants.selectParticipant(), participants.selectParticipant());
            if (skipped.contains(pair)) {
                participants.addParticipants(pair.getParticipants());
            }
            pairs.add(pair);
        }

        return pairs;
    }

}
