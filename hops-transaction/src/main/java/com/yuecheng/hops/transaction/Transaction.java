package com.yuecheng.hops.transaction;

public interface Transaction
{
    public TransactionRequest getRequest();

    public TransactionResponse getResponse();

    public TransactionContext getContext();

    public void setRequest(TransactionRequest request);

    public void setResponse(TransactionResponse response);

    public void setContext(TransactionContext context);
}
