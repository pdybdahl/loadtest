package com.netcompany.skolelogin.broker.loadtest.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ValuesFactory {

    public static List<String> genererMuligeInstitutionsnumre() throws IOException {
        return readFile("c:\\Projects\\Loadtest\\Resources\\Institutionsnumre.csv");
    }

    public static List<String> genererMuligeDrengenavne() throws IOException {
        return readFile("c:\\Projects\\Loadtest\\Resources\\Alle godkendte drengenavne per 2019-09-06.csv");
    }

    public static List<String> genererMuligePigenavne() throws IOException {
        return readFile("c:\\Projects\\Loadtest\\Resources\\Alle godkendte pigenavne per 2019-09-06.csv");
    }

    public static List<String> genererMuligeEfternavne() throws IOException {
        return readFile("c:\\Projects\\Loadtest\\Resources\\Frie efternavne.txt");
    }

    private static List<String> readFile(String absoluteFileName) throws IOException {
        File file = new File(absoluteFileName);
        FileReader fileReader = new FileReader(file);
        BufferedReader buffer = new BufferedReader(fileReader);

        List<String> allNames = new ArrayList<>();
        String name = null;
        while ((name = buffer.readLine()) != null) {
            if (isAllLetters(name)) {
                allNames.add(name);
            }
        }

        return allNames;
    }

    private static boolean isAllLetters(String str) {
        char[] chars = str.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            char c = str.toCharArray()[i];
            if (!Character.isLetter(c) && !Character.isDigit(c)) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        try {
            genererMuligePigenavne().stream().forEach(l -> System.out.println(l));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
