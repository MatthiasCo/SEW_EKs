package E_6a_3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FeedAnalyzer {
    public static void main(String[] args) {
        String pathMessages = "src/E_6b_3/rec/potus.csv";
        Map<AnalyzedText, String> messages = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(pathMessages))) {
            String line;
            while((line = br.readLine()) != null) {
                if (line.startsWith("#")) {
                    continue;
                }
                messages.put(new AnalyzedText(line), line);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Number of messages: " + messages.size());
        double totalSentiment = 0.0;
        for (AnalyzedText analyzedText : messages.keySet()) {
            totalSentiment += analyzedText.getSentiment();
        }
        System.out.println("Total sentiment: " + totalSentiment);
    }

}
