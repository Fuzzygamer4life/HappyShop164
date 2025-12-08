package ci553.happyshop.client.userLogin;

import ci553.happyshop.client.Main;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class loginModel {

    static boolean addUserData = false;
    public loginView logView;
    Main mainProgram;

    static userData[] defData = new userData[]{
            new userData("Zack","58008"),
            new userData("Zay1k","12asf34"),
            new userData("Zay213k","12fasf34"),
            new userData("Zay55k","123fasf4"),
            new userData("Zay15k","12fas34"),
            new userData("Za51yk","12fasf34"),
            new userData("Zayaak","12fasf34"),
            new userData("Zay4sk","12fafa34"),
            new userData("Zayfask","123fasf4"),
            new userData("Zayasfk","12afs34"),
            new userData("abc","123")
    };

    public void addUser(userData userInfo)
    {
        String data = userInfo.userToData(false);
        try{
            File mainFile = new File("src/main/resources/UserData/CustData.txt");
            FileWriter textFile = new FileWriter(mainFile,true);
            textFile.write(data);
            textFile.write("\n");
            System.out.println("Added User Data : " + data);
            textFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static void updateUser(userData userInfo)
    {
        String data = userInfo.userToData(true);
        ArrayList<String> lines = new ArrayList<>();
        try{
            File mainFile = new File("src/main/resources/UserData/CustData.txt");

            Scanner textFile = new Scanner(mainFile);

            while (textFile.hasNextLine())
            {
                String currentLine = textFile.nextLine();
                String[] currentData = splitData(currentLine);

                if (currentData != null)
                {
                    if ((Objects.equals(userInfo.userName, currentData[0])))
                    {
                        lines.add(data);
                    }
                    else{
                        lines.add(currentLine);
                    }
                }
            }
            textFile.close();


            FileWriter writeFile = new FileWriter(mainFile,false);
            for (String line : lines)
            {
                writeFile.write(line);
                writeFile.write("\n");
            }
            writeFile.close();
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public loginModel(Main mainProgram)
    {
        this.mainProgram = mainProgram;
        if (addUserData)
        {
            for (userData data : defData)
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
        System.out.println(user);
        System.out.println(pass);
        if (signIn)
        {
            String searchRes = searchData(user,pass);
            if (searchRes != null)
            {
                logView.thisWindow.close();
                mainProgram.openMainWindows(userData.stringToUser(searchRes));
                return true;
            }
            return false;
        }
        else{
            if (!hasUsername(user))
            {
                System.out.println("Valid username");

                String passwordResult = isValidPassword(pass);
                if (passwordResult != null)
                {
                    logView.errorText = passwordResult;
                    return false;
                }

                addUser(new userData(user,pass));
                return true;
            }
            logView.errorText = "Username cannot be blank / taken";
            System.out.println("Invalid username");
            return false;
        }
    }

    public String isValidPassword(String password)
    {
        if (password.length() < 6)
        {
            return "password must contain >6 characters";
        }
        else if (password.isBlank())
        {
            return "password cannot be blank";
        }
        boolean upperCheck = false;
        boolean lowerCheck = false;
        boolean numcheck = false;
        for (char ch : password.toCharArray())
        {
            if (ch >= 'A' && ch <= 'Z')
            {
                upperCheck = true;
            }
            else if (ch >= 'a' && ch <= 'z')
            {
                lowerCheck = true;
            }
            else if (ch >= '0' && ch <= '9')
            {
                numcheck = true;
            }

        }
        if (!numcheck)
        {
            return "Password must contain a digit";
        }
        if (!upperCheck)
        {
            return "Password needs a upper case letter";
        }
        if (!lowerCheck)
        {
            return "Password needs a lower case letter";
        }
        return null;
    }

    static boolean hasUsername(String userName)
    {
        if (userName.isBlank() || userName.contains(","))
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
                String[] currentData = splitData(currentLine);

                if (currentData != null)
                {
                    if ((Objects.equals(userName, currentData[0])))
                    {
                        textFile.close();
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

    static String[] splitData(String userString)
    {
        if (userString == null)
        {
            return null;
        }
        int[] commaPoints = new int[2];
        commaPoints[0] = userString.indexOf(",");
        commaPoints[1] = (commaPoints[0] == -1 ? -1 : userString.indexOf(",",commaPoints[0] + 1));
        if (commaPoints[commaPoints.length-1] != -1)
        {
            return new String[]{userString.substring(0,commaPoints[0]),userString.substring(commaPoints[0] + 1,commaPoints[1])};
        }
        return null;
    }

    String searchData(String username,String password)
    {
        try{
            File mainFile = new File("src/main/resources/UserData/CustData.txt");

            Scanner textFile = new Scanner(mainFile);

            while (textFile.hasNextLine())
            {
                String currentLine = textFile.nextLine();
                String[] currentData = splitData(currentLine);
                if (currentData != null)
                {
                    if ((Objects.equals(username, currentData[0])) && (Objects.equals(password, currentData[1])))
                    {
                        System.out.println("Password Valid");
                        return currentLine;
                    }
                }
                else{
                    System.out.println("Encountered Invalid Line");
                }
            }

            textFile.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
