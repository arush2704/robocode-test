package org.example;

public class VowelsRemoval {

    public String removeVowels(String input) {
        StringBuilder result = new StringBuilder();
        if (input == null) {
            return null;
        }
        for (Character c : input.toCharArray()) {
            if (Character.isLetter(c) && "AEIOUaeiou".indexOf(c) == -1) {
                result.append(c);
            }
        }

        return result.toString();
    }

    public static void main(String[] args) {
        VowelsRemoval vr = new VowelsRemoval();
        String input = "Hello6545dffsdfsdsads3 World!";
        String output = vr.removeVowels(input);
        System.out.println("Input: " + input);
        System.out.println("Output: " + output);
    }
}
