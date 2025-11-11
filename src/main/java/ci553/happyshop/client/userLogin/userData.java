package ci553.happyshop.client.userLogin;

public class userData {
    String userName;
    String passWord;
    int accMoney = 0;
    String sortType = "Unknown";
    final static int maxMoney = 100000;
    //                 this is 1000.00

    public userData(String name,String pass)
    {
        this.userName = name;
        this.passWord = pass;
        //2,147,483,647 is largest int value
    }

    public String userToData()
    {
        String res = userName + "," + passWord + "," + accMoney + "," + sortType;
        return res;
    }
}
