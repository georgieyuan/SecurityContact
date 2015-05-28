package orangerain.setting.crypt;

import orangerain.setting.MainActivity;
import orangerain.setting.password.ChangePasswordActivity;
import orangerain.setting.whitelist.WhitelistActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import orangerain.hook.R;

public class CryptAlgorithmActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_crypt_algorithm);
		
		final TextView note = (TextView)findViewById(R.id.setting_crypt_note1);
		RadioGroup rg = (RadioGroup)findViewById(R.id.setting_crypt_radiogroup);
		RadioButton rb_aes = (RadioButton)findViewById(R.id.setting_crypt_aes);
		RadioButton rb_des = (RadioButton)findViewById(R.id.setting_crypt_des);
		
		// 配置信息存储
		final SharedPreferences sp = getSharedPreferences("orangerain", Context.MODE_WORLD_READABLE);
		final SharedPreferences.Editor editor = sp.edit();
		
		String crypt_algorithm = sp.getString("algorithm", "");
		if (crypt_algorithm.equals(null) || crypt_algorithm.equalsIgnoreCase("")) {
			editor.putString("algorithm", "aes");
			editor.commit();
		}
		crypt_algorithm = sp.getString("algorithm", "");
		
		rb_aes.setChecked(crypt_algorithm.equalsIgnoreCase("aes"));
		rb_des.setChecked(crypt_algorithm.equalsIgnoreCase("des"));
		
		if (rb_aes.isChecked())
			note.setText("当前加解密算法为AES算法");
		if (rb_des.isChecked())
			note.setText("当前加解密算法为DES算法"); 
		
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) 
			{
				switch (checkedId)
	    		{
	    		case R.id.setting_crypt_aes:
	    			note.setText("当前加解密算法为AES算法"); 
	    			editor.putString("algorithm", "aes");
	    			editor.commit();
	    			break;
	    		case R.id.setting_crypt_des:
	    			note.setText("当前加解密算法为DES算法");
	    			editor.putString("algorithm", "des");
	    			editor.commit();
	    			break;
	    		default:
	    			break;
	    		}
			}
		});
	}
}
