package com.yuecheng.hops.gateway.security.ssl;

/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.Security;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SSLContextFactoryImpl implements SSLContextFactory {
    private static final Logger logger = LoggerFactory.getLogger(SSLContextFactoryImpl.class);
    private String protocol;//协议
	private String keyStoreFile;//密钥文件
	private String keyStorePassword;//密码
	private String algorithm;//运算法则
	private String keyStoreType;//
    
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public void setKeyStoreFile(String keyStoreFile) {
		this.keyStoreFile = keyStoreFile;
	}

	public void setKeyStorePassword(String keyStorePassword) {
		this.keyStorePassword = keyStorePassword;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public void setKeyStoreType(String keyStoreType) {
		this.keyStoreType = keyStoreType;
	}

	private static SSLContext SERVER_CONTEXT;
	private static SSLContext CLIENT_CONTEXT;

	@Override
	public void init() {
		if (this.protocol == null) {
			this.protocol = "TLS";
		}

		this.algorithm = Security
				.getProperty("ssl.KeyManagerFactory.algorithm");
		if (this.algorithm == null) {
			this.algorithm = "SunX509";
		}

		if (this.keyStoreType == null) {
			this.keyStoreType = "JKS";
		}

		SSLContext serverContext = null;
		SSLContext clientContext = null;

		if (this.keyStoreFile != null && this.keyStorePassword != null) {
			char[] ksPassphrase = this.keyStorePassword.toCharArray();
			KeyStore ks = null;
			try {
				ks = KeyStore.getInstance(this.keyStoreType);
				String path = this.getClass().getResource("/").getPath();
//				InputStream keyStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(this.keyStoreFile);
				InputStream keyStream = new FileInputStream(path+this.keyStoreFile);
				ks.load(keyStream, ksPassphrase);
				// Set up key manager factory to use our key store

				try {
					KeyManagerFactory kmf = KeyManagerFactory
							.getInstance(this.algorithm);
					kmf.init(ks, ksPassphrase);

					// Initialize the SSLContext to work with our key managers.
					serverContext = SSLContext.getInstance(this.protocol);
					serverContext.init(kmf.getKeyManagers(), null, null);

					try {
						TrustManagerFactory tmf = TrustManagerFactory
								.getInstance(this.algorithm);
						tmf.init(ks);
						clientContext = SSLContext.getInstance(this.protocol);
						clientContext.init(null,tmf.getTrustManagers(), null);
					} catch (Exception e) {
						throw new Error(
								"Failed to initialize the client-side SSLContext",
								e);
					}

				} catch (Exception e1) {
                	logger.error("SSLContextFactoryImpl:[init]["+e1.getMessage()+"]");
				}

			} catch (Exception e) {
				throw new Error(
						"Failed to initialize the server-side SSLContext", e);
			}

		}

		SERVER_CONTEXT = serverContext;
		CLIENT_CONTEXT = clientContext;
	}

	public SSLContext createSSLServerContext() {
		return SERVER_CONTEXT;
	}

	public SSLContext createSSLClientContext() {
		return CLIENT_CONTEXT;
	}
}
