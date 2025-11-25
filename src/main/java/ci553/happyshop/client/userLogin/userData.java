package ci553.happyshop.client.userLogin;

public class userData {
    String userName;
    String passWord;
    double accMoney = 0;
    String sortType = "Unknown";
    final static int maxMoney = 100000;
    //                 this is 1000.00

    public userData(String name,String pass)
    {
        this.userName = name;
        this.passWord = pass;
        //      100,000
        //2,147,483,647 is largest int value
    }

    public userData(String name,String pass,double accMoney, String sortType)
    {
        this.userName = name;
        this.passWord = pass;
        //2,147,483,647 is largest int value
        this.sortType = sortType;
        this.accMoney = accMoney;
    }

    public void changeMoney(double newValue)
    {
        accMoney = newValue;
    }

    public String getSortType() {
        return sortType;
    }

    public String getUserName()
    {
        return userName;
    }
    public double getAccMoney()
    {
        return accMoney;
    }

    public String userToData()
    {
        String res = userName + "," + passWord + "," + accMoney + "," + sortType;
        return res;
    }

    public static userData stringToUser(String userString)
    {
        final int COMMACOUNT = 3;

        System.out.println(" - User Data -");

        int[] commaPlaces = new int[COMMACOUNT];
        commaPlaces[0] = userString.indexOf(",");
        if (commaPlaces[0] != -1)
        {
            boolean validSearch = true;
            for (int i = 1 ; i < commaPlaces.length ; i++)
            {
                commaPlaces[i] = userString.indexOf(",",commaPlaces[i-1] + 1);
                if (commaPlaces[i] == -1)
                {
                    validSearch = false;
                    break;
                }
            }
            if (validSearch)
            {
                String[] sections = new String[COMMACOUNT + 1];
                sections[0] = userString.substring(0,commaPlaces[0]);
                for (int i = 1; i < sections.length - 1; i ++)
                {
                    sections[i] = userString.substring(commaPlaces[i - 1] + 1,commaPlaces[i]);
                }
                sections[sections.length - 1] = userString.substring(commaPlaces[commaPlaces.length - 1] + 1);

                for (String sect : sections)
                {
                    System.out.println(sect);
                }
                System.out.println("End");
                return new userData(sections[0],sections[1],((double) (Integer.parseInt(sections[2]))) / 100,sections[3]);
            }
        }
        return null;
    }
}
