package hu.nive.ujratervezes.spamfiltering;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static hu.nive.ujratervezes.spamfiltering.WordGenerator.generateWords;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SpamFilterTest {
    @Order(1)
    @Test
    void constructorThrowsInvalidArgumentExceptionWhenWhiteListIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new SpamFilter(null, new ArrayList<>()));
    }

    @Test
    @Order(2)
    void constructorThrowsInvalidArgumentExceptionWhenBlackListIsNull() {
        assertThrows(IllegalArgumentException.class, () -> new SpamFilter(new ArrayList<>(), null));
    }

    @Test
    @Order(3)
    void testApplyRulesReturnsEmptyIfWhiteListIsEmpty() {
        List<String> w = Collections.emptyList();
        List<String> b = asList("a", "b", "c");
        SpamFilter spamFilter = new SpamFilter(w, b);

        List<List<String>> sentences = asList(
                asList("a", "x", "y"), asList("x", "y", "z"), asList("1", "2", "3")
        );
        List<List<String>> actual = spamFilter.applyRules(sentences);
        assertThat(actual).isEmpty();
    }

    @Test
    @Order(4)
    void testApplyRulesReturnsAllWhenBlackListIsEmptyAndAllInputHasWordFromWhiteList() {
        List<String> w = asList("a", "b", "c");
        List<String> b = Collections.emptyList();
        SpamFilter spamFilter = new SpamFilter(w, b);

        List<List<String>> sentences = asList(
                asList("a", "x", "y"), asList("x", "y", "c"), asList("1", "2", "b")
        );
        List<List<String>> actual = spamFilter.applyRules(sentences);
        assertThat(actual).hasSameElementsAs(sentences);
    }

    @Test
    @Order(5)
    void testApplyRulesReturnsEmptyWhenAllSentecesContainBlacklistedWords() {
        List<String> w = asList("x", "y", "z");
        List<String> b = asList("a", "b", "c");
        SpamFilter spamFilter = new SpamFilter(w, b);

        List<List<String>> sentences = asList(
                asList("a", "x", "y"), asList("x", "b", "z"), asList("c", "x", "2", "3")
        );
        List<List<String>> actual = spamFilter.applyRules(sentences);
        assertThat(actual).isEmpty();
    }

    @Test
    @Order(6)
    void testApplyRulesReturnsOnlySentencesWithWhiteListed() {
        List<String> w = asList("x", "y", "z");
        List<String> b = asList("b1", "b2", "b3");
        SpamFilter spamFilter = new SpamFilter(w, b);

        List<List<String>> sentences = asList(
                asList("a", "x", "y"), asList("b1", "b", "z"), asList("a", "z", "b", "c"),
                asList("a", "b", "c"), asList("d", "e", "f"), asList("g", "h", "i", "y")
        );

        List<List<String>> expected = asList(
                asList("a", "x", "y"), asList("a", "z", "b", "c"), asList("g", "h", "i", "y")
        );
        List<List<String>> actual = spamFilter.applyRules(sentences);
        assertThat(actual).hasSameElementsAs(expected);
    }

    @Test
    @Order(7)
    void testApplyRulesCanHandleLargerDataThusUsingTheAppropriateDataStructures() {
        List<String> w = generateWords(10_000, 6, "abcdefghijkl");
        List<String> b = generateWords(10_000, 6, "opqrstuvwxyz");

        List<List<String>> sentences = new ArrayList<>();
        for (int i = 0; i < 10_000; i++) {
            sentences.add(generateWords(100, 6, "abcdefghijkl" + "opqrstuvwxyz"));
        }

        // runs ~24s on my machine using naive solution. ~40ms with proper datastructures
        // using 2s to account for slow test environments
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            SpamFilter spamFilter = new SpamFilter(w, b);
            List<List<String>> actual = spamFilter.applyRules(sentences);
            System.out.println(actual.size());
        });
    }

    @Test
    void foo() {
        List<String> whiteList = new ArrayList<>(Arrays.asList("kutya", "cica", "aranyos"));
        List<String> blackList = new ArrayList<>(Arrays.asList("csunya", "budos", "buta"));

        SpamFilter spamFilter = new SpamFilter(whiteList, blackList);
        List<List<String>> sentences = Arrays.asList(
                Arrays.asList("a", "cica", "kedves"),
                Arrays.asList("a", "nyul", "furge"),
                Arrays.asList("a", "kutya", "budos"),
                Arrays.asList("a", "kutya", "huseges")
        );

        List<List<String>> result = spamFilter.applyRules(sentences);
        System.out.println(result);
    }
}