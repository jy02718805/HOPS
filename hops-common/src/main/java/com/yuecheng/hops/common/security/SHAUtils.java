package com.yuecheng.hops.common.security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SHAUtils
{
	private static final Logger logger = LoggerFactory.getLogger(SHAUtils.class);

	private static final String SHASTR = "HmacSHA1";

	/**
	 * 加密
	 * 
	 * @param strObj
	 *            待加密字符
	 * @param key
	 *            密钥
	 * @return 加密后的密文字符串
	 */
	public static String getHmacSHASign(String strObj, String key)
	{
		String resultString = null;
		try
		{
			resultString = new String(strObj);
			SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), SHASTR);
			Mac mac = Mac.getInstance("HmacSHA1");
			mac.init(signingKey);
			byte[] rawHmac = mac.doFinal(resultString.getBytes());
			resultString = Base64Utils.encode(rawHmac);
		}
		catch (NoSuchAlgorithmException ex)
		{
			logger.error("SHAUtils:[getHmacSHASign-NoSuchAlgorithmException][" + ex.getMessage() + "]");
		}
		catch (InvalidKeyException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return resultString;
	}
}
