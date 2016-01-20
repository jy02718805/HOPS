package com.yuecheng.hops.transaction;

public class DefaultTransaction implements Transaction
{
    private TransactionContext  context  = new DefaultTransactionContextImpl();

    private TransactionRequest  request  = new DefaultTransactionRequestImpl();

    private TransactionResponse response = new DefaultTransactionResponseImpl();

    public DefaultTransaction()
    {
        context = new DefaultTransactionContextImpl();
        request = new DefaultTransactionRequestImpl();
        response = new DefaultTransactionResponseImpl();
    }

    public TransactionContext getContext()
    {
        return context;
    }

    public void setContext(TransactionContext context)
    {
        this.context = context;
    }

    public TransactionRequest getRequest()
    {
        return request;
    }

    public void setRequest(TransactionRequest request)
    {
        this.request = request;
    }

    public TransactionResponse getResponse()
    {
        return response;
    }

    public void setResponse(TransactionResponse response)
    {
        this.response = response;
    }

}
