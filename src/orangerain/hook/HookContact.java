package orangerain.hook;

import static de.robv.android.xposed.XposedHelpers.findClass;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.ContentProviderOperation;

import orangerain.crpyt.AESCrypt;
import orangerain.crpyt.DESCrypt;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Parcel;
import android.util.Log;

public class HookContact implements IXposedHookLoadPackage {
	
	public static String TAG = "orangerain.hook.HookContact";
	
	@Override
    public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
    	
    	XposedBridge.log(TAG + " Loaded app: " + lpparam.packageName);
    	
    	XSharedPreferences pre = new XSharedPreferences(this.getClass().getPackage().getName(), "orangerain");
    	
    	// �������б��е�Ӧ�ò�����������ܴ���
    	if (pre.getBoolean(lpparam.packageName, false)) {
    		Log.i(TAG, lpparam.packageName + " is in whitelist, not encrypt.");
    		return;
    	}
    	Log.i(TAG, lpparam.packageName + " is not whitelist, encrypt.");
        
        try {
        	
        	/*
        	// ��Android��ܵ�android.content.ContentResolver����нػ�Hook��
        	final Class<?> cResolver2 = findClass("android.content.Builder", lpparam.classLoader);
        	
        	// �ػ�android.content.ContentResolver���insert����
        	XposedBridge.hookAllMethods(cResolver2, "withValue", new XC_MethodHook() {
        		@Override
            	// ��ѯ������ԭ������֮ǰ��������������
                protected void beforeHookedMethod(MethodHookParam param) throws
                        Throwable {
        			Log.i(TAG, "hook withValue, beforeHookedMethod");
                }

                @Override
                // ��ѯ������ԭ������֮�󣬽���ѯ���Ľ�����м���
                protected void afterHookedMethod(MethodHookParam param) throws
                        Throwable {
                	Log.i(TAG, "hook withValue, afterHookedMethod");
                }
        	});
        	*/
        	
            // ��Android��ܵ�android.content.ContentResolver����нػ�Hook��
        	final Class<?> cResolver = findClass("android.content.ContentResolver", lpparam.classLoader);
        	
        	/*
        	// �ػ�android.content.ContentResolver���insert����
        	XposedBridge.hookAllMethods(cResolver, "insert", new XC_MethodHook() {
        		@Override
            	// ��ѯ������ԭ������֮ǰ��������������
                protected void beforeHookedMethod(MethodHookParam param) throws
                        Throwable {
        			Log.i(TAG, "hook insert, beforeHookedMethod");
                }

                @Override
                // ��ѯ������ԭ������֮�󣬽���ѯ���Ľ�����м���
                protected void afterHookedMethod(MethodHookParam param) throws
                        Throwable {
                	Log.i(TAG, "hook insert, afterHookedMethod");
                }
        	});
        	
        	
        	// �ػ�android.content.ContentResolver���insert����
        	XposedBridge.hookAllMethods(cResolver, "applyBatch", new XC_MethodHook() {
        		@Override
            	// ��ѯ������ԭ������֮ǰ��������������
                protected void beforeHookedMethod(MethodHookParam param) throws
                        Throwable {
        			Log.i(TAG, "hook applyBatch, beforeHookedMethod, arg[0] " + param.args[0]);
        			
        			ArrayList<ContentProviderOperation> ops = (ArrayList<ContentProviderOperation>) param.args[1];
        			for (ContentProviderOperation op:ops) {
        				Log.i(TAG, "hook applyBatch, beforeHookedMethod, op:" + op.toString());
        				
        				Parcel dest = Parcel.obtain();
        				
        				op.writeToParcel(dest, 0);
        				//Log.i(TAG, "hook applyBatch, beforeHookedMethod, parcel:" + dest.);
        				int mType = dest.readInt();
        				Log.i(TAG, "hook applyBatch, beforeHookedMethod, mType:" + mType);
        				//insert
        		        //if (mType != 1) 
        		        //	break;
        		        
        		        Uri mUri = Uri.CREATOR.createFromParcel(dest);
        		        Log.i(TAG, "hook applyBatch, beforeHookedMethod, mUri" + mUri.toString());
        		        if ((!mUri.toString().toLowerCase().startsWith("content://com.android.contacts/contacts"))
                    			&&(!mUri.toString().toLowerCase().startsWith("content://com.android.contacts/data"))
                    			&&(!mUri.toString().toLowerCase().startsWith("content://com.android.contacts/raw_contacts"))
                        	&& (!mUri.toString().toLowerCase().startsWith("content://com.android.contacts/phone_lookup")))
                        	break;
        		        
        		        ContentValues mValues = dest.readInt() != 0 ? ContentValues.CREATOR.createFromParcel(dest) : null;
        				for (String s: mValues.keySet()) {
            				Log.i(TAG, "hook applyBatch, beforeHookedMethod, ContentValues, key: "+ s);
            				Log.i(TAG, "hook applyBatch, beforeHookedMethod, ContentValues, value: "+ mValues.getAsString(s));
            				}
        			}
        			
        			//ContentValues values = (ContentValues) param.args[1];
        			//Log.i(TAG, "hook applyBatch, beforeHookedMethod, uri: "+ uri.toString());
        			//for (String s: values.keySet()) {
        			//	Log.i(TAG, "hook applyBatch, beforeHookedMethod, ContentValues: "+ s);
        			//}
                }

                @Override
                // ��ѯ������ԭ������֮�󣬽���ѯ���Ľ�����м���
                protected void afterHookedMethod(MethodHookParam param) throws
                        Throwable {
                	Log.i(TAG, "hook applyBatch, afterHookedMethod");
                }
        	});
        	*/

            // �ػ�android.content.ContentResolver���query����
        	XposedBridge.hookAllMethods(cResolver, "query", new XC_MethodHook() {
                @Override
            	// ��ѯ������ԭ������֮ǰ��������������
                protected void beforeHookedMethod(MethodHookParam param) throws
                        Throwable {
                }

                @Override
                // ��ѯ������ԭ������֮�󣬽���ѯ���Ľ�����м���
                protected void afterHookedMethod(MethodHookParam param) throws
                        Throwable {
                	
                	//ֻ������ϵ�����ݿ���У��������ݿ����
                	Uri uri = (Uri) param.args[0];
                	
                	//ֻ������������ͷ��uri�Ŵ����������
                	if ((!uri.toString().toLowerCase().startsWith("content://com.android.contacts/contacts"))
                			&&(!uri.toString().toLowerCase().startsWith("content://com.android.contacts/data"))
                			&&(!uri.toString().toLowerCase().startsWith("content://com.android.contacts/raw_contacts"))
                    	&& (!uri.toString().toLowerCase().startsWith("content://com.android.contacts/phone_lookup")))
                    	return;
                	
                    Cursor cursor = (Cursor) param.getResult();
                    
                    // Modify column names back
                    // TODO ��֪����һ����ʲô�ã�
					Object column_added = param.getObjectExtra("column_added");
					boolean added = (column_added == null ? false : (Boolean) param.getObjectExtra("column_added"));
					List<String> listColumn = new ArrayList<String>();
					listColumn.addAll(Arrays.asList(cursor.getColumnNames()));
					if (added)
						listColumn.remove(listColumn.size() - 1);
					
					MatrixCursor result = new MatrixCursor(listColumn.toArray(new String[0]));

					// Filter rows
					while (cursor.moveToNext()) {
						copyColumns(cursor, result, listColumn.size());
					}
					
					result.respond(cursor.getExtras());
					param.setResult(result);
					cursor.close();
                }
            });

        } catch (Throwable t) {
            throw t;
        }
    }
    
	private void copyColumns(Cursor cursor, MatrixCursor result, int count) {
		try {
			Object[] columns = new Object[count];
			for (int i = 0; i < count; i++)
				switch (cursor.getType(i)) {
				case Cursor.FIELD_TYPE_NULL:
					columns[i] = null;
					break;
				case Cursor.FIELD_TYPE_INTEGER:
					columns[i] = cursor.getInt(i);
					break;
				case Cursor.FIELD_TYPE_FLOAT:
					columns[i] = cursor.getFloat(i);
					break;
				case Cursor.FIELD_TYPE_STRING:
					if ((cursor.getColumnName(i).equalsIgnoreCase("lookup")) ||
						(cursor.getColumnName(i).equalsIgnoreCase("account_name")) ||
						(cursor.getColumnName(i).equalsIgnoreCase("account_type")) ||
						(cursor.getColumnName(i).equalsIgnoreCase("sort_key")) ||
						(cursor.getColumnName(i).equalsIgnoreCase("mimetype")) ||
						(cursor.getColumnName(i).equalsIgnoreCase("account_type_and_data_set")))
					{
						columns[i] = cursor.getString(i);
					} else {
						XSharedPreferences pre = new XSharedPreferences(this.getClass().getPackage().getName(), "orangerain");
				    	
				    	
				    	String algorithm = pre.getString("algorithm", "aes");
						if (algorithm.equalsIgnoreCase("des")) {
				    		//Log.i(TAG, "crypt algorithm is des");
				    		columns[i] = DESCrypt.encrypt(pre.getString("password", "").getBytes(), cursor.getString(i).getBytes());
				    	}
				    	else
				    	{
				    		//Log.i(TAG, "crypt algorithm is aes");
				    		columns[i] = AESCrypt.encrypt(pre.getString("password", ""), cursor.getString(i));
				    	}
						//columns[i] = aes.encryptAsBase64(cursor.getString(i).getBytes());
						//Log.i(TAG,"column name is: " + cursor.getColumnName(i));
						//Log.i(TAG, "column[" + i + "] before: " + cursor.getString(i) + "after: " + columns[i]);
					}
					
					break;
				case Cursor.FIELD_TYPE_BLOB:
					columns[i] = cursor.getBlob(i);
					break;
				default:
					Log.i(TAG, "Unknown cursor data type=" + cursor.getType(i));
				}
			result.addRow(columns);
		} catch (Throwable ex) {
			Log.i(TAG, ex.getMessage());
		}
	}
}
