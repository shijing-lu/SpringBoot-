package com.SpringbootSourceCodePractice.JavaEE.IoPractice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class OutputStreamWriteTest {
    public static void main(String[] args) {
        OutputStreamWriter outputStreamWriter = null;
        FileOutputStream fos = null;
        try {
            File file = new File("src/main/java/com/SpringbootSourceCodePractice/JavaEE/IoPractice/practice3.txt");
            fos = new FileOutputStream(file);
            outputStreamWriter = new OutputStreamWriter(fos,"IBM01143");
            outputStreamWriter.write("hello world");
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                outputStreamWriter.close();
                fos.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
