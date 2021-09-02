import java.io.*;


public class Main {

    public static void main(String[] args) {

        System.out.println("Hello World!");

        try {
            FileReader fr = new FileReader("text.txt");

            int i;
            while ((i=fr.read()) != -1)
                System.out.print((char) i);

        } catch(FileNotFoundException ex) {
            System.out.println("File not found");
        }
    }
}
