package info.androidhive.navigationdrawer.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;

import info.androidhive.navigationdrawer.R;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginTAG_";
    private TextView        emailSignInTxt;
    private TextView        passwordSignInTxt;
    private TextView        errorMessageSignIn;
    private String          defaultValue;
    private CheckBox        rememberMe;
    private TextInputLayout inputLayoutMail, inputLayoutPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputLayoutMail      = (TextInputLayout) findViewById(R.id.input_layout_email_sign_in);
        inputLayoutPassword  = (TextInputLayout) findViewById(R.id.input_layout_password_sign_in);

        errorMessageSignIn   = (TextView) findViewById(R.id.sign_in_layout_error);
        emailSignInTxt       = (TextView) findViewById(R.id.input_email_sign_in);
        passwordSignInTxt    = (TextView) findViewById(R.id.input_password_sign_in);
        rememberMe           = (CheckBox) findViewById(R.id.remember);

        emailSignInTxt   .addTextChangedListener(new MyTextWatcherLogin(emailSignInTxt));
        passwordSignInTxt.addTextChangedListener(new MyTextWatcherLogin(passwordSignInTxt));

        SharedPreferences sharedPref;
        try {
            sharedPref = this.getPreferences(Context.MODE_PRIVATE);
            defaultValue = sharedPref.getString("rem", "0");
        } catch (Exception e) {
        }

        if (defaultValue != null && defaultValue.equals("1")) {
            // Do the login
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private boolean validateEmail() {
        String email = emailSignInTxt.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutMail.setError(getString(R.string.err_msg_email));
            requestFocus(emailSignInTxt);
            return false;
        } else {
            inputLayoutMail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (passwordSignInTxt.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(passwordSignInTxt);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private class MyTextWatcherLogin implements TextWatcher {

        private View view;

        private MyTextWatcherLogin(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_email:
                    validateEmail();
                    break;
                case R.id.input_password:
                    validatePassword();
                    break;
            }
        }
    }

    public void doSignUp(View view) {
    }

    public void doSignIn(View view) {
        boolean isValid = validateEmail() && validatePassword();

        Log.d(TAG, "doSignIn: ");

        if (isValid) {
            Intent intent = new Intent(this, MainActivity.class);
            SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            if (emailSignInTxt.getText().toString().equalsIgnoreCase("garcia.raul@gmail.com") &&
                    passwordSignInTxt.getText().toString().equalsIgnoreCase("admin")) {
                if (rememberMe.isChecked()) {
                    editor.putString("rem", "1");
                    editor.putString("email", emailSignInTxt.getText().toString());
                    editor.putString("password", passwordSignInTxt.getText().toString());
                } else {
                    editor.putString("rem", "0");
                    editor.putString("email", "");
                    editor.putString("password", "");
                }
                editor.commit();
                startActivity(intent);
            } else {
                errorMessageSignIn.setText("Could not sign in");
            }
        }

    }
}
