package app.nomnet;

/**
 * Created by Elliscope on 4/23/15.
 */
public class User {
    private String username ;
    private String password ;
//    private Nomification nomi;



    public User(String username,String passowrd){
        this.username = username;
        this.password = passowrd;
    }

    public void changePassword(String new_password){
        this.password = new_password;
    }

    public String getUsername(){return username;}

    public String getPassowrd(){return password;}

//    public Nomification getNomi(){return nomi;}

    //function that update user' nomificaiton list
    public void NotifyUser(){

    }

}
