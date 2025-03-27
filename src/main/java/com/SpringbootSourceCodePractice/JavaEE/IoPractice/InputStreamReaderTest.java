package com.SpringbootSourceCodePractice.JavaEE.IoPractice;

import java.io.*;

public class InputStreamReaderTest {
    public static void main(String[] args) {
        InputStreamReader inputStreamReader = null;
        FileInputStream fs=null;
        try {
            File file = new File("src/main/java/com/SpringbootSourceCodePractice/JavaEE/IoPractice/practice.txt");
            fs= new FileInputStream(file);
            inputStreamReader = new InputStreamReader(fs,"GBK");
            char[] chars = new char[1024];
            int len;
            while ((len = inputStreamReader.read(chars)) != -1) {
                System.out.println(new String(chars, 0, len));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                inputStreamReader.close();
                fs.close();
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
