

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryCommandline {

    DictionaryManagement ob = new DictionaryManagement();
    Dictionary dict = ob.dict;
    Scanner scanner = new Scanner(System.in);

    public void insertFromCommandline() {

        System.out.print("Number: ");
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; i++) {

            System.out.print("New word: ");
            String newWord = scanner.nextLine();

            System.out.print("Mean in Vietnamese: ");
            String explain = scanner.nextLine();

            ob.generalInsertNewWord(newWord, explain);
        }
    }

    public void dictionaryLookup() {
        scanner = new Scanner(System.in);
        System.out.print("word need to translate: ");
        String s = scanner.nextLine();
        System.out.println("meaning: " + ob.DictionaryLookup(s));
    }

    public void insertNewWord() {
        scanner = new Scanner(System.in);
        System.out.print("New word: ");
        String newWord = scanner.nextLine();

        System.out.print("Mean in Vietnamese: ");
        String Explain = scanner.nextLine();

        Word word = new Word();
        word.setWord_target(newWord);
        word.setWord_explain(Explain);

        boolean check = true;
        for (int i = 0; i < dict.wordList.size(); i++) {
            if (word.getWord_target().equals(dict.wordList.get(i).getWord_target())) {
                check = false;
                break;
            }
        }

        if (check) {
            dict.wordList.add(word);
        } else {
            System.out.println("This word existed!");
        }
    }

    public void deleteWord() {
        scanner = new Scanner(System.in);
        System.out.print("Word need to delete: ");
        String s = scanner.nextLine();

        boolean check = ob.generalDeleteWord(s);
        if (check) {
            System.out.println("Delete successfully.");
        } else {
            System.out.println("Suck no word like that.");
        }
    }

    public void showAllWords() {
        System.out.println("NO  | English       | Vietnamese");
        for (int i = 0; i < dict.size(); i++) {
            System.out.println((i+1) + "\t| " + dict.get(i).getWord_target() + "\t\t\t| " + dict.get(i).getWord_explain());
        }
    }

    public void updateWord() {
        scanner = new Scanner(System.in);

        System.out.println("Word need update: ");
        String word = scanner.nextLine();
        System.out.println("Update meaning: ");
        String explain = scanner.nextLine();

        ob.generalUpdateWord(word, explain);
    }

    public void dictionarySearcher() {
        Scanner scanner = new Scanner(System.in);
        String spelling = scanner.nextLine();
        ArrayList<Word> words = dict.searcher(spelling);

        for (Word word : words) {
            System.out.print(word.getWord_target());
            System.out.println(" " + word.getWord_explain());
        }
    }

    public void dictionaryBasic() {
        insertFromCommandline();
        showAllWords();
    }

    public void dictionaryAdvanced() throws IOException {


        boolean quit = false;

        do {

            System.out.println("Welcome to My Application");
            System.out.println("\t0. Exit");
            System.out.println("\t1. Add");
            System.out.println("\t2. Remove");
            System.out.println("\t3. Update");
            System.out.println("\t4. Display");
            System.out.println("\t5. Lookup");
            System.out.println("\t6. Search");
            System.out.println("\t7. Speech");
            System.out.println("\t8. Import from file");
            System.out.println("\t9.Export to file\n");
            System.out.println("==========================================");

            System.out.print("Enter Your Option: ");
            int op = scanner.nextInt();

            switch (op) {
                case 0:
                    quit = true;
                    break;

                case 1:
                    insertNewWord();
                    break;

                case 2:
                    deleteWord();
                    break;

                case 3:
                    updateWord();
                    break;

                case 4:
                    showAllWords();
                    break;

                case 5:
                    String next;
                    do {
                        scanner = new Scanner(System.in);
                        dictionaryLookup();
                        System.out.print("another word ? (y/n) ");
                        next = scanner.nextLine();
                    } while (next.equals("y"));
                    break;

                case 6:
                    System.out.println("enter word to search: ");
                    dictionarySearcher();
                    break;

                case 7:
                    var service = new TextToSpeechService();
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("Enter a word: ");
                    String word = scanner.nextLine();
                    service.textToSpeech("Your word spell is", "english" + word);

                case 8:
                    ob.insertFromFile();
                    System.out.println("File already imported!");
                    break;

                case 9:
                    ob.saveData();
//                    quit = true;
//                    break;
//
//                case 10:
                    ob.dictionaryExportToFile();
                    quit = true;
                    break;

                default:
                    System.out.println("Action not supported");

            }
        } while (!quit);

    }


}
