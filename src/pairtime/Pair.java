package pairtime;

/**
 * @author Mihail Melnik
 */
public class Pair<T> {

    private final T first;
    private final T second;

    public Pair(T first, T second) {
        this.first = first;
        this.second = second;
    }

    public Participants<T> getParticipants() {
        return Participants.of(first, second);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair pair = (Pair) o;

        if (!first.equals(pair.first)) {
            return first.equals(pair.second) && second.equals(pair.first);
        }
        return second.equals(pair.second);
    }

    @Override
    public int hashCode() {
        return first.hashCode() + second.hashCode();
    }

    @Override
    public String toString() {
        return "{" + first + ", " + second + '}';
    }
}
