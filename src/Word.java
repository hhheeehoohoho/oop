

public class Word {
    private String word_target = null;
    private String word_explain = null;

    public Word() {
    }

    public Word(String word_target, String word_explain) {
        this.word_target = word_target;
        this.word_explain = word_explain;
    }

    public void setWord_target(String newWord) {
        this.word_target = newWord;
    }

    public void setWord_explain(String explain) {
        this.word_explain = explain;
    }

    public String getWord_target() {
        return this.word_target;
    }

    public String getWord_explain() {
        return this.word_explain;
    }
}
