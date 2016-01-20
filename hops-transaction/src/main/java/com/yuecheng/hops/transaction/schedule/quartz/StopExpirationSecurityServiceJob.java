package com.yuecheng.hops.transaction.schedule.quartz;


import com.yuecheng.hops.security.service.SecurityCredentialManagerService;


public class StopExpirationSecurityServiceJob
{
    private SecurityCredentialManagerService securityCredentialManagerService;

    public SecurityCredentialManagerService getSecurityCredentialManagerService()
    {
        return securityCredentialManagerService;
    }

    public void setSecurityCredentialManagerService(SecurityCredentialManagerService securityCredentialManagerService)
    {
        this.securityCredentialManagerService = securityCredentialManagerService;
    }

    public void execute()
    {
        // TODO Auto-generated method stub
        securityCredentialManagerService.stopSecurityCredential();
    }

}
