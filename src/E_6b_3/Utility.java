package E_6b_3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Utility {
    public static double analyzeText(String text) {
        String pathStop = "rec/SmartStoplist.txt";
        String pathLex = "rec/vader_lexicon.txt";
        Set<String> words = new HashSet<>();
        Map<String, Double> lexicon = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(pathStop))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) {
                    continue;
                }
                words.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(pathLex))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) {
                    continue;
                }
                String[] parts = line.split("\t");
                String word = parts[0];
                double score = Double.parseDouble(parts[1]);
                lexicon.put(word, score);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        double score = 0.0;
        for(String word : words) {
            if(lexicon.containsKey(word)) {
                score += lexicon.get(word);
            }
        }

        return score;
    }
}