package orangerain.setting.whitelist;

import java.util.ArrayList;
import java.util.List;

import orangerain.hook.R;

import de.robv.android.xposed.XposedBridge;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class WhitelistActivity extends ListActivity {  
	
	public static String TAG = "orangerain.setting.whitelist.WhitelistActivity";
	
	private ListView lv;
	
	SharedPreferences sp;
	SharedPreferences.Editor editor;
	
	// 判断权限列表中是否存在读取联系人的权限
	public boolean hasContactPermission(String[] permissions)
	{
		if (permissions == null) {
			Log.i(TAG, "permissions is null");
			return false;
		}
			
		for (int i=0; i<permissions.length; i++) {
			if (permissions[i].equalsIgnoreCase("android.permission.READ_CONTACTS"))
				return true;
		}
		return false;
	}
	
	// 系统中所有的应用程序  
    public List<AppInfo> getAppList() {  
    	//得到PackageManager对象  
        PackageManager pm = getPackageManager();  
        
        //得到系统安装的所有程序包的PackageInfo对象  
        List<PackageInfo> packs = pm.getInstalledPackages(0);  
        
        List<AppInfo> list = new ArrayList<AppInfo>(); 
          
        for(PackageInfo pi:packs){  // 会遍历packs中所有数据,pi是变量
        	String[] permissions;
			try {
				permissions = pm.getPackageInfo(pi.packageName, PackageManager.GET_PERMISSIONS).requestedPermissions;
				
				// 筛选应用程序列表，只有具有读取联系人权限android.permission.READ_CONTACTS的应用才读取
				if (hasContactPermission(permissions))
				{
					AppInfo info = new AppInfo();  
		            info.icon = pi.applicationInfo.loadIcon(pm);
		            info.app_name = pi.applicationInfo.loadLabel(pm).toString();
		            info.package_name = pi.applicationInfo.packageName;
		            info.is_white = sp.getBoolean(info.package_name, false);
		            list.add(info);
				}
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }  
        
        return list;
    }
	
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS); 
        
        setContentView(R.layout.main_whitelist);
        lv = (ListView)findViewById(android.R.id.list);
        
        sp = getSharedPreferences("orangerain", Context.MODE_WORLD_READABLE);
    	editor = sp.edit();
		
		final List<AppInfo> list = getAppList(); 
		final AppListAdapter adapter = new AppListAdapter(list, this);
		lv.setAdapter(adapter);
    }  
}  