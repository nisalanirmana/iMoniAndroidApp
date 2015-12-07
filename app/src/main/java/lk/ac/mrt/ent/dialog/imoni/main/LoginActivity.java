package lk.ac.mrt.ent.dialog.imoni.main;

/**
 * @author Anuruddha Hettiarachchi
 *
 */

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class LoginActivity extends Activity {
	String mComPrefix;
	String mPassword;
	EditText mPasswordInput;
	ProgressDialog mProgressDialog;
	String mUsername;
	EditText mUsernameInput;
  
	void ConnectWS(RequestParams params) {
		mProgressDialog.show();
		new AsyncHttpClient().get("http://203.189.68.253:8081/imoniws/login", params, new AsyncHttpResponseHandler() {
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				LoginActivity.this.mProgressDialog.dismiss();
				Toast.makeText(LoginActivity.this.getApplicationContext(), "Login failed. Please try again..", Toast.LENGTH_SHORT).show();
			}
  
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				mProgressDialog.dismiss();
				try {
					if (new JSONObject(new String(responseBody)).getBoolean("status")) {
					    Toast.makeText(getApplicationContext(), "Login success..", Toast.LENGTH_SHORT).show();
					    Variables.mComPrefix = mComPrefix;
					    Intent localIntent = new Intent(getApplicationContext(), MainActivity.class);
					    startActivity(localIntent);
					} else {
						Toast.makeText(getApplicationContext(), "Invalid username or password..", Toast.LENGTH_SHORT).show();	
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
  
	void Login() {
		String str = mUsernameInput.getText().toString();
		mUsername = str.substring(1 + str.indexOf('/'));
		mComPrefix = str.substring(0, str.indexOf('/'));
		mPassword = mPasswordInput.getText().toString();
		RequestParams params = new RequestParams();
		if (TextValidation.ValidateText(mUsername)) {
			if (TextValidation.ValidateUsername(mUsername)) {
				if (TextValidation.ValidateText(mPassword)) {
					params.add("com-prefix", mComPrefix);
					params.add("username", mUsername);
					params.add("password", mPassword);
					ConnectWS(params);
				} else {
					Toast.makeText(getApplicationContext(), "Please enter the password", Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(getApplicationContext(), "Invalid username", Toast.LENGTH_SHORT).show();
			}
		} else{
			Toast.makeText(getApplicationContext(), "Please enter a username", Toast.LENGTH_SHORT).show();
		}
	}
  
	protected void onCreate(Bundle status) {
		super.onCreate(status);
		setContentView(R.layout.activity_login);
		mUsernameInput = ((EditText)findViewById(R.id.login_username_input));
		mPasswordInput = ((EditText)findViewById(R.id.login_password_input));
		((Button)findViewById(R.id.login_button)).setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Login();
			}
		});
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setTitle("Please wait..");
	}
}