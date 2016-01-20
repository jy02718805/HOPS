/*
 * 文件名：AesUtils.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator 修改时间：2015年10月22日 跟踪单号：
 * 修改单号： 修改内容：
 */

package com.yuecheng.hops.common.security;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;


public class AesUtils
{

    // =E2RKLiLSOsc4kkSN，iv=1292939640503927

    public static String encrypt(String input, String key, String vi)
        throws Exception
    {
        try
        {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(), "AES"),
                new IvParameterSpec(vi.getBytes()));
            byte[] encrypted = cipher.doFinal(input.getBytes("utf-8"));
            // 此处使用 此处使用 此处使用 BASE64做转码。 做转码。
            return DatatypeConverter.printBase64Binary(encrypted);
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    public static String decrypt(String input, String key, String vi)
        throws Exception
    {
        try
        {
            byte[] inputByte = DatatypeConverter.parseBase64Binary(input);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(), "AES"),
                new IvParameterSpec(vi.getBytes()));
            byte[] encrypted = cipher.doFinal(inputByte);

            // 此处使用 此处使用 此处使用 BASE64做转码。 做转码。
            return new String(encrypted);
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    public static void main(String[] args)
    {
        // TODO Auto-generated method stub
        String input = "{\"plat_offer_id\": \"LXC00000000T\", \"phone_id\": \"18905172668\", \"facevalue\": \"0\", \"order_id\":"
                       + " \"Q20141117143738\", \"request_no\": \"Q20141117143738\", \"contract_id\": \"100001\"}";
        String aesString1 = "{\"plat_offer_id\":  \"LXC00000000T\",  \"phone_id\":  \"18905172668\",  \"facevalue\":  \"0\",  \"order_id\": \"Q20141117143738\", \"request_no\": \"Q20141117143738\", \"contract_id\": \"100001\"} ";

        // String string =
        // "{'plat_offer_id':  'LXC00000000T',  'phone_id':  '18905172668',  'facevalue':  '0',  'order_id': 'Q20141117143738', 'request_no': 'Q20141117143738', 'contract_id': '100001'} ";
        String aesString = "{'plat_offer_id':  'LXC00000000T',  'phone_id':  '18905172668',  'facevalue':  '0',  'order_id': 'Q20141117143738', 'request_no': 'Q20141117143738', 'contract_id': '100001'} ";
        try
        {
            aesString = encrypt(input, "E2RKLiLSOsc4kkSN", "1292939640503927");
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(aesString);

        String string = "";
        try
        {
            string = decrypt(string, "SW3HNNvX3uBflFJF", "2681765037747052");
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(string);

        System.out.println(aesString);
    }
}
