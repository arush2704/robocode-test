package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.println("Hello and welcome!");
        mergeStrings("123","abcdesss");
    }

   // "123", "abc" = "1a2b3c"
   //         "12345", "abc" = "1a2b3c45"
   //         "123", "abcde" = "1a2b3cde"

    public static void mergeStrings(String str1, String str2)
    {
        char[] charArray1 =str1.toCharArray();
        char[] charArray2 =str2.toCharArray();

        String mergedStrings = "";
        if(charArray1.length > charArray2.length)
        {
                for (int i = 0; i < charArray2.length; i++) {
                    mergedStrings = mergedStrings + charArray1[i] + charArray2[i];
                }


            for(int j=charArray2.length + 1; j<charArray1.length;j++)
            {
                mergedStrings = mergedStrings + charArray1[j];
            }

        }else if(charArray2.length > charArray1.length)
        {
            for(int i=0;i< charArray1.length;i++)
            {
                mergedStrings = mergedStrings + charArray1[i]+charArray2[i];
            }

            for(int j=charArray1.length + 1; j<charArray2.length;j++)
            {
                mergedStrings = mergedStrings + charArray2[j];
            }
        }else{
            for(int i=0; i< charArray1.length;i++)
            {
                mergedStrings = mergedStrings + charArray1[i]+charArray2[i];
            }
        }
        System.out.println(mergedStrings);
    }

    public void merge2String()
    {
        String s1 = "abcd";
        String s2 = "6343435";
        StringBuilder merged = new StringBuilder();

        int maxLength = Math.max(s1.length(), s2.length());

        for (int i = 0; i < maxLength; i++) {
            if (i < s1.length()) {
                merged.append(s1.charAt(i));
            }
            if (i < s2.length()) {
                merged.append(s2.charAt(i));
            }
        }

        System.out.println("Merged String: " + merged.toString());
    }

}