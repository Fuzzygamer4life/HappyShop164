package ci553.happyshop.client.userLogin;

import java.io.*;
import java.util.Scanner;

public class loginModel {

    public loginModel()
    {
        writeData();
    }
    void writeData()
    {
        try{
            File mainFile = new File("src/main/resources/UserData/CustData.txt");
            FileWriter textFile = new FileWriter(mainFile);
            System.out.println("Grabbed custFile");
            textFile.write("User,Pass");
            textFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    String searchData(String username,String password)
    {
        try{
            File mainFile = new File("src/main/resources/UserData/CustData.txt");

            Scanner textFile = new Scanner(mainFile);

            while (textFile.hasNextLine())
            {
                String currentLine = textFile.nextLine();
                int commaPoint = currentLine.indexOf(",");
                if (commaPoint == -1)
                {
                    System.out.println("Invalid Line in text files");
                }
                else{

                }
            }

            textFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
