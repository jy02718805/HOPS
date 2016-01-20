package com.yuecheng.hops.gateway.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import com.yuecheng.hops.common.security.CertFile;
import com.yuecheng.hops.common.utils.KeyToolUtils;
import com.yuecheng.hops.gateway.security.ssl.SSLContextFactory;
import com.yuecheng.hops.gateway.service.CertFileService;

@Service("certFileService")
public class CertFileServiceImpl implements CertFileService {
	@Autowired
	private SSLContextFactory sslContextFactory;
	
	private String keystorefileName;
	
	private String keyspasswd;

	private static final Logger logger = LoggerFactory.getLogger(CertFileServiceImpl.class);
	public void setKeystorefileName(String keystorefileName) {
		this.keystorefileName = keystorefileName;
	}

	public void setKeyspasswd(String keyspasswd) {
		this.keyspasswd = keyspasswd;
	}

	@Override
	public List<CertFile> getAllAliasFromCertFile() {
		FileInputStream in;
		String keystorefile = getClass().getResource("/").getFile().toString() + keystorefileName;
		try {
			in = new FileInputStream(keystorefile);
			List<CertFile> certFiles = KeyToolUtils.getKeystoreAllInfo(in, keyspasswd);
			return certFiles;
		}catch(Exception e){
        	logger.error("CertFileServiceImpl:[getAllAliasFromCertFile]["+e.getMessage()+"]");
			return null;
		}
	}

	@Override
	public void uploadCertFile(byte[] byteFile, String alias) throws Exception {
		File certFile = (File)SerializationUtils.deserialize(byteFile);
		FileInputStream fis = new FileInputStream(certFile);
		String keystorefile = getClass().getResource("/").getFile().toString() + keystorefileName;
		KeyToolUtils.setImportFlowCer(keystorefile,fis,keyspasswd,alias);
		sslContextFactory.init();
	}

	@Override
	public void deleteCertFileByAlias(String alias) {
		String keystorefile = getClass().getResource("/").getFile().toString() + keystorefileName;
		KeyToolUtils.deleteCer(keystorefile,keyspasswd,alias);
		sslContextFactory.init();
	}

}
