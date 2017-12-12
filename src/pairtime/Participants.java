package pairtime;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Participants<T> {

    private static final long SEED;
    private static final Random random;
    private List<T> list = new LinkedList<>();

    static {
        SEED = System.nanoTime();
        random = new Random(SEED);
    }

    private Participants() {
        // use static method instead
    }

    public static <T> Participants<T> of(T... participants) {
        Participants<T> result = new Participants<>();
        result.addParticipants(Arrays.asList(participants));
        return result;
    }

    public boolean countIsOdd() {
        return list.size() % 2 == 1;
    }

    public void addParticipants(List<T> participants) {
        this.list.addAll(participants);
    }

    public boolean hasMore() {
        return !list.isEmpty();
    }

    public T selectParticipant() {
        T participant = list.get(random.nextInt(list.size()));
        list.remove(participant);
        return participant;
    }

    public void addParticipants(Participants<T> participants) {
        this.list.addAll(participants.list);
    }
}
