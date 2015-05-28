package orangerain.crpyt;

import java.security.SecureRandom;  

import javax.crypto.Cipher;  
import javax.crypto.SecretKey;  
import javax.crypto.SecretKeyFactory;  
import javax.crypto.spec.DESKeySpec;  

import android.util.Base64;

public class DESCrypt {
	private static byte[] key = "���key".getBytes();  
    //���ܷ�ʽ���£�  
    //new String(DESUtil.decrypt(Base64.decode("pKkeY+XC8zPKjzPloikX2H8HpfPcztNE", 0)))  
    /** 
     * ���ܺ��� 
     * @param data �������� 
     * @param key  ��Կ 
     * @return ���ؼ��ܺ������ 
     */  
    public static byte[] encrypt(byte[] key, byte[] data) {  
        try {  
            // DES�㷨Ҫ����һ�������ε������Դ  
            SecureRandom sr = new SecureRandom();  
            // ��ԭʼ��Կ���ݴ���DESKeySpec����  
            DESKeySpec dks = new DESKeySpec(key);  
            // ����һ���ܳ׹�����Ȼ��������DESKeySpecת����  
            // һ��SecretKey����  
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
            SecretKey secretKey = keyFactory.generateSecret(dks);  
            // using DES in ECB mode  
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");  
            // ���ܳ׳�ʼ��Cipher����  
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);  
            // ִ�м��ܲ���  
            byte encryptedData[] = cipher.doFinal(data);  
            return Base64.encode(encryptedData, 0);
            //return encryptedData;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
  
    /** 
     * ���ܺ��� 
     * @param data �������� 
     * @param key ��Կ 
     * @return ���ؽ��ܺ������ 
     */  
    public static byte[] decrypt(byte[] key, byte[] data) {  
        try {  
        	byte[] data2 = Base64.decode(data, 0);
            // DES�㷨Ҫ����һ�������ε������Դ  
            SecureRandom sr = new SecureRandom();  
            // byte rawKeyData[] = /* ��ĳ�ַ�����ȡԭʼ�ܳ����� */;  
            // ��ԭʼ�ܳ����ݴ���һ��DESKeySpec����  
            DESKeySpec dks = new DESKeySpec(key);  
            // ����һ���ܳ׹�����Ȼ��������DESKeySpec����ת����  
            // һ��SecretKey����  
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");  
            SecretKey secretKey = keyFactory.generateSecret(dks);  
            // using DES in ECB mode  
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");  
            // ���ܳ׳�ʼ��Cipher����  
            cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);  
            // ��ʽִ�н��ܲ���  
            byte decryptedData[] = cipher.doFinal(data2);  
              
            return decryptedData;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
}
