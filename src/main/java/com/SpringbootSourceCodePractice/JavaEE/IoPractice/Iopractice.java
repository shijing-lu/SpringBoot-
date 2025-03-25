package com.SpringbootSourceCodePractice.JavaEE.IoPractice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Iopractice {


    public static void main(String[] args) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            File fileOut = new File("src/main/java/com/SpringbootSourceCodePractice/JavaEE/IoPractice/practice1.txt");
// 相对路径写法（从项目根目录出发
            File file = new File("src/main/java/com/SpringbootSourceCodePractice/JavaEE/IoPractice/practice.txt");
            fis = new FileInputStream(file);
            fos = new FileOutputStream(fileOut,true);
            byte[] buffer = new byte[50];
            int len = 0;
            while ((len=fis.read(buffer)) != -1) {
                System.out.println(len);
                fos.write(buffer,0,len);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
