//package com.yuecheng.hops.test;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import com.yuecheng.hops.common.security.MD5Util;
//
//public class TestRequest8 {
//	static SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
//	public static void main(String[] args) throws Exception {
//	    
////	    sid    跃程系统订单号 不可空 跃程系统生成的订单号
////	    ste 订单状态    不可空 订单状态只有两种，0为成功，1为失败
////	    cid 商户ID    不可空 商户在我们系统注册的ID
////	    pid 商品编号    不可空 商品编号
////	    oid 商户订单号   不可空 商户系统传递的订单ID
////	    pn  充值帐号    不可空 你所提交的充值帐号
////	    tf  提交充值金额  不可空 提交给用户充值的金额
////	    fm  充值金额    不可空 给用户充值成功金额
////	    dm  扣款金额    不可空 实际扣款折扣
////	    info1   自定义信息   可以空 自定义信息
////	    info2   自定义信息   可以空 自定义信息
////	    info3   自定义信息   可以空 自定义信息
////	    sign    MD5签名   不可空 值:
////	    MD5(sid+ste+cid+pid+oid+pn+tf+fm+key) 大写
//	    
////	    MD5(sid+ste+cid+pid+oid+pn+tf+fm+key) 
////	    //http://192.168.2.233:38455/notify?sid=100000025282&ste=1&cid=mofpt&pid=HFYDHEN****00001000&oid=300000026342&pn=13905624100&tf=10&fm=10&dm=0.9872&sign=471105005f6964bf86b480e361b1372b
////	    1000000307991yiqiaoHFYDSH_****0000100030000003074115221229604101080L604264VH8626V006X268TH20T88H0
//	    
//	    
////		/recieverOrder?oid=226151363&cid=cmpay&pr=20&nb=1&fm=20&pn=18248974645&ru=http%3a%2f%2f117.79.131.122%3a8092%2forder%2fNotify.ashx&tsp=20150712011659&sign=34DE0A43DA63585168B2E68D7E0F028D
//	        
//	    Long id=new Long("1");
//		String tsp = "20150712011659";//formatter.format(new Date());
//		String oid = "226151363";//"RO" + tsp;
//		String cid = "cmpay";//down_merchant_foxgl
//        int pr = 20;
//        int nb = 1;
//        int fm = pr * nb;
//        String pn = "18248974645";
//        String ru = "http%3a%2f%2f117.79.131.122%3a8092%2forder%2fNotify.ashx";
//        String privateKey="befab59b2063f568f4b9c65e84c56a8e";
//        String sign = oid + cid + pr + nb + fm + pn + ru + tsp + privateKey;
//        System.out.println("sign:["+sign+"]  34de0a43da63585168b2e68d7e0f028d   34DE0A43DA63585168B2E68D7E0F028D");
//        System.out.println("34de0a43da63585168b2e68d7e0f028d".equals("34DE0A43DA63585168B2E68D7E0F028D"));
//        sign = MD5Util.getMD5Sign(sign);
//        String url = "http://localhost:38455/recieverOrder?oid=" + oid + "&cid=" + cid + "&pr=" + pr + "&nb=" + nb + "&fm=" + fm + "&ru=" + ru + "&pn=" + pn + "&tsp=" + tsp + "&sign=" + sign;
//        System.out.println("代理商下单URL："+url);
//
////        userid=10002083&
////        productid=&
////        price=100&
////        num=1&
////        mobile=13799271633&
////        spordertime=2015-07-06 00:00:49&
////        sporderid=224887642&
////        sign=351d55753e7bf4c470664e37f2856ce6&
////             351d55753e7bf4c470664e37f2856ce6
////        back_url=http://117.79.131.122:8092/qiezi/Notify.ashx&paytype=YD
//        String userid = "10002083";
//        String productid = "";
//        String price = "1";
//        String num = "1";
//        String mobile = "15116351081";
//        String spordertime = "2015-07-07 16:54:10";
//        String sporderid = "400000019114";
//        String back_url = "http://117.79.131.122:8092/qiezi/Notify.ashx&paytype=YD";
//        String key = "vko10fJHc543JKclwojc18e";
//        sign = "userid="+userid+"&productid="+productid+"&price="+price+"&num="+num+"&mobile="+mobile+"&spordertime="+spordertime+"&sporderid="+sporderid+"&key="+key;
//        sign = MD5Util.getMD5Sign(sign);
//        System.out.println("易林sign："+sign);
//        //ea954d49302bce2fd842889d30a33b53
////        ea954d49302bce2fd842889d30a33b53
//        //�Ա�ֱ��ӿڵ���
//        tsp = formatter.format(new Date());
//        String tbOrderNo = "TBRO"+tsp;
////        String coopId = "1101";
//        String cardId = "67JSAKN9W";
//        String cardNum = "1";
//        String customer = "15874009948";
//        String sum = "100";
//        String gameId = "";
//        String section1 = "";
//        String section2 = "";
//        String tbOrderSnap = "testkuaizhao";
////        String notifyUrl = "http://192.168.1.99:8089/t.html";
////        String privateKey = "dc929a54ed028d9743efc1d8594479a1";
//        String version = "";
//      //UrlDecode�������в���
//      		String str_cardId = new String(cardId.getBytes(),"GBK");
//      		String str_cardNum = new String(cardNum.getBytes(),"GBK");
//      		String str_coopId = new String(cid.getBytes(),"GBK");
//      		String str_customer = new String(customer.getBytes(),"GBK");
//      		String str_gameId = new String(gameId.getBytes(),"GBK");
//      		String str_notifyUrl = new String(ru.getBytes(),"GBK");
//      		String str_section1 = new String(section1.getBytes(),"GBK");
//      		String str_section2 = new String(section2.getBytes(),"GBK");
//      		String str_sum = new String(sum.getBytes(),"GBK");
//      		String str_tbOrderNo = new String(tbOrderNo.getBytes(),"GBK");
//      		String str_tbOrderSnap = new String(tbOrderSnap.getBytes(),"GBK");
//
//              StringBuilder sb_premd5 = new StringBuilder();
//              sb_premd5.append("cardId" + str_cardId);
//              sb_premd5.append("cardNum" + str_cardNum);
//              sb_premd5.append("coopId" + str_coopId);
//              sb_premd5.append("customer" + str_customer);
//              if ((str_gameId != null) && (!str_gameId.isEmpty()))
//              {
//                  sb_premd5.append("gameId" + str_gameId);
//              }
//              sb_premd5.append("notifyUrl" + str_notifyUrl);
//              if ((str_section1 != null) && (!str_section1.isEmpty()))
//              {
//                  sb_premd5.append("section1" + str_section1);
//              }
//
//              if ((str_section2 != null) && (!str_section2.isEmpty()))
//              {
//                  sb_premd5.append("section2" + str_section2);
//              }
//
//              sb_premd5.append("sum" + str_sum);
//              sb_premd5.append("tbOrderNo" + str_tbOrderNo);
//              sb_premd5.append("tbOrderSnap" + str_tbOrderSnap);
//              sb_premd5.append(privateKey);
//      		sign= sb_premd5.toString();
//      	    System.out.println(sign);
//        StringBuilder sb_premd51 = new StringBuilder();
//        sb_premd51.append("cardId=" + cardId);
//        sb_premd51.append("&cardNum=" + cardNum);
//        sb_premd51.append("&coopId=" + cid);
//        sb_premd51.append("&customer=" + customer);
//        if (gameId!=null&&!gameId.isEmpty())
//        {
//        	sb_premd51.append("&gameId=" + gameId);
//        }
//        sb_premd51.append("&notifyUrl=" + ru);
//        sb_premd51.append("&sign=" + MD5Util.getMD5Sign(sign));
//        if (section1!=null&&!section1.isEmpty())
//        {
//        	sb_premd51.append("&section1=" + section1);
//        }
//        if (section2!=null&&!section2.isEmpty())
//        {
//        	sb_premd51.append("&section2=" + section2);
//        }
//        sb_premd51.append("&sum=" + sum);
//        sb_premd51.append("&tbOrderNo=" + tbOrderNo);
//        sb_premd51.append("&tbOrderSnap=" + tbOrderSnap);
//        String tburl = "http://localhost:38457/?" + sb_premd51.toString();
//        System.out.println(tburl);
//        
//        
//        
//        Long a=3l;
//		Long b=3l;
//		Long c=a+b;
//		System.out.println(c);
//		
//		
//		
//		String md5_Str = "haimi201504241357506JL82BB204B4FLB80422Z28FLN80202X";
//		String md5 = MD5Util.getMD5Sign(md5_Str);
//		System.out.println("------------原串:"+md5_Str+"-----------md5:"+md5+"------------------------------------------------------------");
//		System.out.println("------------------------------------------------------------------------------------------");
//
//		md5_Str = "20300511000000000";
//		md5 = MD5Util.getMD5Sign(md5_Str);
//		System.out.println("------------原串:"+md5_Str+"-----------md5:"+md5+"------------------------------------------------------------");
//		System.out.println("------------------------------------------------------------------------------------------");
//		
//		
//		
//		//流量业务通知接口回调地址生成
//		String ptOrderNo="300000000764";
//		
//		String supOrderNo="20150612163957333";
//		String status="ORDER_FAILED";//"ORDER_SUCCESS ORDER_FAILED";
//		String supOrderSuccessTime = "";//formatter.format(new Date());
//		String keyValue="123456";
//		String md5Str=ptOrderNo+supOrderNo+status+supOrderSuccessTime+keyValue;
//		//30000000044320150525143741924ORDER_SUCCESS20150525143355123456
//		//30000000044320150525143741924ORDER_SUCCESS20150525150521123456
//        System.out.println("-------------------------------------sign：原串----------------------------------------------------");
//		System.out.println(md5Str);
//		String signflow=MD5Util.getMD5Sign(md5Str);
//        System.out.println("-------------------------------------sign：密文----------------------------------------------------");
//        System.out.println(signflow);
//        String urlflow = "http://192.168.1.43:48455/ykhFlowNotify?ptOrderNo=" + ptOrderNo + "&supOrderNo=" + supOrderNo + "&status=" + status + "&supOrderSuccessTime=" + supOrderSuccessTime +  "&sign=" + signflow;
//        System.out.println("-------------------------------------url----------------------------------------------------");
//        System.out.println(urlflow);
//		
//	}
//}
