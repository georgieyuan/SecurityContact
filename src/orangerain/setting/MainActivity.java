package orangerain.setting;

import java.util.ArrayList;
import java.util.List;

import orangerain.setting.crypt.CryptAlgorithmActivity;
import orangerain.setting.password.ChangePasswordActivity;
import orangerain.setting.password.NewPasswordActivity;
import orangerain.setting.whitelist.AppInfo;
import orangerain.setting.whitelist.AppListAdapter;
import orangerain.setting.whitelist.WhitelistActivity;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import orangerain.hook.R;

public class MainActivity extends ListActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.main_item);
		
		// 配置信息存储
		final SharedPreferences sp = getSharedPreferences("orangerain", Context.MODE_WORLD_READABLE);
		
		// 如果没有配置加密算法，默认设置为AES算法
		SharedPreferences.Editor editor = sp.edit();
		String crypt_algorithm = sp.getString("algorithm", "");
		if (crypt_algorithm.equals(null) || crypt_algorithm.equalsIgnoreCase("")) {
			editor.putString("algorithm", "aes");
			editor.commit();
		}
		
		List<MenuInfo> list = new ArrayList<MenuInfo>();  
		
		MenuInfo info1 = new MenuInfo();
		info1.icon = getResources().getDrawable(R.drawable.menu_aes);
		info1.name = "设置加解密算法";
		info1.description = "可选择的算法有AES和DES";
		list.add(info1);
		
		MenuInfo info2 = new MenuInfo();
		info2.icon = getResources().getDrawable(R.drawable.menu_key);
		info2.name = "设置密钥";
		info2.description = "用于AES和DES加密";
		list.add(info2);
		
		MenuInfo info3 = new MenuInfo();
		info3.icon = getResources().getDrawable(R.drawable.menu_whitelist);
		info3.name = "设置白名单";
		info3.description = "白名单应用程序读取联系人不加密";
		list.add(info3);
		
		MenuAdapter adapter = new MenuAdapter(list, this);
	    getListView().setAdapter(adapter);
	    
	    getListView().setOnItemClickListener(new OnItemClickListener() {
	    	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	    		switch (position)
	    		{
	    		case 0:
	    			Intent intent0 = new Intent();  
	    	        intent0.setClass(MainActivity.this, CryptAlgorithmActivity.class);
	    	        startActivity(intent0);  
	    			break;
	    		case 1:
	    			Intent intent1 = new Intent(); 
	    			String old_password = sp.getString("password", "");
	    			if (old_password.equalsIgnoreCase(""))
	    				intent1.setClass(MainActivity.this, NewPasswordActivity.class);
	    			else
	    				intent1.setClass(MainActivity.this, ChangePasswordActivity.class);
	    			
	    	        startActivity(intent1);  
	    			break;
	    		case 2:
	    			Intent intent2 = new Intent();  
	    	        intent2.setClass(MainActivity.this, WhitelistActivity.class);
	    	        startActivity(intent2);  
	    			break;
	    		default:
	    			break;
	    		}
	    	}
		});
	}
}
