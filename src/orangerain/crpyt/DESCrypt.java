package orangerain.crpyt;

import java.security.SecureRandom;  

import javax.crypto.Cipher;  
import javax.crypto.SecretKey;  
import javax.crypto.SecretKeyFactory;  
import javax.crypto.spec.DESKeySpec;  

import android.util.Base64;

public class DESCrypt {
	private static byte[] key = "你的key".getBytes();  
    //解密方式如下：  
    //new String(DESUtil.decrypt(Base64.decode("pKkeY+XC8zPKjzPloikX2H8HpfPcztNE", 0)))  
    /** 
     * 加密函数 
     * @param data 加密数据 
     * @param key  密钥 
     * @return 返回加密后的数据 
     */  
    public static byte[] encrypt(byte[] key, byte[] data) {  
        try {  
            // DES算法要求有一个可信任的随机数源  
            SecureRandom sr = new SecureRandom();  
            // 从原始密钥数据创建DESKeySpec对象  
            DESKeySpec dks = new DESKeySpec(key);  
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成  
            // 一个SecretKey对象  
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
            SecretKey secretKey = keyFactory.generateSecret(dks);  
            // using DES in ECB mode  
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");  
            // 用密匙初始化Cipher对象  
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);  
            // 执行加密操作  
            byte encryptedData[] = cipher.doFinal(data);  
            return Base64.encode(encryptedData, 0);
            //return encryptedData;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
  
    /** 
     * 解密函数 
     * @param data 解密数据 
     * @param key 密钥 
     * @return 返回解密后的数据 
     */  
    public static byte[] decrypt(byte[] key, byte[] data) {  
        try {  
        	byte[] data2 = Base64.decode(data, 0);
            // DES算法要求有一个可信任的随机数源  
            SecureRandom sr = new SecureRandom();  
            // byte rawKeyData[] = /* 用某种方法获取原始密匙数据 */;  
            // 从原始密匙数据创建一个DESKeySpec对象  
            DESKeySpec dks = new DESKeySpec(key);  
            // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成  
            // 一个SecretKey对象  
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
            SecretKey secretKey = keyFactory.generateSecret(dks);  
            // using DES in ECB mode  
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");  
            // 用密匙初始化Cipher对象  
            cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);  
            // 正式执行解密操作  
            byte decryptedData[] = cipher.doFinal(data2);  
              
            return decryptedData;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
}
