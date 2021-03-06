package app.nomnet;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Register extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button button1 = (Button)findViewById(R.id.btnSignup);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loginSuccess();
                Intent i = new Intent(Register.this, FoodFeedActivity.class);
                startActivity(i);
            }
        });

        TextView signin = (TextView)this.findViewById(R.id.link_to_signin);
        signin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Register.this, SignIn.class);
                startActivity(i);
            }
        });

        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }

    public void loginSuccess() {
        ((MyApplication) this.getApplication()).setIsLoggedIn(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
