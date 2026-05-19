package org.example;

public class Calculator {
         public void calculate() {
             printSum(5, 10);
             printSum(3, 7);
         }

         private void printSum(int a, int b) {
             System.out.println("Sum: " + (a + b));
         }
     }
