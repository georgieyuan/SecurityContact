package orangerain.setting.password;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import orangerain.hook.R;

public class ChangePasswordActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_change_password);
		
		SharedPreferences sp = getSharedPreferences("orangerain", Context.MODE_WORLD_READABLE);
		final SharedPreferences.Editor editor = sp.edit();
		final String old_password = sp.getString("password", "");
		
		Button button_ok = (Button)findViewById(R.id.setting_change_password_ok);
		button_ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText edittext_old_password = (EditText)findViewById(R.id.setting_change_password_old);
				EditText edittext_new_password1 = (EditText)findViewById(R.id.setting_change_password_new1);
				EditText edittext_new_password2 = (EditText)findViewById(R.id.setting_change_password_new2);
				
				String input_old_password = edittext_old_password.getText().toString();
				String input_new_password1 = edittext_new_password1.getText().toString();
				String input_new_password2 = edittext_new_password2.getText().toString();
				
				if (input_old_password.equals(null) || input_old_password.equalsIgnoreCase("")) {
					Toast.makeText(getApplicationContext(), "请输入旧密钥", Toast.LENGTH_LONG).show();
				}
				else if (!input_old_password.equalsIgnoreCase(old_password)) {
					Toast.makeText(getApplicationContext(), "旧密钥输入错误", Toast.LENGTH_LONG).show();
				}
				else if (input_new_password1.equals(null) || input_new_password1.equalsIgnoreCase("") 
						|| input_new_password2.equals(null) || input_new_password2.equalsIgnoreCase("")) {
					Toast.makeText(getApplicationContext(), "新密钥不能为空", Toast.LENGTH_LONG).show();
				}
				else if (!input_new_password1.equals(input_new_password2)) {
					Toast.makeText(getApplicationContext(), "两次新密钥输入不一致", Toast.LENGTH_LONG).show();
				}
				else {
					editor.putString("password", input_new_password2);
					editor.commit();
					Toast.makeText(getApplicationContext(), "新密钥保存成功", Toast.LENGTH_SHORT).show();
					finish();
				}
			}
		});
	}
}
