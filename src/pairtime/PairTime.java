package pairtime;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PairTime {

    public static void main(String[] people) {
        if (people == null || people.length == 0) {
            people = new String[]{
                    "Петя", "Федя", "Вася", "Миша"
            };
        }

        Set<Pair<String>> skipped = new HashSet<>(Collections.emptyList());
        List<Pair> pairs = Pairs.from(Participants.of(people), skipped);

        System.out.println(pairs);
    }

}
