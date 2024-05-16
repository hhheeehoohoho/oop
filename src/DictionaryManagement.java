

import java.io.*;
import java.util.Scanner;

public class DictionaryManagement {
    Dictionary dict = new Dictionary();

    public void generalInsertNewWord(String word, String explain) {
        Word w = new Word(word, explain);
        boolean check = true;
        for (int i = 0; i < dict.wordList.size(); i++) {
            if (word.equals(dict.wordList.get(i).getWord_target())) {
                check = false;
                break;
            }
        }

        if (check) {
            dict.wordList.add(w);
        }
    }

    public boolean generalDeleteWord(String word) {
        boolean check = false;
        for (int i = 0; i < dict.wordList.size(); i++) {
            if (word.equals(dict.wordList.get(i).getWord_target())) {
                dict.wordList.remove(dict.wordList.get(i));
                check = true;
            }
        }
        return check;
    }

    public String DictionaryLookup(String word) {
        String tran = "No result!";
        for (int i = 0; i < dict.wordList.size(); i++) {
            if (word.equals(dict.wordList.get(i).getWord_target())) {
                tran = dict.wordList.get(i).getWord_explain();
            }
        }
        return tran;
    }

    public void generalUpdateWord(String word, String explain) {
        for (int i = 0; i < dict.wordList.size(); i++) {
            if (word.equals(dict.wordList.get(i).getWord_target())) {
                dict.wordList.get(i).setWord_explain(explain);
                return;
            }
        }
        generalInsertNewWord(word, explain);
    }
    public void saveData() throws IOException {
        FileWriter fileUser = new FileWriter("C:/Users/Admin/Desktop/btl-oop-master/btl-oop-master/DEMO/company/dictionaries_user.txt");
        for (Word i : dict.wordList) {
            fileUser.write(i.getWord_target() + "\t" + i.getWord_explain() + "\n");
        }
        fileUser.close();
    }


    public void dictionaryExportToFile() throws IOException {
        System.out.println("You need to rerun program after reset.");

        File in = new File("..\\dictionaries_host.txt");
        FileWriter out = new FileWriter("..\\dictionaries_user.txt");

        if (in.canRead()) {
            Scanner scanner = new Scanner(in);
            while (scanner.hasNextLine()) {
                out.write(scanner.nextLine() + "\n");
            }
        } else {
            System.out.println("error");
        }

        out.close();

        insertFromFile();
    }

    public void insertFromFile() throws FileNotFoundException {
        File dictionaries = new File("..\\dictionaries_user.txt");

        Scanner scanner = new Scanner(dictionaries);
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            String[] in = s.split("[\t\b]");

            generalInsertNewWord(in[0], in[1]);
            scanner.close();
        }
    }


}
