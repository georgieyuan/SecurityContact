package orangerain.setting;

import java.util.ArrayList;
import java.util.List;

import orangerain.hook.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CheckBox;

public class MenuAdapter extends BaseAdapter {  
    List<MenuInfo> list = new ArrayList<MenuInfo>();  
    LayoutInflater la;  
    Context context;  
      
    public MenuAdapter(List<MenuInfo> list ,Context context){  
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
    public View getView(int position, View convertView, ViewGroup parent) {  
        MenuInfoViewer viewer;  
        if(convertView == null)  
        {   
        	la = LayoutInflater.from(context);  
            convertView=la.inflate(R.layout.item_main, null);  
              
            viewer = new MenuInfoViewer();  
            viewer.icon = (ImageView) convertView.findViewById(R.id.main_icon);  
            viewer.name = (TextView) convertView.findViewById(R.id.main_name);  
            viewer.description = (TextView) convertView.findViewById(R.id.main_description);  
              
            convertView.setTag(viewer);  
        }else{  
            viewer = (MenuInfoViewer) convertView.getTag();  
        }  
        
        final MenuInfo info = (MenuInfo)list.get(position);  
        viewer.icon.setImageDrawable(info.icon);  
        viewer.name.setText(info.name);  
        viewer.description.setText(info.description);
          
        return convertView;  
    }  
}  