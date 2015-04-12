package app.nomnet;
import android.app.Application;

/*
    This class will hold our global variables
    We can access these variables throughout the whole app

    USAGE EXAMPLES
    Set:   ((MyApplication) this.getApplication()).setSomeVariable("foo");
    Get:   String s = ((MyApplication) this.getApplication()).getSomeVariable();
 */
public class MyApplication extends Application {

    private String currentUser;
    private boolean isLoggedIn = false;

    public String getCurrentUser() {
        return currentUser;
    }
    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }
    public boolean getIsLoggedIn(){
        return isLoggedIn;
    }
    public void setIsLoggedIn(boolean isLoggedIn){
        this.isLoggedIn = isLoggedIn;
    }
}