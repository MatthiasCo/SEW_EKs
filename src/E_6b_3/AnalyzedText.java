package E_6b_3;

import java.util.Objects;

public class AnalyzedText implements Comparable<AnalyzedText> {
    private String text;
    private double sentiment;

    public AnalyzedText(String text) {
        this.text = text;
        this.sentiment = Utility.analyzeText(text);
    }

    public String getText() {
        return text;
    }
    public double getSentiment() {
        return sentiment;
    }


    @Override
    public int compareTo(AnalyzedText other) {
        return Double.compare(this.sentiment, other.sentiment);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AnalyzedText)) return false;
        AnalyzedText other = (AnalyzedText) o;
        return Double.compare(other.sentiment, sentiment) == 0 &&
                Objects.equals(text, other.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, sentiment);
    }

    @Override
    public String toString() {
        return "AnalyzedText{text=\"" + text + "\", sentiment=" + sentiment + "}";
    }
}
