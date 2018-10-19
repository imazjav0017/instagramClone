package com.example.imazjav0017.instaclone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnKeyListener{
    RelativeLayout background;
    EditText username,password,email;
    Button logIn;
    TextView changeMode;
    boolean inLogInMode=true;
    public void goToTrending()
    {
        Intent intent=new Intent(getApplicationContext(),TopPosts.class);
        startActivity(intent);
    }
    public void logIn(View v) {
        if (username.getText().toString().matches("") || password.getText().toString().matches("")) {
            Toast.makeText(this, "Username and password are necessary", Toast.LENGTH_SHORT).show();

        } else {
            if(inLogInMode) {
                ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null) {
                            Log.i("Login", "Success");
                            goToTrending();
                        } else {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else
            {

                ParseUser user=new ParseUser();
                user.setUsername(username.getText().toString());
                user.setPassword(password.getText().toString());
                user.setEmail(email.getText().toString());
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null)
                        {
                            Log.i("SignUp","Success");
                            goToTrending();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Instagram");
        background=(RelativeLayout)findViewById(R.id.background);
        username=(EditText)findViewById(R.id.usernameInput);
        password=(EditText)findViewById(R.id.passwordInput);
        logIn=(Button)findViewById(R.id.LogIn_btn);
        changeMode=(TextView)findViewById(R.id.changeMode);
        email=(EditText)findViewById(R.id.email);
        changeMode.setOnClickListener(this);
        background.setOnClickListener(this);
        password.setOnKeyListener(this);
        if(ParseUser.getCurrentUser()!=null)
        {
            goToTrending();
        }
        //ParseAnalytics.trackAppOpenedInBackground(getIntent());
        parse();
    }
    public void parse()
    {
        Parse.enableLocalDatastore(this);

        // Add your initialization code here
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("55ae035c549fb229da81e140d0a41df8cc26c5ac")
                .clientKey("9558daedc385c6fb25ab307a8f778c3a366b8138")
                .server("http://18.216.253.77:80/parse/")
                .build()
        );





        // ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setPublicWriteAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);

    }


    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.changeMode)
        {
            if(inLogInMode)
            {
                username.setText("");
                password.setText("");
                inLogInMode=false;
                logIn.setText("SignUp");
                changeMode.setText(",or LogIn");
                email.setVisibility(View.VISIBLE);
                username.setHint("Enter your desired username");
            }
            else
            {
                username.setText("");
                password.setText("");
                inLogInMode=true;
                logIn.setText("LogIn");
                changeMode.setText(",or SignUp");
                email.setVisibility(View.INVISIBLE);
                username.setHint("Enter your Username..");
            }
        }
        else if(view.getId()==R.id.background)
        {
            InputMethodManager manager=(InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if(i==KeyEvent.KEYCODE_ENTER && keyEvent.getAction()==KeyEvent.ACTION_DOWN)
        {
            logIn(view);
        }
        return false;
    }
}

