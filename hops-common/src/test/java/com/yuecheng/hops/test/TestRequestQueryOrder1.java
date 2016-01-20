package com.yuecheng.hops.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.hisun.iposm.HiiposmUtil;
import com.yuecheng.hops.common.security.MD5Util;
import com.yuecheng.hops.common.utils.UUIDUtils;

public class TestRequestQueryOrder1 {
	static SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	public static void main(String[] args) throws Exception {
//		oid	商户订单ID	不可空	商户自己生成的平台ID
//		cid	商户ID	不可空	商户ID
//		tsp	时间戳	不可空	请求时间戳，格式yyyyMMddHHmmss
//		sign	签名	不可空	值:MD5 (oid+cid+tsp+key) 大写
//                                           oid=123456789&cid=kachuang&tsp=20150317163356&sign=be4252932bd29fdbe72e6d4715fe90a8
//	    http://58.67.203.80:48455/queryOrder?oid=123456789&cid=kachuang&tsp=20150317163356&sign=be4252932bd29fdbe72e6d4715fe90a8
		String oid = "0NQW0D1DZRMYARXRADLW";
		String cid = "kachuang";
		String tsp = formatter.format(new Date());
		String privateKey="937179ca1e312e0e7c22d695e2a2d66c";
		String sign = oid + cid + tsp + privateKey;
		System.out.println("sign:["+sign+"]");
        sign = MD5Util.getMD5Sign(sign);
        String url = "http://58.67.203.80:48455/queryOrder?oid=" + oid + "&cid=" + cid + "&tsp=" + tsp + "&sign=" + sign;
        System.out.println("queryOrder:"+url);
        
        sign = cid + tsp + privateKey;
        sign = MD5Util.getMD5Sign(sign);
        System.out.println("sign:["+sign+"]");
        //sign yctest20150317163356f8d14b8e10f3b93e37e67099efe585af
        //     yctest20150317163356f8d14b8e10f3b93e37e67099efe585af
        url = "http://58.67.203.80:48455/queryAccount?cid="+cid+"&tsp=" + tsp + "&sign=" + sign;
        System.out.println("queryAccount:"+url);
        
//        coopId=kachuang&tbOrderNo=fake20150409115739426855154&sign=xxx
        sign = cid+oid+privateKey;
        sign = MD5Util.getMD5Sign(sign);
        url = "http://192.168.1.50:48455/queryTBorder?coopId="+cid+"&tbOrderNo="+oid+"&sign="+sign;
        System.out.println(url);
        

//        https://ipos.10086.cn/ips/phoneChargeService 
//        characterSet=00
//notifyUrl=http://110.53.241.90:8086/hops-robot/cmpay/notify
//merchantId=888009972990026
//requestId=400000484414
//signType=MD5
//type=PhoneChargesOffline
//version=2.0.0
//amount=100
//mobileNo=15116351081
//currency=00
//smsFlag=1
//smsCd=PTC001
//hmac=2ca67e170cb62cadeed599446f35438a]
        
        
        System.out.println("---------------------------------------------------------CMPAY---------------------------------------------------------");
        //00http://221.174.24.162:8091/10086/notify.ashx88800997299002674596580MD5PhoneChargesOffline2.0.0100018233663390001PTC001
//        https://ipos.10086.cn/ips/phoneChargeService?characterSet=00&
//        notifyUrl=http://58.67.203.80:48455/n_cmpay&
//            merchantId=888009972990026&
//        requestId=300002088164&
//        signType=MD5&
//        type=PhoneChargesOffline&
//        version=2.0.0&
//        amount=1000&
//        mobileNo=18715133405&
//        currency=00&
//        smsFlag=1&
//        smsCd=PTC001&
//        hmac=02b0ab2a90d7785feb2f4fa272ad4fe9
        
            
        String characterSet = "00";
        String notifyUrl = "http://58.67.203.80:48455/n_cmpay";
        String merchantId = "888009972990026";
        String requestId = UUIDUtils.uuid();
        String signType = "MD5";
        String type = "PhoneChargesOffline";
        String version = "2.0.0";
        String amount = "100";
        String mobileNo = "15116351081";
        String currency = "00";
        String smsFlag = "1";
        String smsCd = "PTC001";
        String key = "3769191d6f5ab6df125d525e42d62562";
        sign = characterSet+notifyUrl+merchantId+requestId+signType+type+version+amount+mobileNo+currency+smsFlag+smsCd;
//        (characterSet)(notifyUrl)(merchantId)(requestId)(signType)(type)(version)
        System.out.println(sign);
        HiiposmUtil util = new HiiposmUtil();
        sign = util.MD5Sign(sign, key);
        
        System.out.println("sign    "+sign);
        url = "https://ipos.10086.cn/ips/phoneChargeService?characterSet="+characterSet + 
            "&notifyUrl="+notifyUrl+"&merchantId="+merchantId+"&requestId="+requestId+"&signType="+signType
            +"&type="+type+"&version="+version
            +"&amount="+amount
            +"&mobileNo="+mobileNo
            +"&currency="+currency
            +"&smsFlag="+smsFlag
            +"&smsCd="+smsCd
            +"&hmac="+sign;
        System.out.println(url);
        
        System.out.println("-----------------------------------------CMPAY查询订单----------------------------------------------------");
        String queryRequestId = "300057095528";
        type = "PhoneChargesQuery";
        sign = merchantId + requestId + signType + type + version + queryRequestId;
        sign = util.MD5Sign(sign, key);
        
        System.out.println("sign    "+sign);
        url = "https://ipos.10086.cn/ips/phoneChargeService?" + 
            "merchantId="+merchantId
            +"&requestId="+requestId
            +"&signType="+signType
            +"&type="+type
            +"&version="+version
            +"&queryRequestId="+queryRequestId
            +"&hmac="+sign;
        System.out.println("CMPAY查询订单["+url+"]");
//        https://ipos.10086.cn/ips/phoneChargeService?
//            merchantId=888009972990026&
//            requestId=1390838510&
//            signType=MD5&
//            type=PhoneChargesQuery&
//            version=2.0.0&
//            queryRequestId=73910711&
//            hmac=0f37b1a7077d4acb157737e01d09b95a
        
	}
}
