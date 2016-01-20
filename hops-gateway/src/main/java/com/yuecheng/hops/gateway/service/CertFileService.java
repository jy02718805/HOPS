package com.yuecheng.hops.gateway.service;

import java.util.List;

import com.yuecheng.hops.common.security.CertFile;


public interface CertFileService {
	public List<CertFile> getAllAliasFromCertFile();
	
	public void uploadCertFile(byte[] byteFile,String alias) throws Exception;
	
	public void deleteCertFileByAlias(String Alias);
}
