package hu.nive.ujratervezes.spamfiltering;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordGenerator {
    public static final Random RAND = new Random();

    static List<String> generateWords(int howMany, int howLong, String fromCharacters) {
        List<String> generatedWords = new ArrayList<>(howMany);
        while (howMany-- > 0) {
            generatedWords.add(generateWord(howLong, fromCharacters));
        }
        return generatedWords;
    }

    private static String generateWord(int howLong, String fromCharacters) {
        StringBuilder b = new StringBuilder(howLong);
        while (howLong-- > 0) {
            int index = RAND.nextInt(fromCharacters.length());
            char c = fromCharacters.charAt(index);
            b.append(c);
        }
        return b.toString();
    }
}
