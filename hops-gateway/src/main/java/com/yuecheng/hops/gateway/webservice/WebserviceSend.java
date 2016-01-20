package com.yuecheng.hops.gateway.webservice;

import java.rmi.RemoteException;

import org.apache.axis.AxisFault;
import org.springframework.stereotype.Component;

@Component("webserviceSend")
public class WebserviceSend {
	public  String send(String str,String sendMessage) throws RemoteException, Exception {
		String result = null;
		try {
			IDispatchControl service =  new IDispatchControlLocator();
			IDispatchControlPortType client =  service.getIDispatchControlHttpPort();
			result = client.dispatchCommand(str, sendMessage);
		} catch (AxisFault e) {
			e.printStackTrace();
		}
		return result;
		
	}
}
