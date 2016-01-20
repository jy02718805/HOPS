package com.yuecheng.hops.transaction;

public interface TransactionCallableResolver
{
    TransactionService getTransactionService(Transaction transaction);
}
