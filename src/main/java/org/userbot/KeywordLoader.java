package org.userbot;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class KeywordLoader {
    public static Set<String> loadKeywords(String filePath) {
        try {
            String content = Files.readString(Paths.get(filePath));
            String[] words = content.toLowerCase().split(",");
            Set<String> wordsSet = new HashSet<>();
            for (String word : words) {
                wordsSet.add(word.toLowerCase());
            }
            return wordsSet;
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptySet();
        }
    }

}