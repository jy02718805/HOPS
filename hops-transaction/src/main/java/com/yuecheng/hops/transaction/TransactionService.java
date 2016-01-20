package com.yuecheng.hops.transaction;

public interface TransactionService
{
    TransactionResponse doTransaction(TransactionRequest request);
}
