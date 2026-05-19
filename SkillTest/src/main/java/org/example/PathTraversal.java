package org.example;

import java.io.*;

public class PathTraversal {
    public static void main(String[] args) throws IOException {
        // Vulnerability: Path Traversal - user-controlled input can access sensitive files
        String filename = "../../etc/passwd"; // Hardcoded for demo, but dangerous if user input
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        System.out.println(reader.readLine());
        reader.close();
    }
}
