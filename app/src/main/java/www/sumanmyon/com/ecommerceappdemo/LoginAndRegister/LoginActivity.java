package www.sumanmyon.com.ecommerceappdemo.LoginAndRegister;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import www.sumanmyon.com.ecommerceappdemo.Admin.AddItemActivity;
import www.sumanmyon.com.ecommerceappdemo.Admin.DashboardActivity;
import www.sumanmyon.com.ecommerceappdemo.DataBase.LoginAndRegisterDBHelper;
import www.sumanmyon.com.ecommerceappdemo.DataBase.SharePreferenceForUserCredential;
import www.sumanmyon.com.ecommerceappdemo.MainActivity;
import www.sumanmyon.com.ecommerceappdemo.R;

public class LoginActivity extends AppCompatActivity {

    LoginAndRegisterDBHelper helper;

    EditText editTextUserName, editTextPassword;
    Button buttonLogin;
    TextView textViewRegisterAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        helper = new LoginAndRegisterDBHelper(this);

        editTextUserName = (EditText)findViewById(R.id.login_user_name);
        editTextPassword = (EditText)findViewById(R.id.login_password);
        buttonLogin = (Button) findViewById(R.id.login_button);
        textViewRegisterAcc = (TextView) findViewById(R.id.textView_register_acc);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isTrue = checkingEditTextFieldsAreNotNull();
                if(isTrue == true){
                    boolean isHaved = helper.check(editTextUserName.getText().toString(),editTextPassword.getText().toString());
                    if(isHaved == true){
                        showMessage("Login Successfully");

                        //put data in share preference
                        new SharePreferenceForUserCredential(LoginActivity.this)
                                .setCredential(editTextUserName.getText().toString(),editTextPassword.getText().toString(),helper.getID());

                        //redirect
                        redirect();
                    }else {
                        showMessage("Login failed. Please create account to login");
                    }
                }
            }
        });

        textViewRegisterAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });

    }

    private boolean checkingEditTextFieldsAreNotNull() {
        if (TextUtils.isEmpty(editTextUserName.getText())) {
            editTextUserName.setError("Please enter username");
            return false;
        } else if (TextUtils.isEmpty(editTextPassword.getText())) {
            editTextPassword.setError("Please enter password");
            return false;
        } else {
            return true;
        }
    }

    private void showMessage(String message) {
        Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();
    }

    private void createAccount(){
        finish();
        Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(i);
    }

    private void redirect() {
        finish();
        Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
        i.putExtra("uid",helper.getID());
        i.putExtra("activity","login");
        startActivity(i);
    }
}
