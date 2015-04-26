package app.nomnet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elliscope on 4/26/15.
 */
public class Comments implements Serializable {
    public List<String> Comments_list;
    public List<Integer> Comments_user_profile;


    Comments() {

        Comments_list = new ArrayList<String>();
        Comments_user_profile = new ArrayList<Integer>();
    }

    public void addComments(Integer ImagID, String comment_string){
        Comments_user_profile.add(ImagID);
        Comments_list.add(comment_string);
        System.out.println(Comments_list.size());
    }

    public Integer[] getC_userProfile(){
        Integer[] Cprofile_array = new Integer[Comments_user_profile.size()];
        for(int i = 0; i<Comments_user_profile.size();i++){
            Cprofile_array[i]=Comments_user_profile.get(i);
        }
        return Cprofile_array ;
    }

    public String[] getComments(){
        String[] Comments_array = new String[Comments_list.size()];
        Comments_list.toArray(Comments_array);
        return Comments_array;
    }
}
