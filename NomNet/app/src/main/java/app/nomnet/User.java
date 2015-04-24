package app.nomnet;

import java.util.List;

/**
 * Created by Elliscope on 4/23/15.
 */
public class User {
    private String username ;
    private String password ;
    private String email;
    private Nomification nomi;

    List<Nom> Nom_list;

    public User(String username,String passwordrd, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public void changePassword(String new_password){
        this.password = new_password;
    }

    public String getUsername(){return username;}

    public String getPassowrd(){return password;}

    public String getEmail(){return email;}

    public Nomification getNomi(){return nomi;}

    //function that update user' nomificaiton list
    public void NotifyUser(){

    }

    public void addNom(Nom nom) {
        Nom_list.add(nom);
    }

    public void encrypt(String pwd){}

    public void decryp(String pwd){}

}
