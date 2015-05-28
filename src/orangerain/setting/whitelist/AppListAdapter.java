package orangerain.setting.whitelist;

import java.util.ArrayList;
import java.util.List;

import orangerain.hook.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CheckBox;

public class AppListAdapter extends BaseAdapter {  
	public static String TAG = "orangerain.setting.whitelist.AppListAdapter";
	
	List<AppInfo> list = new ArrayList<AppInfo>();  
    LayoutInflater la;  
    Context context;  
    
      
    public AppListAdapter(List<AppInfo> list ,Context context){  
        this.list = list;  
        this.context = context;  
    }  
      
    @Override  
    public int getCount() {  
        // TODO Auto-generated method stub  
        return list.size();  
    }  
    @Override  
    public Object getItem(int position) {  
        // TODO Auto-generated method stub  
        return list.get(position);  
    }  
    @Override  
    public long getItemId(int position) {  
        // TODO Auto-generated method stub  
        return position;  
    }  
    @Override  
    public View getView(final int position, View convertView, ViewGroup parent) {  
    	final SharedPreferences sp = context.getSharedPreferences("orangerain", Context.MODE_WORLD_READABLE);
    	final SharedPreferences.Editor editor = sp.edit();
    	
        AppInfoViewer viewer;  
        
        if(convertView == null)  
        {   
        	la = LayoutInflater.from(context);  
            convertView=la.inflate(R.layout.item_whitelist, null);  
              
            viewer = new AppInfoViewer();  
            viewer.icon = (ImageView) convertView.findViewById(R.id.whitelist_icon);  
            viewer.app_name = (TextView) convertView.findViewById(R.id.whitelist_app_name);  
            viewer.package_name = (TextView) convertView.findViewById(R.id.whitelist_package_name);  
            viewer.is_white = (CheckBox) convertView.findViewById(R.id.whitelist_is_white);  
            
            convertView.setTag(viewer);
        	
        }else{  
            viewer = (AppInfoViewer) convertView.getTag();  
        }  
        
        viewer.is_white.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Boolean ischecked = ((CheckBox)v).isChecked();
				editor.putBoolean(((AppInfo)list.get(position)).package_name, ischecked);
				editor.commit();
				Log.i(TAG, ((AppInfo)list.get(position)).package_name + " is white: " + ischecked);
				((AppInfo)list.get(position)).is_white = ischecked;
				AppListAdapter.this.notifyDataSetChanged();
			}
        });
        
        viewer.icon.setImageDrawable(((AppInfo)list.get(position)).icon);  
        viewer.app_name.setText(((AppInfo)list.get(position)).app_name);  
        viewer.package_name.setText(((AppInfo)list.get(position)).package_name); 
        viewer.is_white.setChecked(((AppInfo)list.get(position)).is_white);
        
        return convertView;  
    }  
}  