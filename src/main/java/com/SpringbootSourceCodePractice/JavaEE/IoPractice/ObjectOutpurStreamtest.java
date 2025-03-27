package com.SpringbootSourceCodePractice.JavaEE.IoPractice;


import java.io.*;

class students implements Serializable {
    private String name;
    private int age;
    private String sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "students{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                '}';
    }
}


public class ObjectOutpurStreamtest {
    public static void main(String[] args) {
        students students = new students();
        students.setName("张三");
        students.setAge(18);
        students.setSex("男");

/*        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream("src/main/java/com/SpringbootSourceCodePractice/JavaEE/IoPractice/ObjectTxt.txt"));
            oos.writeObject(students);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally{
            try {
                oos.close();

            } catch (IOException e) {
              e.printStackTrace();
            }
        }*/

//        反序列化
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream("src/main/java/com/SpringbootSourceCodePractice/JavaEE/IoPractice/ObjectTxt.txt"));
            students students1 = (students) ois.readObject();
            System.out.println(students1.toString());
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally{
            try {
                ois.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
