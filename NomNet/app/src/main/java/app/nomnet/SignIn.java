package app.nomnet;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;


public class SignIn extends ActionBarActivity {

    EditText userEdit, regUserEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Button button = (Button)findViewById(R.id.btnLogin);
        userEdit = (EditText) findViewById(R.id.usernameEdit);
        regUserEdit = (EditText) findViewById(R.id.regUserEdit);

        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String username = String.valueOf(userEdit.getText() );
                loginSuccess(username);
                Intent i = new Intent(SignIn.this, FoodFeedActivity.class);
                 startActivity(i);
            }
        });

        TextView register = (TextView)this.findViewById(R.id.link_to_register);
        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignIn.this, Register.class);

                String username = String.valueOf(regUserEdit.getText() );
                loginSuccess(username);
                startActivity(i);
            }
        });

        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

   public void loginSuccess(String username) {
       ((MyApplication) this.getApplication()).setCurrentUser(username);
       ((MyApplication) this.getApplication()).setIsLoggedIn(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
