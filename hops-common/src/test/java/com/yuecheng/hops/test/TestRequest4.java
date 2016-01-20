package com.yuecheng.hops.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.yuecheng.hops.common.security.MD5Util;

public class TestRequest4 {
	static SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	public static void main(String[] args) throws Exception {
	    
//	    sid    跃程系统订单号 不可空 跃程系统生成的订单号
//	    ste 订单状态    不可空 订单状态只有两种，0为成功，1为失败
//	    cid 商户ID    不可空 商户在我们系统注册的ID
//	    pid 商品编号    不可空 商品编号
//	    oid 商户订单号   不可空 商户系统传递的订单ID
//	    pn  充值帐号    不可空 你所提交的充值帐号
//	    tf  提交充值金额  不可空 提交给用户充值的金额
//	    fm  充值金额    不可空 给用户充值成功金额
//	    dm  扣款金额    不可空 实际扣款折扣
//	    info1   自定义信息   可以空 自定义信息
//	    info2   自定义信息   可以空 自定义信息
//	    info3   自定义信息   可以空 自定义信息
//	    sign    MD5签名   不可空 值:
//	    MD5(sid+ste+cid+pid+oid+pn+tf+fm+key) 大写
//	    oid=15091614450514759&cid=cssy&pr=10&nb=1&fm=10&pn=15874945248&ru=http://58.20.54.132:28088/seud_api/yuechengNoticeUrl.htm&tsp=20150916144434&sign=29E32B85F4BCF3035E177E7A9AF9779B
		Long id=new Long("1");
		String tsp = formatter.format(new Date());
		String oid = "RO" + tsp;
		String cid = "kachuang";//"YC_AGENT";//down_merchant_foxgl
        int pr = 1;
        int nb = 1;
        int fm = pr * nb;
        String pn = "073188884543";
        String ru = "http://www.baidu.com";
        String privateKey="937179ca1e312e0e7c22d695e2a2d66c";
        String sign = oid + cid + pr + nb + fm + pn + ru + tsp + privateKey;
        System.out.println("sign:["+sign+"]");
        sign = MD5Util.getMD5Sign(sign);
        String url = "http://192.168.1.50:48455/recieverOrder?oid=" + oid + "&cid=" + cid + "&pr=" + pr + "&nb=" + nb + "&fm=" + fm + "&ru=" + ru + "&pn=" + pn + "&tsp=" + tsp + "&sign=" + sign;
        System.out.println("代理商下单URL："+url);
        
        sign = MD5Util.getMD5Sign("15091614595414769cssy1011015874945248http://58.20.54.132:28088/seud_api/yuechengNoticeUrl.htm20150916145924d80b0b4815b281a070dda8ef29fb9763");
        System.out.println("!!!!!!!!!!!!sign"+sign);
//        http://58.67.203.80:48455/recieverOrder?oid=0NQFPZX7EBV50NUZ4ZRQ&cid=bjwmfj&pr=10&nb=1&fm=10&pn=15110000435&ru=http%3A%2F%2Fpre.api.huafeiduo.com%2Forder%2Fphone%2Fcallback%2Fyuecheng.cgi&tsp=20150624135957&sign=9AB09FC9366A233AA94740DD5A1F3A1D
        
//        http://58.67.203.80:48455/queryAccount?cid=*****&tsp=20150318163143&sign=C861D11DCB91C5FE9D1DDCBF7732D692
        sign = cid + tsp + privateKey;
        sign = MD5Util.getMD5Sign(sign);
        url = "http://58.67.203.80:48455/queryAccount?cid="+cid+"&tsp="+tsp+"&sign="+sign;
        System.out.println("查询账户URL："+url);
        
        //http://117.79.131.122:8093/Tel/Request.ashx?cid=mofpt&oid=300000003273&pr=50&nb=1&fm=50&pn=15199399816&ct=0&ru=http://58.67.203.80:48455/n_of&tsp=20150408145937&key=33627719590C6269B74CCC1419D687CB&sign=B54F03ADE17E0E9D662FE80925888547
        
        //http://192.168.1.50:48455/notify? sid=RO1430982487573&ste=1&cid=MRSYSTEM&pid=HFYDHUN****00000100&oid=100000003285&pn=18273211353&tf=1&fm=1&dm=1&sign=123
        
        sign = "00http://221.174.24.162:8091/10086/notify.ashx88800997299002674596763MD5PhoneChargesOffline2.0.0100018233663390001PTC001";
        sign = MD5Util.getMD5Sign(sign);
        System.out.println("CMPAY sign:["+ sign +"]");
        
        
        //�Ա�ֱ��ӿڵ���
//        cardId=67J53JQJ6&cardNum=1&coopId=1652410867&customer=18351199268&gameId=1754&notifyUrl=http%3A%2F%2Fczapi.taobao.com%2Fecard%2Fecard_result.do&section1=1&sum=30.39&tbOrderNo=1374524004820237&tbOrderSnap=30.39%7C24%D0%A1%CA%B1%D7%D4%B6%AF%B3%E4%D6%B5+%BD%AD%CB%D5%D2%C6%B6%AF%BB%B0%B7%D1%B3%E4%D6%B530%D4%AA%D7%D4%B6%AF%BF%EC%B3%E4+%C3%EB%B3%E4+%BC%B4%CA%B1%B5%BD%D5%CB%7C%D6%D0%B9%FA%D2%C6%B6%AF&sign=7496d1e712b1eb88c08645f5b5eb5a5b
        tsp = formatter.format(new Date());
        String tbOrderNo = "TBRO"+tsp;
        String coopId = "1652410867";
        String cardId = "18351199268";
        String cardNum = "1";
        String customer = "1652410867";
        String sum = "100";
        String gameId = "1754";
        ru = "http%3A%2F%2Fczapi.taobao.com%2Fecard%2Fecard_result.do";
        String section1 = "";
        String section2 = "";
        String tbOrderSnap = "testkuaizhao";
       
//        String notifyUrl = "http://192.168.1.99:8089/t.html";
//        String privateKey = "dc929a54ed028d9743efc1d8594479a1";
        String version = "";
      //UrlDecode�������в���
      		String str_cardId = new String(cardId.getBytes(),"GBK");
      		String str_cardNum = new String(cardNum.getBytes(),"GBK");
      		String str_coopId = new String(cid.getBytes(),"GBK");
      		String str_customer = new String(customer.getBytes(),"GBK");
      		String str_gameId = new String(gameId.getBytes(),"GBK");
      		String str_notifyUrl = new String(ru.getBytes(),"GBK");
      		String str_section1 = new String(section1.getBytes(),"GBK");
      		String str_section2 = new String(section2.getBytes(),"GBK");
      		String str_sum = new String(sum.getBytes(),"GBK");
      		String str_tbOrderNo = new String(tbOrderNo.getBytes(),"GBK");
      		String str_tbOrderSnap = new String(tbOrderSnap.getBytes(),"GBK");

              StringBuilder sb_premd5 = new StringBuilder();
              sb_premd5.append("cardId" + str_cardId);
              sb_premd5.append("cardNum" + str_cardNum);
              sb_premd5.append("coopId" + str_coopId);
              sb_premd5.append("customer" + str_customer);
              if ((str_gameId != null) && (!str_gameId.isEmpty()))
              {
                  sb_premd5.append("gameId" + str_gameId);
              }
              sb_premd5.append("notifyUrl" + str_notifyUrl);
              if ((str_section1 != null) && (!str_section1.isEmpty()))
              {
                  sb_premd5.append("section1" + str_section1);
              }

              if ((str_section2 != null) && (!str_section2.isEmpty()))
              {
                  sb_premd5.append("section2" + str_section2);
              }

              sb_premd5.append("sum" + str_sum);
              sb_premd5.append("tbOrderNo" + str_tbOrderNo);
              sb_premd5.append("tbOrderSnap" + str_tbOrderSnap);
              sb_premd5.append(privateKey);
      		sign= sb_premd5.toString();
      	    System.out.println(sign);
        StringBuilder sb_premd51 = new StringBuilder();
        sb_premd51.append("cardId=" + cardId);
        sb_premd51.append("&cardNum=" + cardNum);
        sb_premd51.append("&coopId=" + cid);
        sb_premd51.append("&customer=" + customer);
        if (gameId!=null&&!gameId.isEmpty())
        {
        	sb_premd51.append("&gameId=" + gameId);
        }
        sb_premd51.append("&notifyUrl=" + ru);
        sb_premd51.append("&sign=" + MD5Util.getMD5Sign(sign));
        if (section1!=null&&!section1.isEmpty())
        {
        	sb_premd51.append("&section1=" + section1);
        }
        if (section2!=null&&!section2.isEmpty())
        {
        	sb_premd51.append("&section2=" + section2);
        }
        sb_premd51.append("&sum=" + sum);
        sb_premd51.append("&tbOrderNo=" + tbOrderNo);
        sb_premd51.append("&tbOrderSnap=" + tbOrderSnap);
        String tburl = "http://localhost:38457/?" + sb_premd51.toString();
        System.out.println(tburl);
        
        
        
        Long a=3l;
		Long b=3l;
		Long c=a+b;
		System.out.println(c);
		
		
		
		String md5_Str = "20300411000000000000";
		String md5 = MD5Util.getMD5Sign(md5_Str);
		System.out.println("------------原串:"+md5_Str+"-----------md5:"+md5+"------------------------------------------------------------");
		System.out.println("------------------------------------------------------------------------------------------");

		md5_Str = "20300511000000000";
		md5 = MD5Util.getMD5Sign(md5_Str);
		System.out.println("------------原串:"+md5_Str+"-----------md5:"+md5+"------------------------------------------------------------");
		System.out.println("------------------------------------------------------------------------------------------");
		
		
//		String coopId = "1652410867";
		tbOrderNo = "1152500187211105";
		sign = "coopId"+coopId + "tbOrderNo" + tbOrderNo + "35f1a24d9234sfb2cfe423dfsf4wex33";
		sign = MD5Util.getMD5Sign(sign);
		System.out.println("TB!!!!!!!!!!!!!!!!!["+sign+"]");
		//coopId=1652410867&tbOrderNo=1152500187211105&sign=1c8d8f0d8300e18a4a85a83fe56639ac
	}
}
