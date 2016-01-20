//package com.yuecheng.hops.test;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import com.hisun.iposm.HiiposmUtil;
//import com.yuecheng.hops.common.security.MD5Util;
//import com.yuecheng.hops.common.utils.UUIDUtils;
//
//public class TransKeyChange {
//	static SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
//	public static void main(String[] args) throws Exception {
//	    String sign = "";
//	    String url = "";
//        System.out.println("---------------------------------------------------------CMPAY下单---------------------------------------------------------");
//        //00http://221.174.24.162:8091/10086/notify.ashx88800997299002674596580MD5PhoneChargesOffline2.0.0100018233663390001PTC001
//        String characterSet = "00";
//        String notifyUrl = "http://110.53.241.90:8086/hops-robot/cmpay/notify";
//        String merchantId = "888009972990026";
//        String requestId = "400000484414";
//        String signType = "MD5";
//        String type = "PhoneChargesOffline";
//        String version = "2.0.0";
//        String amount = "100";
//        String mobileNo = "15116351081";
//        String currency = "00";
//        String smsFlag = "1";
//        String smsCd = "PTC001";
//        String key = "ll0VfALHMtZKPYOsfj92MtFonjVxCgvvgMgwYhPmRjuZxNl4XArpQ3JGxXnS2PMQ";
//        sign = characterSet+notifyUrl+merchantId+requestId+signType+type+version+amount+mobileNo+currency+smsFlag+smsCd;
////        (characterSet)(notifyUrl)(merchantId)(requestId)(signType)(type)(version)
//        System.out.println(sign);
//        HiiposmUtil util = new HiiposmUtil();
//        sign = util.MD5Sign(sign, key);
//        
//        System.out.println("sign    "+sign);
//        url = "https://ipos.10086.cn/ips/phoneChargeService?characterSet="+characterSet + 
//            "&notifyUrl="+notifyUrl+"&merchantId="+merchantId+"&requestId="+requestId+"&signType="+signType
//            +"&type="+type+"&version="+version
//            +"&amount="+amount
//            +"&mobileNo="+mobileNo
//            +"&currency="+currency
//            +"&smsFlag="+smsFlag
//            +"&smsCd="+smsCd
//            +"&hmac="+sign;
//        System.out.println(url);
//        
//        System.out.println("---------------------------------------------------------CMPAY下单---------------------------------------------------------");
////      商户编号 merchantId Max(15) String 我方平台给商户分配的唯一标识 N
////     商户请求号 requestId Max(20) String 商户请求的交易流水号 N
////      签名方式 signType Max(3) String 只能是MD5 N
////      业务类型 type Max(20) String TransKeyChange N
////      版本号 version Max(10) String 2.0.0 N
////      密钥类型 signKeyType Max(1) String 0-线下密钥  1-线上密钥(暂不支持) N
////      变更密钥 signKey Max(64) String 商户自定义密钥，只能以数字、大小写字母组成，长度不超过64位，修改成功后新密钥在1分钟内生效 N
////      签名数据 hmac 以上请求参数生成的签名串,获得hmac的方法见签名算法,参数顺序按照表格中从上到下的顺序,但不包括本参数. N
//        System.out.println("---------------------------------------------------------CMPAY更新密钥---------------------------------------------------------");
//       
//        
//        requestId = UUIDUtils.uuid();
//        type = "TransKeyChange";
//        String signKeyType = "0";
//        String signKey_old = "ll0VfALHMtZKPYOsfj92MtFonjVxCgvvgMgwYhPmRjuZxNl4XArpQ3JGxXnS2PMQ";
//        String signKey_new = MD5Util.getMD5Sign("yuecheng")+MD5Util.getMD5Sign("20122111");
//        System.out.println(signKey_new);
//        String sign_orginal = merchantId + requestId + signType + type + version + signKeyType + signKey_new;
//        System.out.println(sign_orginal);
//        HiiposmUtil util2 = new HiiposmUtil();
//        sign = util2.MD5Sign(sign_orginal, signKey_old);
//        System.out.println("sign    "+sign);
//        url = "https://ipos.10086.cn/ips/phoneChargeService?merchantId="+merchantId
//            +"&requestId="+requestId
//            +"&signType="+signType
//            +"&type="+type
//            +"&version="+version
//            +"&signKeyType="+signKeyType
//            +"&signKey="+signKey_new
//            +"&hmac="+sign;
//        System.out.println(url);
//        System.out.println("---------------------------------------------------------CMPAY更新密钥---------------------------------------------------------");
//	}
//}
