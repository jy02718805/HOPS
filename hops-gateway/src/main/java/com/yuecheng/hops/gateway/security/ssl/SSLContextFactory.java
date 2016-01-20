package com.yuecheng.hops.gateway.security.ssl;

import javax.net.ssl.SSLContext;

public interface SSLContextFactory {
    
    public void setProtocol(String protocol);
    
    public void setKeyStoreFile(String keyStoreFile);

    public void setKeyStorePassword(String keyStorePassword) ;

    public void setAlgorithm(String algorithm) ;

    public void setKeyStoreType(String keyStoreType);
    
	public void init();
	
	public SSLContext createSSLServerContext();

	public SSLContext createSSLClientContext();
}
