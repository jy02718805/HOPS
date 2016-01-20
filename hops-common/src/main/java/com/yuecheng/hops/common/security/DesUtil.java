package com.yuecheng.hops.common.security;


import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class DesUtil
{
    // 算法定义
    private static final String Algorithm = "DESede";
    
    private static final Logger logger = LoggerFactory.getLogger(DesUtil.class);

    public static void main(String[] args)
    {
        byte[] strByte = encryptDes("123456789012345678901234".getBytes(),
            "N6L2N4JVBZ0JR8B026RN880Z6TDN468B".getBytes());
        String str = byte2Hex(strByte);
        logger.info(str);
    }
    /**
     * 加密
     * @param key 3Des密钥
     * @param src  待加密数据
     * @return 加密后的密文字符串
     */
    public static String encryptDes(String key, String src)
    {
        byte[] keybyte=key.getBytes();
        byte[] srcbyte=src.getBytes();
        byte[] valueByte=encryptDes(keybyte, srcbyte);
        return byte2Hex(valueByte);
    }

    public static byte[] encryptDes(byte[] keybyte, byte[] src)
    {
        SecretKey desKey = new SecretKeySpec(keybyte, Algorithm);
        Cipher cipher;
        try
        {
            cipher = Cipher.getInstance(Algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, desKey);
            return cipher.doFinal(src);
        }
        catch (NoSuchAlgorithmException e)
        {
        	logger.error("DesUtil:[encryptDes-NoSuchAlgorithmException]["+e.getMessage()+"]");
        }
        catch (NoSuchPaddingException e)
        {
        	logger.error("DesUtil:[encryptDes-NoSuchPaddingException]["+e.getMessage()+"]");
        }
        catch (InvalidKeyException e)
        {
        	logger.error("DesUtil:[encryptDes-InvalidKeyException]["+e.getMessage()+"]");
        }
        catch (IllegalBlockSizeException e)
        {
        	logger.error("DesUtil:[encryptDes-IllegalBlockSizeException]["+e.getMessage()+"]");
        }
        catch (BadPaddingException e)
        {
        	logger.error("DesUtil:[encryptDes-BadPaddingException]["+e.getMessage()+"]");
        }
        return null;
    }
    /**
     * 解密
     * @param key 3Des密钥
     * @param src 待解密数据 
     * @return 解密后的明文字符串
     */
    public static String decryptDes(String key, String src)
    {
        byte[] srcbyte=toStringHex(src);
        byte[] keybyte=key.getBytes();
        byte[] valueByte=decryptDes(keybyte, srcbyte);
        return new String(valueByte);
    }
    public static byte[] decryptDes(byte[] keybyte, byte[] src)
    {
        SecretKey desKey = new SecretKeySpec(keybyte, Algorithm);
        Cipher cipher;
        try
        {
            cipher = Cipher.getInstance(Algorithm);
            cipher.init(Cipher.DECRYPT_MODE, desKey);
            return cipher.doFinal(src);
        }
        catch (NoSuchAlgorithmException e)
        {
        	logger.error("DesUtil:[decryptDes-NoSuchAlgorithmException]["+e.getMessage()+"]");
        }
        catch (NoSuchPaddingException e)
        {
        	logger.error("DesUtil:[decryptDes-NoSuchPaddingException]["+e.getMessage()+"]");
        }
        catch (InvalidKeyException e)
        {
        	logger.error("DesUtil:[decryptDes-InvalidKeyException]["+e.getMessage()+"]");
        }
        catch (IllegalBlockSizeException e)
        {
        	logger.error("DesUtil:[decryptDes-IllegalBlockSizeException]["+e.getMessage()+"]");
        }
        catch (BadPaddingException e)
        {
        	logger.error("DesUtil:[decryptDes-BadPaddingException]["+e.getMessage()+"]");
        }
        return null;
    }

    // 字符转16进制
    public static String byte2Hex(byte[] b)
    {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++ )
        {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
            {
                hs = hs + "0" + stmp;
            }
            else
            {
                hs = hs + stmp;
            }
            if (n < b.length - 1) hs = hs + "";
        }
        return hs.toUpperCase();
    }

    // 16进制转字符
    public static byte[] toStringHex(String s)
    {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++ )
        {
            try
            {
                baKeyword[i] = (byte)(0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            }
            catch (Exception e)
            {
            	logger.error("DesUtil:[toStringHex]["+e.getMessage()+"]");
            }
        }
        return baKeyword;
    }
}
