package com.yuecheng.hops.gateway.security.authentication;


import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.crypto.AesCipherService;

import com.bonc.wo_key.WoMd5;
import com.google.gson.Gson;
import com.hisun.iposm.HiiposmUtil;
import com.huateng.util.MacUtil;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ApplicationException;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.exception.HopsException;
import com.yuecheng.hops.common.security.AesUtils;
import com.yuecheng.hops.common.security.Digest;
import com.yuecheng.hops.common.security.MD5Util;
import com.yuecheng.hops.common.security.RSAUtils;
import com.yuecheng.hops.common.security.SHAUtils;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.common.utils.StringUtil;
import com.yuecheng.hops.injection.entity.InterfaceParam;


public class VerificationComponent
{
    // 第三方 sign map.get("sign")
    // 本系统 sign interfaceParam.get
    public static void verification(List<InterfaceParam> interfaceParams,
                                    Map<String, Object> fields, String packets, String encoding)
        throws Exception
    {
        try
        {
            for (InterfaceParam interfaceParam : interfaceParams)
            {
                if (BeanUtils.isNotNull(interfaceParam.getEncryptionFunction())
                    && !interfaceParam.getEncryptionFunction().equalsIgnoreCase(
                        Constant.SecurityCredential.NO_NEED))
                {
                    String ycSign = getSign(fields, interfaceParam.getEncryptionParamNames(),
                        interfaceParam.getEncryptionFunction(), packets, encoding);
                    String outSideSign = fields.get(interfaceParam.getOutParamName()).toString();
                    // 验签
                    if (!ycSign.equalsIgnoreCase(outSideSign))
                    {
                        // 签名错误
                        String[] msgParams = new String[] {fields.toString()};
                        String[] viewParams = new String[] {};
                        ApplicationException ae = new ApplicationException("getway000006",
                            msgParams, viewParams);
                        throw ExceptionUtil.throwException(ae);
                    }
                }
            }
        }
        catch (Exception e)
        {
            throw new HopsException(Constant.ErrorCode.SIGN_ERROR, new String[] {},
                new String[] {}, e);
        }
    }

    public static Map<String, Object> encoderSign(List<InterfaceParam> ips,
                                                  Map<String, Object> fields, String encoding)
        throws Exception
    {
        try
        {
            if (BeanUtils.isNotNull(ips))
            {
                for (InterfaceParam interfaceParam : ips)
                {
                    if (BeanUtils.isNotNull(interfaceParam.getEncryptionFunction())
                        && !Constant.SecurityCredential.NO_NEED.equalsIgnoreCase(interfaceParam.getEncryptionFunction()))
                    {
                        String ycSign = getSign(fields, interfaceParam.getEncryptionParamNames(),
                            interfaceParam.getEncryptionFunction(), null, encoding);
                        fields.put(interfaceParam.getOutParamName(), ycSign);
                    }
                }
            }
            return fields;
        }
        catch (Exception e)
        {
            throw new Exception(Constant.ErrorCode.SIGN_ERROR);
        }
    }

    /**
     * 调用配置加密方法进行加密
     * 
     * @param map
     * @param encryptionParamName
     * @param encryptionFunction
     * @return
     * @throws Exception
     */
    public static String getSign(Map<String, Object> map, String encryptionParamName,
                                 String encryptionFunction, String packets, String encoding)
        throws Exception
    {
        String signStr = getSignStr(map, encryptionParamName, packets);
        
        if (null != encryptionFunction && !encryptionFunction.isEmpty())
        {
            String sign = StringUtil.initString();
            if (encryptionFunction.equalsIgnoreCase(Constant.SecurityCredential.MD5))
            {
                sign = MD5Util.getMD5Sign(signStr, encoding);
            }
            else if (encryptionFunction.equalsIgnoreCase(Constant.SecurityCredential.RSA))
            {
                String publicKey = StringUtil.initString();
                sign = new String(RSAUtils.encryptByPublicKey(signStr.getBytes(), publicKey));
            }
            else if (encryptionFunction.equalsIgnoreCase(Constant.SecurityCredential.SHA))
            {
                sign = SHAUtils.getHmacSHASign(signStr, String.valueOf(map.get("secretOAuthKey")));
                sign = URLEncoder.encode(sign);
            }
            else if (encryptionFunction.equalsIgnoreCase(Constant.SecurityCredential.JFMD5))
            {
                String key = (String)map.get(Constant.Common.KEY);
                sign = Digest.hmacSign(signStr, key);
            }
            else if (encryptionFunction.equalsIgnoreCase(Constant.SecurityCredential.MD5_CMPAY))
            {
                HiiposmUtil util = new HiiposmUtil();
                // 如果是中文，cmpay要求先转码再加密
                if (signStr.length() != signStr.getBytes().length)
                {
                    signStr = URLEncoder.encode(signStr, encoding);
                }
                String key = (String)map.get(Constant.Common.KEY);
                sign = util.MD5Sign(signStr, key);
            }
            else if (encryptionFunction.equalsIgnoreCase(Constant.SecurityCredential.MD5_YS))
            {
                sign = MD5Util.getMD5SignBeforeEncoding(signStr, Constant.EncodType.GB2312);
                return sign.toUpperCase().substring(0, 16);

            }
            else if (encryptionFunction.equalsIgnoreCase("MAC"))
            {
                String key = (String)map.get(Constant.Common.KEY);
                sign = MacUtil.genMac(signStr, key);

            }
            else if (encryptionFunction.equalsIgnoreCase(Constant.SecurityCredential.MD5_HX))
            {
                WoMd5 wMd5 = new WoMd5();
                // 如果是中文， HX要求先转码再加密
                if (signStr.length() != signStr.getBytes().length)
                {
                    signStr = URLEncoder.encode(signStr, encoding);
                }
                sign = wMd5.encode(signStr);
            }
            else if (encryptionFunction.equalsIgnoreCase(Constant.SecurityCredential.AES))
            {
                // 如果是中文， HX要求先转码再加密
                if (signStr.length() != signStr.getBytes().length)
                {
                    signStr = URLEncoder.encode(signStr, encoding);
                }
                String key = (String)map.get(Constant.Common.KEY);
                String vi = (String)map.get(Constant.Common.AES_IV);
                sign = AesUtils.encrypt(signStr, key, vi);
            }
            else
            {
                sign = null;
            }
            return sign;
        }
        return null;
    }

    /**
     * 解析加密元字符串
     * 
     * @param map
     * @param encryptionParamName
     * @return
     * @throws Exception
     */
    public static String getSignStr(Map<String, Object> map, String encryptionParamName,
                                    String packets)
        throws Exception
    {
        if (encryptionParamName != null && !encryptionParamName.isEmpty())
        {
            StringBuffer sb = new StringBuffer();
            String str = encryptionParamName;
            if (Constant.SignRule.RULE_ONE.equals(str) && packets != null)
            {
                int beginIndex = packets.indexOf("<head>");

                int endIndex = packets.indexOf("</body>") + 7;

                if (beginIndex >= 0 && endIndex >= 0 && endIndex > beginIndex)
                {
                    sb.append(packets.substring(beginIndex, endIndex)).append(map.get("key"));
                }

            }
            if (str.contains(Constant.SignRule.RULE_JOSN))
            {
                Map<String, String> signMap = new HashMap<String, String>();
                while (true)
                {
                    int length = str.length();
                    int beginIndex = str.indexOf("(");
                    int endIndex = str.indexOf(")");
                    if (beginIndex >= 0 && endIndex >= 0 && endIndex > beginIndex)
                    {
                        String key = str.substring(beginIndex + 1, endIndex);
                        Object value = map.get(key);
                        signMap.put(key,
                            value != null ? value.toString() : StringUtil.initString());
                        str = str.substring(endIndex + 1, length);
                    }
                    else
                    {
                        break;
                    }
                }
                Gson gson = new Gson();
                String signJson = gson.toJson(signMap);
                return signJson;
            }
            else
            {
                boolean flag = true;
                while (flag)
                {
                    int length = str.length();
                    int beginIndex = str.indexOf("(");
                    int endIndex = str.indexOf(")");
                    if (beginIndex >= 0 && endIndex >= 0 && endIndex > beginIndex)
                    {

                        String key = str.substring(beginIndex + 1, endIndex);
                        Object value = map.get(key);
                      
                        if (str.substring(0, beginIndex).contains("<")
                            && !str.substring(0, beginIndex).contains(">"))
                        {
                            if (BeanUtils.isNotNull(value))
                            {
                                sb.append(str.substring(0, beginIndex).replaceAll("<", ""));
                                sb.append(value != null ? value.toString() : StringUtil.initString());
                            }
                        }
                        else
                        {
							// 可选项配置，传了参与签名，没传不参与
							if (!StringUtil.isNullOrEmpty(key) && key.indexOf('?') == 0)
							{
								value = map.get(key.substring(1));
								if (null != value && !StringUtil.isNullOrEmpty(value.toString()))
								{
									sb.append(str.substring(0, beginIndex));
									sb.append(value.toString());
								}
							}
							else
							{
								sb.append(str.substring(0, beginIndex));
								sb.append(value != null ? value.toString() : StringUtil.initString());
							}
                        }

                        str = str.substring(endIndex + 1, length);
                        if (!str.contains("(") && !str.contains(")"))
                        {
                            if (str.contains("<") && str.contains(">"))
                            {
                                sb.append(str);
                            }
                            flag = false;
                        }
                    }
                    else
                    {
                        flag = false;
                    }
                }
            }
            return sb.toString();
        }
        return null;
    }
}
