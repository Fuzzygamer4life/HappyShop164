package ci553.happyshop.client.userLogin;

import ci553.happyshop.client.Main;

import java.io.*;
import java.util.Objects;
import java.util.Scanner;

public class loginModel {

    static boolean addUserData = false;
    public loginView logView;
    Main mainProgram;
    static String[] fakeUserData = new String[]{
            "Zayk,1234",
            "Zamla,67",
            "Zang,234",
            "Zapp,12456",
            "Zalik,674",
            "Zany,7568",
            "Zabadaba,Yaya",
            "Zankyou,gsdg",
            "Zabe,cvb",
            "Zate,sdgfser",
    };

    public void addUser(String userData)
    {
        try{
            File mainFile = new File("src/main/resources/UserData/CustData.txt");
            FileWriter textFile = new FileWriter(mainFile,true);
            textFile.write(userData);
            textFile.write("\n");
            System.out.println("Added User Data : " + userData);
            textFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void addUser(String userName,String userPass)
    {
        try{
            File mainFile = new File("src/main/resources/UserData/CustData.txt");
            FileWriter textFile = new FileWriter(mainFile,true);
            textFile.write(userName + "," + userPass);
            textFile.write("\n");
            System.out.println("Added User Data : " + userName);
            textFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public loginModel(Main mainProgram)
    {
        this.mainProgram = mainProgram;
        if (addUserData)
        {
            for (String data : fakeUserData)
            {
                addUser(data);
            }
        }
    }

    public boolean takeInput(boolean signIn)
    {
        String user = logView.nameInput.getText();
        String pass = logView.passInput.getText();
        System.out.println("Taking Input : " + (signIn ? "SignIn" : "RedgIn"));
        if (signIn)
        {
            String searchRes = searchData(user,pass);
            if (searchRes != null)
            {
                logView.thisWindow.close();
                mainProgram.openMainWindows();
                return true;
            }
            return false;
        }
        else{
            if (!hasUsername(user,pass))
            {
                System.out.println("Valid username");
                addUser(user,pass);
                return true;
            }
            System.out.println("Invalid username");
            return false;
        }
    }

    boolean hasUsername(String userName,String pass)
    {
        if (pass.isBlank() || userName.isBlank() || userName.contains(","))
        {
            System.out.println("Attempted to login with : Blank Username/Password");
            return true;
        }
        try{
            File mainFile = new File("src/main/resources/UserData/CustData.txt");

            Scanner textFile = new Scanner(mainFile);

            while (textFile.hasNextLine())
            {
                String currentLine = textFile.nextLine();
                int[] commaPoints = new int[2];
                commaPoints[0] = currentLine.indexOf(",");
                if (commaPoints[0] == -1)
                {
                    System.out.println("Invalid Line in text files");
                }
                else{
                    if ((Objects.equals(userName, currentLine.substring(0, commaPoints[0]))))
                    {
                        return true;
                    }
                }
            }

            textFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    String searchData(String username,String password)
    {
        try{
            File mainFile = new File("src/main/resources/UserData/CustData.txt");

            Scanner textFile = new Scanner(mainFile);

            while (textFile.hasNextLine())
            {
                String currentLine = textFile.nextLine();
                int[] commaPoints = new int[2];
                commaPoints[0] = currentLine.indexOf(",");
                if (commaPoints[0] == -1)
                {
                    System.out.println("Invalid Line in text files");
                }
                else{
                    if ((Objects.equals(username, currentLine.substring(0, commaPoints[0]))) && (Objects.equals(password, currentLine.substring(1 + commaPoints[0]))))
                    {
                        System.out.println("Password Valid");
                        return currentLine;
                    }
                }
            }

            textFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
