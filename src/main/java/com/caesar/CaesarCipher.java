package com.caesar;

public class CaesarCipher {
    private final String russianAlphabet = "абвгдежзийклмнопрстуфхцчшщъыьэюя";
    private final String englishAlphabet = "abcdefghijklmnopqrstuvwxyz";
    private String alphabet;
    private final int shift;

    public CaesarCipher(int shift, String language) {
        this.shift = shift % getAlphabet(language).length();
        this.alphabet = getAlphabet(language);
    }

    private String getAlphabet(String language) {
        switch (language.toLowerCase()) {
            case "russian":
                return russianAlphabet;
            case "english":
            default:
                return englishAlphabet;
        }
    }

    public String encrypt(String text) {
        return shiftText(text, shift);
    }

    public String decrypt(String text) {
        return shiftText(text, -shift);
    }

    private String shiftText(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (char character : text.toCharArray()) {
            int index = alphabet.indexOf(Character.toLowerCase(character));
            if (index != -1) {
                int newIndex = (index + shift + alphabet.length()) % alphabet.length();
                char newChar = alphabet.charAt(newIndex);
                result.append(Character.isUpperCase(character) ? Character.toUpperCase(newChar) : newChar);
            } else {
                result.append(character);
            }
        }
        return result.toString();
    }
}
