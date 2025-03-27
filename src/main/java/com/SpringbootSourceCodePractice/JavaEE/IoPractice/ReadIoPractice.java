package com.SpringbootSourceCodePractice.JavaEE.IoPractice;

import java.io.*;

public class ReadIoPractice {

    public static void main(String[] args) {
        FileReader fr = null;
        FileWriter fw = null;
        try {
            File fileW = new File("src/main/java/com/SpringbootSourceCodePractice/JavaEE/IoPractice/practiceW.txt");
            File file = new File("src/main/java/com/SpringbootSourceCodePractice/JavaEE/IoPractice/practice.txt");
            fr = new FileReader(file);
            fw = new FileWriter(fileW,true);
            char [] chars=new char[10];
            int len = 0;
            while ((len = fr.read(chars)) != -1) {
                System.out.print(chars);
                fw.write(chars);
                chars= new char[10];
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally{
            try {
                fr.close();
                fw.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }
}
