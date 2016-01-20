/**
 * @Title: TestKeyToolNew.java
 * @Package com.link.encode.RSA
 * @Description: 通过java代码操作keystore文件 Copyright: Copyright (c) 2011 Company:湖南跃程网络科技有限公司
 * @author 肖进
 * @date 2014年9月15日 上午9:08:18
 * @version V1.0
 */

package com.yuecheng.hops.common.utils;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Encoder;

import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.security.CertFile;


/**
 * @ClassName: TestKeyToolNew
 * @Description: 通过java代码操作keystore文件
 * @author 肖进
 * @date 2014年9月15日 上午9:08:18
 */

public class KeyToolUtils implements Serializable
{
	private static final Logger logger = LoggerFactory.getLogger(KeyToolUtils.class);
    private static final long serialVersionUID = 5787962649735142029L;

    public static void execCommand(String[] arstringCommand)
    {
        for (int i = 0; i < arstringCommand.length; i++ )
        {
        	logger.info("KeyToolUtils:[execCommand][No"+i+":"+arstringCommand[i]+"]");
        }
        try
        {
            Runtime.getRuntime().exec(arstringCommand);

        }
        catch (Exception e)
        {
        	logger.error("KeyToolUtils:[execCommand]["+ExceptionUtil.getStackTraceAsString(e)+"]");
        }
    }

    /**
     * 得到密钥字符串（经过base64编码）
     * 
     * @return
     */
    public static String getKeyString(Key key)
        throws Exception
    {
        byte[] keyBytes = key.getEncoded();
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String s = base64Encoder.encode(keyBytes);
        return s;
    }

    /**
     * 生成密钥对
     */
    public static void genkey()
    {
        String[] arstringCommand = new String[] {

        "cmd ", "/k", "start", // cmd Shell命令

            "keytool", "-genkey", // -genkey表示生成密钥
            "-validity", // -validity指定证书有效期(单位：天)，这里是36000天
            "36500", "-keysize",// 指定密钥长度
            "1024", "-alias", // -alias指定别名，这里是ss2
            "ss2", "-keyalg", // -keyalg 指定密钥的算法 (如 RSA DSA（如果不指定默认采用DSA）)
            "RSA", "-keystore", // -keystore指定存储位置，这里是d:/demo.keystore
            "d:/demonew3", "-dname",// CN=(名字与姓氏), OU=(组织单位名称), O=(组织名称), L=(城市或区域名称),
                                    // ST=(州或省份名称), C=(单位的两字母国家代码)"
            "CN=(SS), OU=(SS), O=(SS), L=(BJ), ST=(BJ), C=(CN)", "-storepass", // 指定密钥库的密码(获取keystore信息所需的密码)
            "123456", "-keypass",// 指定别名条目的密码(私钥的密码)
            "654321", "-v"// -v 显示密钥库中的证书详细信息
        };
        execCommand(arstringCommand);
    }

    /**
     * 通过文件流建立生成密钥对
     */

    /**
     * 导出证书文件
     */
    public static void export()
    {

        String[] arstringCommand = new String[] {

        "cmd ", "/k", "start", // cmd Shell命令

            "keytool", "-export", // - export指定为导出操作
            "-keystore", // -keystore指定keystore文件，这里是d:/demo.keystore
            "d:/demonew1.keystore", "-alias", // -alias指定别名，这里是ss2
            "ss2", "-file",// -file指向导出路径
            "d:/demonew1.cer", "-storepass",// 指定密钥库的密码
            "123456"

        };
        execCommand(arstringCommand);

    }

    /**
     * 将数字证书导入到keystore文件
     *
     * @Title: setImportCer
     * @Description: 将数字证书导入到keystore文件
     * @param @param keystorefile keystore文件路径
     * @param @param cerfile 证书路径
     * @param @param keyspasswd keystore文件登入密码
     * @param @param alias 别名
     * @return void 返回类型
     * @throws
     */
    public static void setImportCer(String keystorefile, String cerfile, String keyspasswd,
                                    String alias)
    {
        try
        {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");

            FileInputStream cerin = new FileInputStream(cerfile);

            Certificate cerc = cf.generateCertificate(cerin);

            cerin.close();

        	logger.info("KeyToolUtils:[setImportCer]["+cerfile+"证书："+cerc.toString()+"]");

            FileInputStream keysin = new FileInputStream(keystorefile);
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(keysin, keyspasswd.toCharArray());

            ks.setCertificateEntry(alias, cerc);

            FileOutputStream fos = new FileOutputStream(keystorefile);

            ks.store(fos, keyspasswd.toCharArray());

            keysin.close();
            fos.close();

        }
        catch (Exception e)
        {
        	logger.error("KeyToolUtils:[setImportCer]["+e.getMessage()+"]");
        }
    }

    /**
     * 将数字证书流导入到keystore文件
     *
     * @Title: setImportFlowCer
     * @Description: 将数字证书流导入到keystore文件
     * @param @param keystorefile keystore文件路径
     * @param @param cerfin 证书文件流
     * @param @param keyspasswd keystore文件登入密码
     * @param @param alias 别名
     * @return void 返回类型
     * @throws
     */
    public static void setImportFlowCer(String keystorefile, FileInputStream cerfin,
                                        String keyspasswd, String alias)
    {
        try
        {
            // 导入证书流
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            Certificate cerc = cf.generateCertificate(cerfin);

        	logger.info("KeyToolUtils:[setImportFlowCer][证书："+cerc.toString()+"]");
            // 得到keystore文件
            FileInputStream keysin = new FileInputStream(keystorefile);
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(keysin, keyspasswd.toCharArray());
            // 将导入证书set到KeyStore对象
            ks.setCertificateEntry(alias, cerc);
            // 写出流
            FileOutputStream fos = new FileOutputStream(keystorefile);

            ks.store(fos, keyspasswd.toCharArray());

            keysin.close();
            fos.close();

        }
        catch (Exception e)
        {
        	logger.error("KeyToolUtils:[setImportFlowCer]["+e.getMessage()+"]");
        }
    }

    /**
     * 显示证书指定信息（全名/公钥/签名等）
     *
     * @Title: getCerAllInfo
     * @Description: 显示证书指定信息（全名/公钥/签名等）
     * @param @param cerfin 证书文件流
     * @return void 返回类型
     * @throws
     */
    public static void getCerAllInfo(FileInputStream cerfin)
    {

        try
        {
            // 导入证书流
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            Certificate cerc = cf.generateCertificate(cerfin);

            X509Certificate t = (X509Certificate)cerc;
        	logger.info("KeyToolUtils:[getCerAllInfo][版本号 ："+t.getVersion()+"]");
        	logger.info("KeyToolUtils:[getCerAllInfo][序列号："+t.getSerialNumber().toString(16)+"]");
        	logger.info("KeyToolUtils:[getCerAllInfo][全名  ："+t.getSubjectDN()+"]");
        	logger.info("KeyToolUtils:[getCerAllInfo][签发者全名 ："+t.getIssuerDN()+"]");
        	logger.info("KeyToolUtils:[getCerAllInfo][有效期起始日 ："+t.getNotBefore()+"]");
        	logger.info("KeyToolUtils:[getCerAllInfo][有效期截至日 ："+t.getNotAfter()+"]");
        	logger.info("KeyToolUtils:[getCerAllInfo][签名算法 ："+t.getSigAlgName()+"]");
            byte[] sig = t.getSignature();
        	logger.info("KeyToolUtils:[getCerAllInfo][签名："+new BigInteger(sig).toString(16)+"]");
            PublicKey pk = t.getPublicKey();
            byte[] pkenc = pk.getEncoded();
            logger.info("KeyToolUtils:[getCerAllInfo][公钥]");
            for (int i = 0; i < pkenc.length; i++ )
            {
            logger.info(pkenc[i]+",");
            }
        }
        catch (CertificateException e)
        {
        	logger.error("KeyToolUtils:[getCerAllInfo-CertificateException]["+e.getMessage()+"]");
        }
    }

    /**
     * 显示keystore文件中的全部信息
     *
     * @Title: getKeystoreAllInfo
     * @Description: TODO
     * @param @param keystorefile keystore文件路径
     * @param @param keyspasswd keystore文件登入密码
     * @return void 返回类型
     * @throws
     */
    public static List<CertFile> getKeystoreAllInfo(FileInputStream in, String keyspasswd)
    {
        try
        {
            List<CertFile> certFiles = new ArrayList<CertFile>();
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(in, keyspasswd.toCharArray());
            Enumeration<String> e = ks.aliases();

            while (e.hasMoreElements())
            {
                CertFile cf = new CertFile();
                Object obj = e.nextElement();
                String alias = obj.toString();
                Certificate c = ks.getCertificate(alias);
                X509Certificate t = (X509Certificate)c;
                byte[] sig = t.getSignature();
                cf.setAlias(alias);
                cf.setVersion(t.getVersion());
                cf.setSerialNumber(t.getSerialNumber().toString(16));
                cf.setNotBefore(t.getNotBefore());
                cf.setNotAfter(t.getNotAfter());
                cf.setSigAlgName(t.getSigAlgName());
                certFiles.add(cf);
            }
            return certFiles;
        }
        catch (Exception e)
        {
        	logger.error("KeyToolUtils:[getKeystoreAllInfo]["+e.getMessage()+"]");
            return null;
        }
    }

    /**
     * 通过别名删除
     *
     * @Title: deleteCer
     * @Description: TODO
     * @param @param keystorefile keystore文件路径
     * @param @param keyspasswd keystore文件登入密码
     * @param @param alias 别名
     * @return void 返回类型
     * @throws
     */
    public static void deleteCer(String keystorefile, String keyspasswd, String alias)
    {

        try
        {
            FileInputStream in = new FileInputStream(keystorefile);
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(in, keyspasswd.toCharArray());

            // 检验条目是否在密钥库中，存在返回true
            if (ks.containsAlias(alias))
            {
                // 删除别名对应的条目
                ks.deleteEntry(alias);
                FileOutputStream output = new FileOutputStream(keystorefile);
                // 将keystore对象内容写入文件,条目删除成功
                ks.store(output, keyspasswd.toCharArray());
            	logger.info("KeyToolUtils:[deleteCer]["+alias+"条目删除成功  ~~~！]");
            }
            else
            {
            	logger.info("KeyToolUtils:[deleteCer][没有找到："+alias+"条目  ~~~！]");
            }
        }
        catch (Exception e)
        {
        	logger.error("KeyToolUtils:[deleteCer]["+e.getMessage()+"]");
        }
    }
}