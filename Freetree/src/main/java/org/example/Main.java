package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.println("Hello and welcome!");

        LambdaAnalysis analysis = new LambdaAnalysis();
      //  System.out.println(analysis.getSecondHighestPaid(analysis.intialiseMap()));

        //analysis.findMaxFrequency();
        //analysis.findMaxFrequencyStr();
        //analysis.findFrequency();
        //analysis.findCharFrequency();
        //analysis.findFirstRepeatingChar();
       // analysis.findCommanNames();
        analysis.pelindrome();
        analysis.fabonacciSeries();
    }
}