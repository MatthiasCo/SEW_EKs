package E_6a_3;

import java.util.ArrayList;

public class tests {
    public static void main(String[] args) {
        ArrayList<Frage> cooked = new ArrayList<>();
        cooked.add(new Frage("Wie geht es dir?", 0.5));
        cooked.add(new Frage("Was ist dein Lieblingsessen?", 0.8));
        cooked.add(new Frage("Wie findest du das Wetter?", 0.2));

        ArrayList<Frage> cooked2 = (ArrayList<Frage>) cooked.stream().sorted().toList();
        for (Frage frage : cooked2) {
            System.out.println(frage.getText() + " - " + frage.getSentiment());
        }
    }
}

class Frage implements Comparable<Frage> {
    private String text;
    private double sentiment;

    public Frage(String text, double sentiment) {
        this.text = text;
        this.sentiment = sentiment;
    }

    public String getText() {
        return text;
    }

    public double getSentiment() {
        return sentiment;
    }

    @Override
    public int compareTo(Frage other) {
        return Double.compare(this.sentiment, other.sentiment);
    }
}