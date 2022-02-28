package hu.nive.ujratervezes.spamfiltering;

import java.util.*;

public class SpamFilter {
    private List<String> whiteList;    // legalább egynek elő kell fordulnia
    private List<String> blackList;    // egy sem fordulhat elő

    public SpamFilter(List<String> whiteList, List<String> blackList) {
        this.whiteList = whiteList;
        this.blackList = blackList;
        if (whiteList == null || blackList == null) {
            throw new IllegalArgumentException();
        }
    }

    public List<List<String>> applyRules(List<List<String>> sentences) {
        List<List<String>> result = new ArrayList<>();
        result = atLeastOne(whiteList, sentences);
        result = notOne(blackList,result);


        return result;
    }

    private List<List<String>> atLeastOne(List<String> whiteList, List<List<String>> sentences) {
        List<List<String>> result = new ArrayList<>();
        for (int i = 0; i < sentences.size(); i++) {
            List<String> rowSentence = sentences.get(i);
            for (String white : whiteList) {
                if (rowSentence.contains(white)) {
                    result.add(rowSentence);
                }
            }
        }
        return result;
    }

    private List<List<String>> notOne(List<String> blackList, List<List<String>> sentences) {
        List<List<String>> result = new ArrayList<>();
        for (int i = 0; i < sentences.size(); i++) {
            boolean in = true;
            List<String> rowSentence = sentences.get(i);
            for (String black : blackList) {
                if (rowSentence.contains(black)) {
                    in = false;
                    break;
                }
            }
            if (in) {
                result.add(rowSentence);
            }
        }
        return result;
    }


}
