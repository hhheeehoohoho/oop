

import java.util.ArrayList;

public class Dictionary {

    public ArrayList<Word> wordList = new ArrayList<>();


    public int size() {
        return wordList.size();
    }

    public Word get(int i) {
        return wordList.get(i);
    }

    private int binarySearcher(int start, int end, String spelling) {
        if (end < start) return -1;
        int mid = start + (end - start) / 2;
        Word word = wordList.get(mid);
        String currentWord = word.getWord_target();
        if (currentWord.startsWith(spelling)) {
            return mid;
        }
        int compare = currentWord.compareTo(spelling);
        if (compare == 0) return mid;
        if (compare > 0) return binarySearcher(start, mid - 1, spelling);
        return binarySearcher(mid + 1, end, spelling);
    }

    public ArrayList<Word> searcher(String spelling) {
        ArrayList<Word> result = new ArrayList<>();
        int index =  binarySearcher(0, wordList.size() - 1, spelling);
        if (index >= 0) {
            result.add(wordList.get(index));
            int left = index - 1, right = index + 1;

            while (left >= 0) {
                Word leftWord = wordList.get(left--);
                if (leftWord.getWord_target().startsWith(spelling))
                    result.add(leftWord);
                else
                    break;
            }

            int length = wordList.size();
            while (right < length) {
                Word leftWord = wordList.get(right++);
                if (leftWord.getWord_target().startsWith(spelling))
                    result.add(leftWord);
                else
                    break;
            }
        }
        return result;
    }
}
