package com.yuecheng.hops.common.security;


import java.io.ByteArrayOutputStream;
import java.net.URLDecoder;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.utils.StringUtil;


/** */
/**
 * <p> RSA公钥/私钥/签名工具包 </p> <p> 罗纳德·李维斯特（Ron [R]ivest）、阿迪·萨莫尔（Adi [S]hamir）和伦纳德·阿德曼（Leonard
 * [A]dleman） </p> <p> 字符串格式的密钥在未在特殊说明情况下都为BASE64编码格式<br/> 由于非对称加密速度极其缓慢，一般文件不使用它来加密而是使用对称加密，<br/>
 * 非对称加密算法可以用来对对称加密的密钥加密，这样保证密钥的安全也就保证了数据的安全 </p>
 * 
 * @author IceWee
 * @date 2012-4-26
 * @version 1.0
 */
public class RSAUtils
{
    private static final Logger logger = LoggerFactory.getLogger(RSAUtils.class);

    /** */
    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";

    /** */
    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /** */
    /**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";

    /** */
    /**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /** */
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /** */
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /** */
    /**
     * <p> 生成密钥对(公钥和私钥) </p>
     * 
     * @return
     * @throws Exception
     */
    public static Map<String, Object> genKeyPair()
        throws Exception
    {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /** */
    /**
     * <p> 用私钥对信息生成数字签名 </p>
     * 
     * @param data
     *            已加密数据
     * @param privateKey
     *            私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey)
        throws Exception
    {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);
        return Base64Utils.encode(signature.sign());
    }

    /** */
    /**
     * <p> 校验数字签名 </p>
     * 
     * @param data
     *            已加密数据
     * @param publicKey
     *            公钥(BASE64编码)
     * @param sign
     *            数字签名
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign)
        throws Exception
    {
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(Base64Utils.decode(sign));
    }

    /**
     * 私钥解密
     * 
     * @param key
     *            待解密数据
     * @param privateKey
     *            私钥对象
     * @return 解密后的明文字符串
     * @throws Exception
     */
    public static String decryptByPrivateKey(String key, RSAPrivateKey privateKey)
        throws Exception
    {
        String rsakey = getKeyString(privateKey);
        byte[] toByteText = toStringHex(key);
        byte[] decryptData = decryptByPrivateKey(toByteText, rsakey);
        return new String(decryptData);
    }

    /** */
    /**
     * <P> 私钥解密 </p>
     * 
     * @param encryptedData
     *            已加密数据
     * @param privateKey
     *            私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey)
        throws Exception
    {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0)
        {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK)
            {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            }
            else
            {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++ ;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * 公钥解密
     * 
     * @param key
     *            待解密数据
     * @param publicKey
     *            公钥对象
     * @return 解密后的明文字符串
     * @throws Exception
     */
    public static String decryptByPublicKey(String key, RSAPublicKey publicKey)
        throws Exception
    {
        String rsakey = getKeyString(publicKey);
        byte[] toByteText = toStringHex(key);
        byte[] decryptData = decryptByPublicKey(toByteText, rsakey);
        return new String(decryptData);
    }

    /** */
    /**
     * <p> 公钥解密 </p>
     * 
     * @param encryptedData
     *            已加密数据
     * @param publicKey
     *            公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey)
        throws Exception
    {
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0)
        {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK)
            {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            }
            else
            {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++ ;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * 公钥加密
     * 
     * @param key
     *            待加密数据
     * @param publicKey
     *            公钥对象
     * @return 加密后的密文字符串
     * @throws Exception
     */
    public static String encryptByPublicKey(String key, RSAPublicKey publicKey)
        throws Exception
    {
        String rsaKey = getKeyString(publicKey);
        byte[] keybyte = key.getBytes();
        byte[] encryptedData = encryptByPublicKey(keybyte, rsaKey);
        return byte2Hex(encryptedData);
    }

    /** */
    /**
     * <p> 公钥加密 </p>
     * 
     * @param data
     *            源数据
     * @param publicKey
     *            公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey)
        throws Exception
    {
        byte[] keyBytes = Base64Utils.decode(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0)
        {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK)
            {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            }
            else
            {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++ ;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /**
     * 私钥加密
     * 
     * @param key
     *            待加密数据
     * @param privateKey
     *            私钥对象
     * @return 加密后的密文字符串
     * @throws Exception
     */
    public static String encryptByPrivateKey(String key, RSAPrivateKey privateKey)
        throws Exception
    {
        String rsaKey = getKeyString(privateKey);
        byte[] keybyte = key.getBytes();
        byte[] encryptedData = encryptByPrivateKey(keybyte, rsaKey);
        return byte2Hex(encryptedData);
    }

    /** */
    /**
     * <p> 私钥加密 </p>
     * 
     * @param data
     *            源数据
     * @param privateKey
     *            私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey)
        throws Exception
    {
        byte[] keyBytes = Base64Utils.decode(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0)
        {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK)
            {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            }
            else
            {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++ ;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    /** */
    /**
     * <p> 获取私钥 </p>
     * 
     * @param keyMap
     *            密钥对
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap)
        throws Exception
    {
        Key key = (Key)keyMap.get(PRIVATE_KEY);
        return Base64Utils.encode(key.getEncoded());
    }

    /** */
    /**
     * <p> 获取公钥 </p>
     * 
     * @param keyMap
     *            密钥对
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap)
        throws Exception
    {
        Key key = (Key)keyMap.get(PUBLIC_KEY);
        return Base64Utils.encode(key.getEncoded());
    }

    public static RSAPrivateKey getPrivateKeyByString(String privateKey)
    {
        byte[] keyBytes;
        try
        {
            keyBytes = Base64Utils.decode(privateKey);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            RSAPrivateKey privateK = (RSAPrivateKey)keyFactory.generatePrivate(pkcs8KeySpec);
            return privateK;
        }
        catch (Exception e)
        {
            logger.error("RSAUtils:[getPrivateKeyByString][" + e.getMessage() + "]");
        }
        return null;
    }

    public static RSAPublicKey getPublicKeyByString(String publicKey)
    {
        byte[] keyBytes;
        try
        {
            keyBytes = Base64Utils.decode(publicKey);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            RSAPublicKey publicK = (RSAPublicKey)keyFactory.generatePublic(x509KeySpec);
            return publicK;
        }
        catch (Exception e)
        {
            logger.error("RSAUtils:[getPublicKeyByString][" + e.getMessage() + "]");
        }
        return null;
    }

    /**
     * * 加密 *
     * 
     * @param key
     *            加密的密钥 *
     * @param data
     *            待加密的明文数据 *
     * @return 加密后的数据 *
     * @throws Exception
     */
    public static byte[] encryptByJs(String publicKey, byte[] data)
        throws Exception
    {
        try
        {
            // 字符串转key
            byte[] keyBytes = Base64Utils.decode(publicKey);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            RSAPublicKey publicK = (RSAPublicKey)keyFactory.generatePublic(x509KeySpec);

            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM,
                new org.bouncycastle.jce.provider.BouncyCastleProvider());
            cipher.init(Cipher.ENCRYPT_MODE, publicK);
            int blockSize = cipher.getBlockSize();// 获得加密块大小，如：加密前数据为128个byte，而key_size=1024
            // 加密块大小为127
            // byte,加密后为128个byte;因此共有2个加密块，第一个127
            // byte第二个为1个byte
            int outputSize = cipher.getOutputSize(data.length);// 获得加密块加密后块大小
            int leavedSize = data.length % blockSize;
            int blocksSize = leavedSize != 0 ? data.length / blockSize + 1 : data.length
                                                                             / blockSize;
            byte[] raw = new byte[outputSize * blocksSize];
            int i = 0;
            while (data.length - i * blockSize > 0)
            {
                if (data.length - i * blockSize > blockSize)
                    cipher.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);
                else
                    cipher.doFinal(data, i * blockSize, data.length - i * blockSize, raw,
                        i * outputSize);
                // 这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把byte[]放到
                // ByteArrayOutputStream中，而最后doFinal的时候才将所有的byte[]进行加密，可是到了此时加密块大小很可能已经超出了
                // OutputSize所以只好用dofinal方法。

                i++ ;
            }
            return raw;
        }
        catch (Exception e)
        {
            throw new Exception(ExceptionUtil.getStackTraceAsString(e));
        }
    }

    /**
     * JS的RSA解密
     * 
     * @param privateKey
     *            解密密钥对象
     * @param key
     *            待解密数据
     * @return 解密后的明文字符串
     * @throws Exception
     */

    public static String decryptByJs(RSAPrivateKey privateKey, String key)
        throws Exception
    {
        byte[] resultByte = RSAUtils.hexStringToBytes(key);
        String rsaKey = getKeyString(privateKey);
        byte[] decryptData = decryptByJs(rsaKey, resultByte);
        String decryptKey = StringUtil.reverse(new String(decryptData));
        decryptKey = URLDecoder.decode(decryptKey, "UTF-8");
        return decryptKey;
    }

    /**
     * * JS的RSA解密 *
     * 
     * @param key
     *            解密的密钥 *
     * @param raw
     *            已经加密的数据 *
     * @return 解密后的明文 *
     * @throws Exception
     */
    public static byte[] decryptByJs(String privateKey, byte[] raw)
        throws Exception
    {
        try
        {
            byte[] keyBytes = Base64Utils.decode(privateKey);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            RSAPrivateKey privateK = (RSAPrivateKey)keyFactory.generatePrivate(pkcs8KeySpec);
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM,
                new org.bouncycastle.jce.provider.BouncyCastleProvider());
            cipher.init(cipher.DECRYPT_MODE, privateK);
            int blockSize = cipher.getBlockSize();
            ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
            int j = 0;

            while (raw.length - j * blockSize > 0)
            {
                bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
                j++ ;
            }
            return bout.toByteArray();
        }
        catch (Exception e)
        {
            throw new Exception(ExceptionUtil.getStackTraceAsString(e));
        }
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
                logger.error("RSAUtils:[toStringHex][" + e.getMessage() + "]");
            }
        }
        return baKeyword;
    }

    /**
     * 作用于16进制字符串转byte
     * 
     * @param hexString
     * @return
     */
    public static byte[] hexStringToBytes(String hexString)
    {
        if (hexString == null || hexString.equals(""))
        {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++ )
        {
            int pos = i * 2;
            d[i] = (byte)(charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * Convert char to byte
     * 
     * @param c
     *            char
     * @return byte
     */
    private static byte charToByte(char c)
    {
        return (byte)"0123456789ABCDEF".indexOf(c);
    }

    /**
     * 公钥 系数
     * 
     * @param publicKey
     * @return
     */
    public static String getModulus(RSAPublicKey publicKey)
    {
        return publicKey.getModulus().toString(16);
    }

    /**
     * 公钥 指数
     * 
     * @param publicKey
     * @return
     */
    public static String getPublicExponent(RSAPublicKey publicKey)
    {
        return publicKey.getPublicExponent().toString(16);
    }

    /**
     * 转Rsakey 为字符串
     * 
     * @param key
     * @return
     * @throws Exception
     */
    public static String getKeyString(Key key)
        throws Exception
    {
        String keyString = Base64Utils.encode(key.getEncoded());
        return keyString;
    }

    public static RSAPublicKey getKeyByString(String publicKey)
    {
        byte[] keyBytes;
        try
        {
            keyBytes = Base64Utils.decode(publicKey);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            RSAPublicKey publicK = (RSAPublicKey)keyFactory.generatePublic(x509KeySpec);
            return publicK;
        }
        catch (Exception e)
        {
            logger.error("RSAUtils:[getKeyByString][" + e.getMessage() + "]");
        }
        return null;
    }
}