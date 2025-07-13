package org.userbot;

import java.util.*;
import java.util.regex.*;

public class LeadAnalyzer {

    private static Set<String> positiveWords;
    private static Set<String> negativeWords;
    private static Set<String> urgencyWords;
    private static Set<String> locationWords;

    public static void loadDictionaries() {
        positiveWords = KeywordLoader.loadKeywords("data/positive.csv");
        negativeWords = KeywordLoader.loadKeywords("data/negative.csv");
        urgencyWords = KeywordLoader.loadKeywords("data/urgency.csv");
        locationWords = KeywordLoader.loadKeywords("data/location.csv");
    }

    public static int analyze(String message) {
        loadDictionaries();
        if (message == null || message.isBlank()) return 1;
        message = message.toLowerCase(Locale.ROOT);

        String[] words = message.split("[\\s\\p{Punct}]+");
        int score = 0;
        

        for (String word : words) {
            if (positiveWords.contains(word)) score += 50;
            if (negativeWords.contains(word)) score -= 25;
            if (urgencyWords.contains(word)) score += 10;
            if (locationWords.contains(word)) score += 30;
        }
        return score;
    }
}
