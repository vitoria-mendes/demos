package mobile.liferay.com.formsscreenletdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.login.LoginScreenlet;
import com.liferay.mobile.screens.context.User;

public class LoginActivity extends AppCompatActivity implements LoginListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        LoginScreenlet loginScreenlet = findViewById(R.id.login);
        loginScreenlet.setListener(this);
    }

    @Override
    public void onLoginSuccess(User user) {
        Toast.makeText(LoginActivity.this, "Login is Successful!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onLoginFailure(Exception e) {
        Toast.makeText(LoginActivity.this, "Login Faild!", Toast.LENGTH_SHORT).show();
        Log.e("Test", e.getMessage());
    }

    @Override
    public void onAuthenticationBrowserShown() {

    }
}
