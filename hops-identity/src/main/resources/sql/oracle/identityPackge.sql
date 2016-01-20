create or replace procedure save_ccyAcBalance_History(transactionId          in number,
                                                      accountId              in number,
                                                      newAvailableBalance    in number,
                                                      newUnavailableBanlance in number,
                                                      newCreditableBanlance  in number,
                                                      createDate             in date,
                                                      historyType            in varchar2,
                                                      descStr                in varchar2,
                                                      identityName           in varchar2,
                                                      his_id                 in number,
                                                      changeAmount           in number,
                                                      accountTypeId          in number) as
  modValue number;
begin

  modValue := mod(his_id, 4);

  if modValue = 0 then
    insert into ccy_account_balance_history_0
      (id,
       transaction_id,
       account_id,
       new_available_balance,
       new_unavailable_banlance,
       new_creditable_banlance,
       create_date,
       type,
       desc_str,
       identity_name,
       change_amount,
       ACCOUNT_TYPE_ID)
    values
      (his_id,
       transactionId,
       accountId,
       newAvailableBalance,
       newUnavailableBanlance,
       newCreditableBanlance,
       createDate,
       historyType,
       descStr,
       identityName,
       changeAmount,
       accountTypeId);
  elsif modValue = 1 then
    insert into ccy_account_balance_history_1
      (id,
       transaction_id,
       account_id,
       new_available_balance,
       new_unavailable_banlance,
       new_creditable_banlance,
       create_date,
       type,
       desc_str,
       identity_name,
       change_amount,
       ACCOUNT_TYPE_ID)
    values
      (his_id,
       transactionId,
       accountId,
       newAvailableBalance,
       newUnavailableBanlance,
       newCreditableBanlance,
       createDate,
       historyType,
       descStr,
       identityName,
       changeAmount,
       accountTypeId);
  elsif modValue = 2 then
    insert into ccy_account_balance_history_2
      (id,
       transaction_id,
       account_id,
       new_available_balance,
       new_unavailable_banlance,
       new_creditable_banlance,
       create_date,
       type,
       desc_str,
       identity_name,
       change_amount,
       ACCOUNT_TYPE_ID)
    values
      (his_id,
       transactionId,
       accountId,
       newAvailableBalance,
       newUnavailableBanlance,
       newCreditableBanlance,
       createDate,
       historyType,
       descStr,
       identityName,
       changeAmount,
       accountTypeId);
  elsif modValue = 3 then
    insert into ccy_account_balance_history_3
      (id,
       transaction_id,
       account_id,
       new_available_balance,
       new_unavailable_banlance,
       new_creditable_banlance,
       create_date,
       type,
       desc_str,
       identity_name,
       change_amount,
       ACCOUNT_TYPE_ID)
    values
      (his_id,
       transactionId,
       accountId,
       newAvailableBalance,
       newUnavailableBanlance,
       newCreditableBanlance,
       createDate,
       historyType,
       descStr,
       identityName,
       changeAmount,
       accountTypeId);
  end if;
exception
  when others then
    rollback;
    return;
end;
/

create or replace procedure ccyAc_balance_credit(transactionId in number,
                                                 accountId     in number,
                                                 amt           in number,
                                                 historyType   in varchar2,
                                                 descStr       in varchar2,
                                                 accountTypeId in number,
                                                 tableName     in varchar2,
                                                 transfer_flag out number,
                                                 error_msg     out varchar2) as
  new_available_balance    number(18, 2);
  new_unavailable_banlance number(18, 2);
  new_creditable_banlance  number(18, 2);
  identityId               number;
  identityType             varchar2(64);
  identityName             varchar2(30);
  his_id                   number;
  transfer_num             number;
  accountDirectory         varchar2(10);
begin
  identityName  := '';
  transfer_flag := '0001';

  select at.directory
    into accountDirectory
    from account_type at
   where at.account_type_id = accountTypeId;

  his_id := CCY_AC_BALANCE_ID_SEQ.nextval;

  select iar.identity_type, iar.identity_id
    into identityType, identityId
    from identity_account_role iar
   where iar.account_id = accountId
     and iar.relation = 'own';

  if identityType = 'MERCHANT' then
    select mer.merchant_name
      into identityName
      from merchant mer
     where mer.identity_id = identityId;
  elsif identityType = 'SP' then
    select s.sp_name
      into identityName
      from sp s
     where s.identity_id = identityId;
  elsif identityType = 'OPERATOR' then
    select o.operator_name
      into identityName
      from operator o
     where o.identity_id = identityId;
  elsif identityType = 'CUSTOMER' then
    select c.customer_name
      into identityName
      from CUSTOMER c
     where c.identity_id = identityId;
  end if;

  --锁表
  if tableName = 'ccy_account' then
    select c.available_balance,
           c.unavailable_banlance,
           c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from ccy_account c
     where c.account_id = accountId
       for update;
  
    if accountDirectory = 'CREDIT' then
      update ccy_account c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance + amt
       where c.account_id = accountId;
    elsif accountDirectory = 'DEBIT' then
      update ccy_account c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance - amt
       where c.account_id = accountId
         and amt <= c.available_balance + c.creditable_banlance;
    end if;
  
    select c.available_balance,
           c.unavailable_banlance,
           c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from ccy_account c
     where c.account_id = accountId;
  
    save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
  
    select count(0)
      into transfer_num
      from ccy_account c
     where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg     := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg     := '存储过程执行成功!';
      commit;
    end if;
  
  elsif tableName = 'merchant_credit_account' then
    select c.available_balance,
           c.unavailable_banlance,
           c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from merchant_credit_account c
     where c.account_id = accountId
       for update;
  
    if accountDirectory = 'CREDIT' then
      update merchant_credit_account c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance + amt
       where c.account_id = accountId;
    elsif accountDirectory = 'DEBIT' then
      update merchant_credit_account c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance - amt
       where c.account_id = accountId
         and amt <= c.available_balance + c.creditable_banlance;
    end if;
    
    select c.available_balance,
           c.unavailable_banlance,
           c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from merchant_credit_account c
     where c.account_id = accountId;
  
    save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
  
    select count(0)
      into transfer_num
      from merchant_credit_account c
     where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg     := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg     := '存储过程执行成功!';
      commit;
    end if;
  
  elsif tableName = 'merchant_debit_account' then
    select c.available_balance,
           c.unavailable_banlance,
           c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from merchant_debit_account c
     where c.account_id = accountId
       for update;
  
    if accountDirectory = 'CREDIT' then
      update merchant_debit_account c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance + amt
       where c.account_id = accountId;
    elsif accountDirectory = 'DEBIT' then
      update merchant_debit_account c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance - amt
       where c.account_id = accountId
         and amt <= c.available_balance + c.creditable_banlance;
    end if;
    
    select c.available_balance,
           c.unavailable_banlance,
           c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from merchant_debit_account c
     where c.account_id = accountId;
  
    save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
  
    select count(0)
      into transfer_num
      from merchant_debit_account c
     where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg     := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg     := '存储过程执行成功!';
      commit;
    end if;
  
  elsif tableName = 'system_debit_account0' then
    select c.available_balance,
           c.unavailable_banlance,
           c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account0 c
     where c.account_id = accountId
       for update;
  
    if accountDirectory = 'CREDIT' then
      update system_debit_account0 c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance + amt
       where c.account_id = accountId;
    elsif accountDirectory = 'DEBIT' then
      update system_debit_account0 c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance - amt
       where c.account_id = accountId
         and amt <= c.available_balance + c.creditable_banlance;
    end if;
    
    select c.available_balance,
           c.unavailable_banlance,
           c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account0 c
     where c.account_id = accountId;
  
    save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
  
    select count(0)
      into transfer_num
      from system_debit_account0 c
     where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg     := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg     := '存储过程执行成功!';
      commit;
    end if;
    
  elsif tableName = 'system_debit_account1' then
    select c.available_balance,
           c.unavailable_banlance,
           c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account1 c
     where c.account_id = accountId
       for update;
  
    if accountDirectory = 'CREDIT' then
      update system_debit_account1 c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance + amt
       where c.account_id = accountId;
    elsif accountDirectory = 'DEBIT' then
      update system_debit_account1 c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance - amt
       where c.account_id = accountId
         and amt <= c.available_balance + c.creditable_banlance;
    end if;
  
    select c.available_balance,
           c.unavailable_banlance,
           c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account1 c
     where c.account_id = accountId;
  
    save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
  
    select count(0)
      into transfer_num
      from system_debit_account1 c
     where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg     := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg     := '存储过程执行成功!';
      commit;
    end if;
  
  elsif tableName = 'system_debit_account2' then
    select c.available_balance,
           c.unavailable_banlance,
           c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account2 c
     where c.account_id = accountId
       for update;
  
    if accountDirectory = 'CREDIT' then
      update system_debit_account2 c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance + amt
       where c.account_id = accountId;
    elsif accountDirectory = 'DEBIT' then
      update system_debit_account2 c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance - amt
       where c.account_id = accountId
         and amt <= c.available_balance + c.creditable_banlance;
    end if;
    
    select c.available_balance,
           c.unavailable_banlance,
           c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account2 c
     where c.account_id = accountId;
  
    save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
  
    select count(0)
      into transfer_num
      from system_debit_account2 c
     where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg     := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg     := '存储过程执行成功!';
      commit;
    end if;
  
  elsif tableName = 'system_debit_account3' then
    select c.available_balance,
           c.unavailable_banlance,
           c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account3 c
     where c.account_id = accountId
       for update;
  
    if accountDirectory = 'CREDIT' then
      update system_debit_account3 c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance + amt
       where c.account_id = accountId;
    elsif accountDirectory = 'DEBIT' then
      update system_debit_account3 c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance - amt
       where c.account_id = accountId
         and amt <= c.available_balance + c.creditable_banlance;
    end if;
  
    select c.available_balance,
           c.unavailable_banlance,
           c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account3 c
     where c.account_id = accountId;
  
    save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
  
    select count(0)
      into transfer_num
      from system_debit_account3 c
     where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg     := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg     := '存储过程执行成功!';
      commit;
    end if;
  
  elsif tableName = 'system_debit_account4' then
    select c.available_balance,
           c.unavailable_banlance,
           c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account4 c
     where c.account_id = accountId
       for update;
  
    if accountDirectory = 'CREDIT' then
      update system_debit_account4 c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance + amt
       where c.account_id = accountId;
    elsif accountDirectory = 'DEBIT' then
      update system_debit_account4 c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance - amt
       where c.account_id = accountId
         and amt <= c.available_balance + c.creditable_banlance;
    end if;
  
    select c.available_balance,
           c.unavailable_banlance,
           c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account4 c
     where c.account_id = accountId;
  
    save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
  
    select count(0)
      into transfer_num
      from system_debit_account4 c
     where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg     := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg     := '存储过程执行成功!';
      commit;
    end if;
  
  elsif tableName = 'system_debit_account5' then
    select c.available_balance,
           c.unavailable_banlance,
           c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account5 c
     where c.account_id = accountId
       for update;
  
    if accountDirectory = 'CREDIT' then
      update system_debit_account5 c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance + amt
       where c.account_id = accountId;
    elsif accountDirectory = 'DEBIT' then
      update system_debit_account5 c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance - amt
       where c.account_id = accountId
         and amt <= c.available_balance + c.creditable_banlance;
    end if;
    
    select c.available_balance,
           c.unavailable_banlance,
           c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account5 c
     where c.account_id = accountId;
  
    save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
  
    select count(0)
      into transfer_num
      from system_debit_account5 c
     where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg     := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg     := '存储过程执行成功!';
      commit;
    end if;
  
  elsif tableName = 'system_debit_account6' then
    select c.available_balance,
           c.unavailable_banlance,
           c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account6 c
     where c.account_id = accountId
       for update;
  
    if accountDirectory = 'CREDIT' then
      update system_debit_account6 c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance + amt
       where c.account_id = accountId;
    elsif accountDirectory = 'DEBIT' then
      update system_debit_account6 c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance - amt
       where c.account_id = accountId
         and amt <= c.available_balance + c.creditable_banlance;
    end if;
    
    select c.available_balance,
           c.unavailable_banlance,
           c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account6 c
     where c.account_id = accountId;
  
    save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
                              
    select count(0)
      into transfer_num
      from system_debit_account6 c
     where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg     := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg     := '存储过程执行成功!';
      commit;
    end if;
  
  elsif tableName = 'system_debit_account7' then
    select c.available_balance,
           c.unavailable_banlance,
           c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account7 c
     where c.account_id = accountId
       for update;
  
    if accountDirectory = 'CREDIT' then
      update system_debit_account7 c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance + amt
       where c.account_id = accountId;
    elsif accountDirectory = 'DEBIT' then
      update system_debit_account7 c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance - amt
       where c.account_id = accountId
         and amt <= c.available_balance + c.creditable_banlance;
    end if;
    
    select c.available_balance,
           c.unavailable_banlance,
           c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account7 c
     where c.account_id = accountId;
  
    save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
  
    select count(0)
      into transfer_num
      from system_debit_account7 c
     where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg     := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg     := '存储过程执行成功!';
      commit;
    end if;
  
  elsif tableName = 'system_debit_account8' then
    select c.available_balance,
           c.unavailable_banlance,
           c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account8 c
     where c.account_id = accountId
       for update;
  
    if accountDirectory = 'CREDIT' then
      update system_debit_account8 c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance + amt
       where c.account_id = accountId;
    elsif accountDirectory = 'DEBIT' then
      update system_debit_account8 c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance - amt
       where c.account_id = accountId
         and amt <= c.available_balance + c.creditable_banlance;
    end if;
    
    select c.available_balance,
           c.unavailable_banlance,
           c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account8 c
     where c.account_id = accountId;
  
    save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
  
    select count(0)
      into transfer_num
      from system_debit_account8 c
     where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg     := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg     := '存储过程执行成功!';
      commit;
    end if;
  
  elsif tableName = 'system_debit_account9' then
    select c.available_balance,
           c.unavailable_banlance,
           c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account9 c
     where c.account_id = accountId
       for update;
  
    if accountDirectory = 'CREDIT' then
      update system_debit_account9 c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance + amt
       where c.account_id = accountId;
    elsif accountDirectory = 'DEBIT' then
      update system_debit_account9 c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance - amt
       where c.account_id = accountId
         and amt <= c.available_balance + c.creditable_banlance;
    end if;
    
    select c.available_balance,
           c.unavailable_banlance,
           c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account9 c
     where c.account_id = accountId;
  
    save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
  
    select count(0)
      into transfer_num
      from system_debit_account9 c
     where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg     := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg     := '存储过程执行成功!';
      commit;
    end if;
  
  end if;
exception
  when others then
    rollback;
    transfer_flag := '9999';
    error_msg     := sqlerrm;
    return;
end;
/

create or replace procedure ccyAc_balance_debit(transactionId in number,
                                                accountId     in number,
                                                amt           in number,
                                                historyType   in varchar2,
                                                descStr       in varchar2,
                                                accountTypeId in number,
                                                tableName     in varchar2,
                                                transfer_flag out number,
                                                error_msg     out varchar2) as
  new_available_balance    number(18, 2);
  new_unavailable_banlance number(18, 2);
  new_creditable_banlance  number(18, 2);
  identityId               number;
  identityType             varchar2(64);
  identityName             varchar2(30);
  his_id                   number;
  transfer_num             number;
  accountDirectory         varchar2(10);
begin
  identityName  := '';
  transfer_flag := '0001';
  
  select at.directory
    into accountDirectory
    from account_type at
   where at.account_type_id = accountTypeId;

  his_id := CCY_AC_BALANCE_ID_SEQ.nextval;
  
  select iar.identity_type, iar.identity_id
    into identityType, identityId
    from identity_account_role iar
   where iar.account_id = accountId
     and iar.relation = 'own';

  if identityType = 'MERCHANT' then
    select mer.merchant_name
      into identityName
      from merchant mer
     where mer.identity_id = identityId;
  elsif identityType = 'SP' then
    select s.sp_name
      into identityName
      from sp s
     where s.identity_id = identityId;
  elsif identityType = 'OPERATOR' then
    select o.operator_name
      into identityName
      from operator o
     where o.identity_id = identityId;
  elsif identityType = 'CUSTOMER' then
    select c.customer_name
      into identityName
      from CUSTOMER c
     where c.identity_id = identityId;
  end if;
  
  if tableName = 'ccy_account' then
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from ccy_account c
     where c.account_id = accountId
       for update;
  
    if accountDirectory = 'DEBIT' then
      update ccy_account c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance + amt
       where c.account_id = accountId;
    elsif accountDirectory = 'CREDIT' then
      update ccy_account c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance - amt
       where c.account_id = accountId
         and amt <= c.available_balance + c.creditable_banlance;
    end if;
  
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from ccy_account c
     where c.account_id = accountId;
  
    save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
  
    select count(0)
      into transfer_num
      from ccy_account c
     where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg     := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg     := '存储过程执行成功!';
      commit;
    end if;
  elsif tableName = 'merchant_credit_account' then
      select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from merchant_credit_account c
     where c.account_id = accountId
       for update;
  
    if accountDirectory = 'DEBIT' then
      update merchant_credit_account c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance + amt
       where c.account_id = accountId;
    elsif accountDirectory = 'CREDIT' then
      update merchant_credit_account c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance - amt
       where c.account_id = accountId
         and amt <= c.available_balance + c.creditable_banlance;
    end if;
  
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from merchant_credit_account c
     where c.account_id = accountId;
  
    save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
  
    select count(0)
      into transfer_num
      from merchant_credit_account c
     where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg     := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg     := '存储过程执行成功!';
      commit;
    end if;
  elsif tableName = 'merchant_debit_account' then
      select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from merchant_debit_account c
     where c.account_id = accountId
       for update;
  
    if accountDirectory = 'DEBIT' then
      update merchant_debit_account c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance + amt
       where c.account_id = accountId;
    elsif accountDirectory = 'CREDIT' then
      update merchant_debit_account c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance - amt
       where c.account_id = accountId
         and amt <= c.available_balance + c.creditable_banlance;
    end if;
  
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from merchant_debit_account c
     where c.account_id = accountId;
  
    save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
  
    select count(0)
      into transfer_num
      from merchant_debit_account c
     where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg     := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg     := '存储过程执行成功!';
      commit;
    end if;
  elsif tableName = 'system_debit_account0' then
      select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account0 c
     where c.account_id = accountId
       for update;
  
    if accountDirectory = 'DEBIT' then
      update system_debit_account0 c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance + amt
       where c.account_id = accountId;
    elsif accountDirectory = 'CREDIT' then
      update system_debit_account0 c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance - amt
       where c.account_id = accountId
         and amt <= c.available_balance + c.creditable_banlance;
    end if;
  
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account0 c
     where c.account_id = accountId;
  
    save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
  
    select count(0)
      into transfer_num
      from system_debit_account0 c
     where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg     := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg     := '存储过程执行成功!';
      commit;
    end if;
  elsif tableName = 'system_debit_account1' then
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account1 c
     where c.account_id = accountId
       for update;
  
    if accountDirectory = 'DEBIT' then
      update system_debit_account1 c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance + amt
       where c.account_id = accountId;
    elsif accountDirectory = 'CREDIT' then
      update system_debit_account1 c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance - amt
       where c.account_id = accountId
         and amt <= c.available_balance + c.creditable_banlance;
    end if;
  
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account1 c
     where c.account_id = accountId;
  
    save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
  
    select count(0)
      into transfer_num
      from system_debit_account1 c
     where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg     := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg     := '存储过程执行成功!';
      commit;
    end if;
  elsif tableName = 'system_debit_account2' then
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account2 c
     where c.account_id = accountId
       for update;
  
    if accountDirectory = 'DEBIT' then
      update system_debit_account2 c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance + amt
       where c.account_id = accountId;
    elsif accountDirectory = 'CREDIT' then
      update system_debit_account2 c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance - amt
       where c.account_id = accountId
         and amt <= c.available_balance + c.creditable_banlance;
    end if;
  
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account2 c
     where c.account_id = accountId;
  
    save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
  
    select count(0)
      into transfer_num
      from system_debit_account2 c
     where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg     := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg     := '存储过程执行成功!';
      commit;
    end if;
  elsif tableName = 'system_debit_account3' then
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account3 c
     where c.account_id = accountId
       for update;
  
    if accountDirectory = 'DEBIT' then
      update system_debit_account3 c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance + amt
       where c.account_id = accountId;
    elsif accountDirectory = 'CREDIT' then
      update system_debit_account3 c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance - amt
       where c.account_id = accountId
         and amt <= c.available_balance + c.creditable_banlance;
    end if;
  
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account3 c
     where c.account_id = accountId;
  
    save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
  
    select count(0)
      into transfer_num
      from system_debit_account3 c
     where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg     := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg     := '存储过程执行成功!';
      commit;
    end if;
  elsif tableName = 'system_debit_account4' then
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account4 c
     where c.account_id = accountId
       for update;
  
    if accountDirectory = 'DEBIT' then
      update system_debit_account4 c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance + amt
       where c.account_id = accountId;
    elsif accountDirectory = 'CREDIT' then
      update system_debit_account4 c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance - amt
       where c.account_id = accountId
         and amt <= c.available_balance + c.creditable_banlance;
    end if;
  
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account4 c
     where c.account_id = accountId;
  
    save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
  
    select count(0)
      into transfer_num
      from system_debit_account4 c
     where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg     := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg     := '存储过程执行成功!';
      commit;
    end if;
  elsif tableName = 'system_debit_account5' then
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account5 c
     where c.account_id = accountId
       for update;
  
    if accountDirectory = 'DEBIT' then
      update system_debit_account5 c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance + amt
       where c.account_id = accountId;
    elsif accountDirectory = 'CREDIT' then
      update system_debit_account5 c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance - amt
       where c.account_id = accountId
         and amt <= c.available_balance + c.creditable_banlance;
    end if;
  
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account5 c
     where c.account_id = accountId;
  
    save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
  
    select count(0)
      into transfer_num
      from system_debit_account5 c
     where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg     := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg     := '存储过程执行成功!';
      commit;
    end if;
  elsif tableName = 'system_debit_account6' then
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account6 c
     where c.account_id = accountId
       for update;
  
    if accountDirectory = 'DEBIT' then
      update system_debit_account6 c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance + amt
       where c.account_id = accountId;
    elsif accountDirectory = 'CREDIT' then
      update system_debit_account6 c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance - amt
       where c.account_id = accountId
         and amt <= c.available_balance + c.creditable_banlance;
    end if;
  
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account6 c
     where c.account_id = accountId;
  
    save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
  
    select count(0)
      into transfer_num
      from system_debit_account6 c
     where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg     := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg     := '存储过程执行成功!';
      commit;
    end if;
  elsif tableName = 'system_debit_account7' then
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account7 c
     where c.account_id = accountId
       for update;
  
    if accountDirectory = 'DEBIT' then
      update system_debit_account7 c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance + amt
       where c.account_id = accountId;
    elsif accountDirectory = 'CREDIT' then
      update system_debit_account7 c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance - amt
       where c.account_id = accountId
         and amt <= c.available_balance + c.creditable_banlance;
    end if;
  
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account7 c
     where c.account_id = accountId;
  
    save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
  
    select count(0)
      into transfer_num
      from system_debit_account7 c
     where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg     := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg     := '存储过程执行成功!';
      commit;
    end if;
  elsif tableName = 'system_debit_account8' then
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account8 c
     where c.account_id = accountId
       for update;
  
    if accountDirectory = 'DEBIT' then
      update system_debit_account8 c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance + amt
       where c.account_id = accountId;
    elsif accountDirectory = 'CREDIT' then
      update system_debit_account8 c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance - amt
       where c.account_id = accountId
         and amt <= c.available_balance + c.creditable_banlance;
    end if;
  
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account8 c
     where c.account_id = accountId;
  
    save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
  
    select count(0)
      into transfer_num
      from system_debit_account8 c
     where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg     := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg     := '存储过程执行成功!';
      commit;
    end if;
  elsif tableName = 'system_debit_account9' then
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account9 c
     where c.account_id = accountId
       for update;
  
    if accountDirectory = 'DEBIT' then
      update system_debit_account9 c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance + amt
       where c.account_id = accountId;
    elsif accountDirectory = 'CREDIT' then
      update system_debit_account9 c
         set c.history_id        = his_id,
             c.available_balance = c.available_balance - amt
       where c.account_id = accountId
         and amt <= c.available_balance + c.creditable_banlance;
    end if;
  
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account9 c
     where c.account_id = accountId;
  
    save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
  
    select count(0)
      into transfer_num
      from system_debit_account9 c
     where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg     := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg     := '存储过程执行成功!';
      commit;
    end if;
  end if;

exception
  when others then
    rollback;
    transfer_flag := '9999';
    error_msg     := sqlerrm;
    return;
end;
/

create or replace procedure ccyAc_balance_frozen(transactionId in number,
                                                 accountId     in number,
                                                 amt           in number,
                                                 historyType   in varchar2,
                                                 descStr       in varchar2,
                                                 tableName     in varchar2,
                                                 accountTypeId in number,
                                                 transfer_flag out number,
                                                 error_msg     out varchar2) as
  new_available_balance    number(18, 2);
  new_unavailable_banlance number(18, 2);
  new_creditable_banlance  number(18, 2);
  identityId               number;
  identityType             varchar2(64);
  identityName             varchar2(30);
  his_id                   number;
  transfer_num             number;
begin
  identityName  := '';
  transfer_flag := '0001';
  
  select iar.identity_type, iar.identity_id
    into identityType, identityId
    from identity_account_role iar
   where iar.account_id = accountId
     and iar.relation = 'own';

  if identityType = 'MERCHANT' then
    select mer.merchant_name
      into identityName
      from merchant mer
     where mer.identity_id = identityId;
  elsif identityType = 'SP' then
    select s.sp_name
      into identityName
      from sp s
     where s.identity_id = identityId;
  elsif identityType = 'OPERATOR' then
    select o.operator_name
      into identityName
      from operator o
     where o.identity_id = identityId;
  elsif identityType = 'CUSTOMER' then
    select c.customer_name
      into identityName
      from CUSTOMER c
     where c.identity_id = identityId;
  end if;
  
  his_id := CCY_AC_BALANCE_ID_SEQ.nextval;
  
  
  if tableName = 'ccy_account' then
	  select c.available_balance, c.unavailable_banlance, c.creditable_banlance
	    into new_available_balance,
	         new_unavailable_banlance,
	         new_creditable_banlance
	    from ccy_account c
	   where c.account_id = accountId
	     for update;
	  
	  update ccy_account c
	     set c.history_id           = his_id,
	         c.available_balance    = c.available_balance - amt,
	         c.unavailable_banlance = c.unavailable_banlance + amt
	   where c.account_id = accountId
	     and c.available_balance + c.creditable_banlance >= amt;
	
	  select c.available_balance, c.unavailable_banlance, c.creditable_banlance
	    into new_available_balance,
	         new_unavailable_banlance,
	         new_creditable_banlance
	    from ccy_account c
	   where c.account_id = accountId;
	
	  save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
  
    select count(0)
      into transfer_num
      from ccy_account c
     where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg     := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg     := '存储过程执行成功!';
      commit;
    end if;
  elsif tableName = 'merchant_credit_account' then
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from merchant_credit_account c
     where c.account_id = accountId
       for update;
    
    update merchant_credit_account c
       set c.history_id           = his_id,
           c.available_balance    = c.available_balance - amt,
           c.unavailable_banlance = c.unavailable_banlance + amt
     where c.account_id = accountId
       and c.available_balance + c.creditable_banlance >= amt;
  
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from merchant_credit_account c
     where c.account_id = accountId;
  
    save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
  
    select count(0)
      into transfer_num
      from merchant_credit_account c
     where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg     := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg     := '存储过程执行成功!';
      commit;
    end if;
  elsif tableName = 'merchant_debit_account' then
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from merchant_debit_account c
     where c.account_id = accountId
       for update;
    
    update merchant_debit_account c
       set c.history_id           = his_id,
           c.available_balance    = c.available_balance - amt,
           c.unavailable_banlance = c.unavailable_banlance + amt
     where c.account_id = accountId
       and c.available_balance + c.creditable_banlance >= amt;
  
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from merchant_debit_account c
     where c.account_id = accountId;
  
    save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
  
    select count(0)
      into transfer_num
      from merchant_debit_account c
     where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg     := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg     := '存储过程执行成功!';
      commit;
    end if;
  elsif tableName = 'system_debit_account0' then
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account0 c
     where c.account_id = accountId
       for update;
    
    update system_debit_account0 c
       set c.history_id           = his_id,
           c.available_balance    = c.available_balance - amt,
           c.unavailable_banlance = c.unavailable_banlance + amt
     where c.account_id = accountId
       and c.available_balance + c.creditable_banlance >= amt;
  
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account0 c
     where c.account_id = accountId;
  
    save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
  
    select count(0)
      into transfer_num
      from system_debit_account0 c
     where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg     := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg     := '存储过程执行成功!';
      commit;
    end if;
  elsif tableName = 'system_debit_account1' then
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account1 c
     where c.account_id = accountId
       for update;
    
    update system_debit_account1 c
       set c.history_id           = his_id,
           c.available_balance    = c.available_balance - amt,
           c.unavailable_banlance = c.unavailable_banlance + amt
     where c.account_id = accountId
       and c.available_balance + c.creditable_banlance >= amt;
  
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account1 c
     where c.account_id = accountId;
  
    save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
                              
    select count(0)
      into transfer_num
      from system_debit_account1 c
     where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg     := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg     := '存储过程执行成功!';
      commit;
    end if;
  elsif tableName = 'system_debit_account2' then
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account2 c
     where c.account_id = accountId
       for update;
    
    update system_debit_account2 c
       set c.history_id           = his_id,
           c.available_balance    = c.available_balance - amt,
           c.unavailable_banlance = c.unavailable_banlance + amt
     where c.account_id = accountId
       and c.available_balance + c.creditable_banlance >= amt;
  
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account2 c
     where c.account_id = accountId;
  
    save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
  
    select count(0)
      into transfer_num
      from system_debit_account2 c
     where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg     := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg     := '存储过程执行成功!';
      commit;
    end if;
  elsif tableName = 'system_debit_account3' then
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account3 c
     where c.account_id = accountId
       for update;
    
    update system_debit_account3 c
       set c.history_id           = his_id,
           c.available_balance    = c.available_balance - amt,
           c.unavailable_banlance = c.unavailable_banlance + amt
     where c.account_id = accountId
       and c.available_balance + c.creditable_banlance >= amt;
  
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account3 c
     where c.account_id = accountId;
  
    save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
  
    select count(0)
      into transfer_num
      from system_debit_account3 c
     where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg     := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg     := '存储过程执行成功!';
      commit;
    end if;
  elsif tableName = 'system_debit_account4' then
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account4 c
     where c.account_id = accountId
       for update;
    
    update system_debit_account4 c
       set c.history_id           = his_id,
           c.available_balance    = c.available_balance - amt,
           c.unavailable_banlance = c.unavailable_banlance + amt
     where c.account_id = accountId
       and c.available_balance + c.creditable_banlance >= amt;
  
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account4 c
     where c.account_id = accountId;
  
    save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
  
    select count(0)
      into transfer_num
      from system_debit_account4 c
     where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg     := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg     := '存储过程执行成功!';
      commit;
    end if;
  elsif tableName = 'system_debit_account5' then
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account5 c
     where c.account_id = accountId
       for update;
    
    update system_debit_account5 c
       set c.history_id           = his_id,
           c.available_balance    = c.available_balance - amt,
           c.unavailable_banlance = c.unavailable_banlance + amt
     where c.account_id = accountId
       and c.available_balance + c.creditable_banlance >= amt;
  
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account5 c
     where c.account_id = accountId;
  
    save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
  
    select count(0)
      into transfer_num
      from system_debit_account5 c
     where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg     := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg     := '存储过程执行成功!';
      commit;
    end if;
  elsif tableName = 'system_debit_account6' then
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account6 c
     where c.account_id = accountId
       for update;
    
    update system_debit_account6 c
       set c.history_id           = his_id,
           c.available_balance    = c.available_balance - amt,
           c.unavailable_banlance = c.unavailable_banlance + amt
     where c.account_id = accountId
       and c.available_balance + c.creditable_banlance >= amt;
  
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account6 c
     where c.account_id = accountId;
  
    save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
  
    select count(0)
      into transfer_num
      from system_debit_account6 c
     where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg     := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg     := '存储过程执行成功!';
      commit;
    end if;
  elsif tableName = 'system_debit_account7' then
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account7 c
     where c.account_id = accountId
       for update;
    
    update system_debit_account7 c
       set c.history_id           = his_id,
           c.available_balance    = c.available_balance - amt,
           c.unavailable_banlance = c.unavailable_banlance + amt
     where c.account_id = accountId
       and c.available_balance + c.creditable_banlance >= amt;
  
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account7 c
     where c.account_id = accountId;
  
    save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
  
    select count(0)
      into transfer_num
      from system_debit_account7 c
     where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg     := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg     := '存储过程执行成功!';
      commit;
    end if;
  elsif tableName = 'system_debit_account8' then
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account8 c
     where c.account_id = accountId
       for update;
    
    update system_debit_account8 c
       set c.history_id           = his_id,
           c.available_balance    = c.available_balance - amt,
           c.unavailable_banlance = c.unavailable_banlance + amt
     where c.account_id = accountId
       and c.available_balance + c.creditable_banlance >= amt;
  
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account8 c
     where c.account_id = accountId;
  
    save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
  
    select count(0)
      into transfer_num
      from system_debit_account8 c
     where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg     := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg     := '存储过程执行成功!';
      commit;
    end if;
  elsif tableName = 'system_debit_account9' then
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account9 c
     where c.account_id = accountId
       for update;
    
    update system_debit_account9 c
       set c.history_id           = his_id,
           c.available_balance    = c.available_balance - amt,
           c.unavailable_banlance = c.unavailable_banlance + amt
     where c.account_id = accountId
       and c.available_balance + c.creditable_banlance >= amt;
  
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account9 c
     where c.account_id = accountId;
  
    save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
  
    select count(0)
      into transfer_num
      from system_debit_account9 c
     where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg     := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg     := '存储过程执行成功!';
      commit;
    end if;
  end if;

exception
  when others then
    rollback;
    transfer_flag := '9999';
    error_msg     := sqlerrm;
    return;
end;
/

create or replace procedure ccyAc_balance_unfrozen(transactionId in number,
                                                   accountId     in number,
                                                   amt           in number,
                                                   historyType   in varchar2,
                                                   descStr       in varchar2,
                                                   tableName     in varchar2,
                                                   accountTypeId in number,
                                                   transfer_flag out number,
                                                   error_msg     out varchar2
                                                   ) as
  new_available_balance    number(18, 2);
  new_unavailable_banlance number(18, 2);
  new_creditable_banlance  number(18, 2);
  identityId               number;
  identityType             varchar2(64);
  identityName             varchar2(30);
  his_id                   number;
  transfer_num             number;
begin
  identityName := '';
  transfer_flag := '0001';
  
  his_id := CCY_AC_BALANCE_ID_SEQ.nextval;
  select iar.identity_type, iar.identity_id
    into identityType, identityId
    from identity_account_role iar
   where iar.account_id = accountId
     and iar.relation = 'own';

  if identityType = 'MERCHANT' then
    select mer.merchant_name
      into identityName
      from merchant mer
     where mer.identity_id = identityId;
  elsif  identityType = 'SP' then
    select s.sp_name
      into identityName
      from sp s
     where s.identity_id = identityId;
  elsif  identityType = 'OPERATOR' then
    select o.operator_name
      into identityName
      from operator o
     where o.identity_id = identityId;
  elsif  identityType = 'CUSTOMER' then
    select c.customer_name
      into identityName
      from CUSTOMER c
     where c.identity_id = identityId;
  end if;
  
  if tableName = 'ccy_account' then
	  select c.available_balance, c.unavailable_banlance, c.creditable_banlance
	    into new_available_balance,
	         new_unavailable_banlance,
	         new_creditable_banlance
	    from ccy_account c
	   where c.account_id = accountId
	     for update;
	  
	  update ccy_account c
	     set c.history_id           = his_id,
	         c.available_balance    = c.available_balance + amt,
	         c.unavailable_banlance = c.unavailable_banlance - amt
	   where c.account_id = accountId
	     and c.unavailable_banlance >= amt;
	
	  select c.available_balance, c.unavailable_banlance, c.creditable_banlance
	    into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from ccy_account c
     where c.account_id = accountId;
  
   
      	  save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
    select count(0) into transfer_num from ccy_account c where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg := '存储过程执行成功!';
      commit;
    end if;
  elsif tableName = 'merchant_credit_account' then
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from merchant_credit_account c
     where c.account_id = accountId
       for update;
    
    update merchant_credit_account c
       set c.history_id           = his_id,
           c.available_balance    = c.available_balance + amt,
           c.unavailable_banlance = c.unavailable_banlance - amt
     where c.account_id = accountId
       and c.unavailable_banlance >= amt;
  
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from merchant_credit_account c
     where c.account_id = accountId;
  
   
      save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
    select count(0) into transfer_num from merchant_credit_account c where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg := '存储过程执行成功!';
      commit;
    end if;
  elsif tableName = 'merchant_debit_account' then
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from merchant_debit_account c
     where c.account_id = accountId
       for update;
    
    update merchant_debit_account c
       set c.history_id           = his_id,
           c.available_balance    = c.available_balance + amt,
           c.unavailable_banlance = c.unavailable_banlance - amt
     where c.account_id = accountId
       and c.unavailable_banlance >= amt;
  
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from merchant_debit_account c
     where c.account_id = accountId;
  
   
      save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
    select count(0) into transfer_num from merchant_debit_account c where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg := '存储过程执行成功!';
      commit;
    end if;
  elsif tableName = 'system_debit_account0' then
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account0 c
     where c.account_id = accountId
       for update;
    
    update system_debit_account0 c
       set c.history_id           = his_id,
           c.available_balance    = c.available_balance + amt,
           c.unavailable_banlance = c.unavailable_banlance - amt
     where c.account_id = accountId
       and c.unavailable_banlance >= amt;
  
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account0 c
     where c.account_id = accountId;
  
   
      save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
    select count(0) into transfer_num from system_debit_account0 c where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg := '存储过程执行成功!';
      commit;
    end if;
  elsif tableName = 'system_debit_account1' then
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account1 c
     where c.account_id = accountId
       for update;
    
    update system_debit_account1 c
       set c.history_id           = his_id,
           c.available_balance    = c.available_balance + amt,
           c.unavailable_banlance = c.unavailable_banlance - amt
     where c.account_id = accountId
       and c.unavailable_banlance >= amt;
  
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account1 c
     where c.account_id = accountId;
  
   
      save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
    select count(0) into transfer_num from system_debit_account1 c where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg := '存储过程执行成功!';
      commit;
    end if;
  elsif tableName = 'system_debit_account2' then
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account2 c
     where c.account_id = accountId
       for update;
    
    update system_debit_account2 c
       set c.history_id           = his_id,
           c.available_balance    = c.available_balance + amt,
           c.unavailable_banlance = c.unavailable_banlance - amt
     where c.account_id = accountId
       and c.unavailable_banlance >= amt;
  
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account2 c
     where c.account_id = accountId;
  
   
      save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
    select count(0) into transfer_num from system_debit_account2 c where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg := '存储过程执行成功!';
      commit;
    end if;
  elsif tableName = 'system_debit_account3' then
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account3 c
     where c.account_id = accountId
       for update;
    
    update system_debit_account3 c
       set c.history_id           = his_id,
           c.available_balance    = c.available_balance + amt,
           c.unavailable_banlance = c.unavailable_banlance - amt
     where c.account_id = accountId
       and c.unavailable_banlance >= amt;
  
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account3 c
     where c.account_id = accountId;
  
   
      save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
    select count(0) into transfer_num from system_debit_account3 c where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg := '存储过程执行成功!';
      commit;
    end if;
  elsif tableName = 'system_debit_account4' then
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account4 c
     where c.account_id = accountId
       for update;
    
    update system_debit_account4 c
       set c.history_id           = his_id,
           c.available_balance    = c.available_balance + amt,
           c.unavailable_banlance = c.unavailable_banlance - amt
     where c.account_id = accountId
       and c.unavailable_banlance >= amt;
  
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account4 c
     where c.account_id = accountId;
  
   
      save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
    select count(0) into transfer_num from system_debit_account4 c where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg := '存储过程执行成功!';
      commit;
    end if;
  elsif tableName = 'system_debit_account5' then
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account5 c
     where c.account_id = accountId
       for update;
    
    update system_debit_account5 c
       set c.history_id           = his_id,
           c.available_balance    = c.available_balance + amt,
           c.unavailable_banlance = c.unavailable_banlance - amt
     where c.account_id = accountId
       and c.unavailable_banlance >= amt;
  
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account5 c
     where c.account_id = accountId;
  
   
      save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
    select count(0) into transfer_num from system_debit_account5 c where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg := '存储过程执行成功!';
      commit;
    end if;
  elsif tableName = 'system_debit_account6' then
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account6 c
     where c.account_id = accountId
       for update;
    
    update system_debit_account6 c
       set c.history_id           = his_id,
           c.available_balance    = c.available_balance + amt,
           c.unavailable_banlance = c.unavailable_banlance - amt
     where c.account_id = accountId
       and c.unavailable_banlance >= amt;
  
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account6 c
     where c.account_id = accountId;
  
   
      save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
    select count(0) into transfer_num from system_debit_account6 c where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg := '存储过程执行成功!';
      commit;
    end if;
  elsif tableName = 'system_debit_account7' then
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account7 c
     where c.account_id = accountId
       for update;
    
    update system_debit_account7 c
       set c.history_id           = his_id,
           c.available_balance    = c.available_balance + amt,
           c.unavailable_banlance = c.unavailable_banlance - amt
     where c.account_id = accountId
       and c.unavailable_banlance >= amt;
  
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account7 c
     where c.account_id = accountId;
  
   
      save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
    select count(0) into transfer_num from system_debit_account7 c where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg := '存储过程执行成功!';
      commit;
    end if;
  elsif tableName = 'system_debit_account8' then
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account8 c
     where c.account_id = accountId
       for update;
    
    update system_debit_account8 c
       set c.history_id           = his_id,
           c.available_balance    = c.available_balance + amt,
           c.unavailable_banlance = c.unavailable_banlance - amt
     where c.account_id = accountId
       and c.unavailable_banlance >= amt;
  
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account8 c
     where c.account_id = accountId;
  
   
      save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
    select count(0) into transfer_num from system_debit_account8 c where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg := '存储过程执行成功!';
      commit;
    end if;
  elsif tableName = 'system_debit_account9' then
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account9 c
     where c.account_id = accountId
       for update;
    
    update system_debit_account9 c
       set c.history_id           = his_id,
           c.available_balance    = c.available_balance + amt,
           c.unavailable_banlance = c.unavailable_banlance - amt
     where c.account_id = accountId
       and c.unavailable_banlance >= amt;
  
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from system_debit_account9 c
     where c.account_id = accountId;
  
   
      save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
    select count(0) into transfer_num from system_debit_account9 c where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg := '存储过程执行成功!';
      commit;
    end if;
  end if;

exception
  when others then
    rollback;
    transfer_flag := '9999';
    error_msg := sqlerrm;
    return;
end;
/

create or replace procedure ccyAc_creditableBalance_add(transactionId in number,
                                                        accountId     in number,
                                                        amt           in number,
                                                        historyType   in varchar2,
                                                        descStr       in varchar2,
                                                        tableName     in varchar2,
                                                        accountTypeId in number,
                                                        transfer_flag out number,
                                                        error_msg     out varchar2) as
  new_available_balance    number(18, 2);
  new_unavailable_banlance number(18, 2);
  new_creditable_banlance  number(18, 2);
  identityId               number;
  identityType             varchar2(64);
  identityName             varchar2(30);
  his_id                   number;
  transfer_num             number;
begin
  identityName  := '';
  transfer_flag := '0001';
  
  his_id := CCY_AC_BALANCE_ID_SEQ.nextval;
  
  select iar.identity_type, iar.identity_id
    into identityType, identityId
    from identity_account_role iar
   where iar.account_id = accountId
     and iar.relation = 'own';

  if identityType = 'MERCHANT' then
    select mer.merchant_name
      into identityName
      from merchant mer
     where mer.identity_id = identityId;
  elsif identityType = 'SP' then
    select s.sp_name
      into identityName
      from sp s
     where s.identity_id = identityId;
  elsif identityType = 'OPERATOR' then
    select o.operator_name
      into identityName
      from operator o
     where o.identity_id = identityId;
  elsif identityType = 'CUSTOMER' then
    select c.customer_name
      into identityName
      from CUSTOMER c
     where c.identity_id = identityId;
  end if;
  
  if tableName = 'merchant_debit_account' then
	  select c.available_balance, c.unavailable_banlance, c.creditable_banlance
	    into new_available_balance,
	         new_unavailable_banlance,
	         new_creditable_banlance
	    from merchant_debit_account c
	   where c.account_id = accountId
	     for update;
	  
	  update merchant_debit_account c
	     set c.history_id          = his_id,
	         c.creditable_banlance = c.creditable_banlance + amt
	   where c.account_id = accountId;
	   
	  select c.available_balance, c.unavailable_banlance, c.creditable_banlance
	    into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from merchant_debit_account c
     where c.account_id = accountId;
  
    save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
    select count(0)
      into transfer_num
      from merchant_debit_account c
     where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg     := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg     := '存储过程执行成功!';
      commit;
    end if;
  elsif tableName = 'merchant_credit_account' then
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from merchant_credit_account c
     where c.account_id = accountId
       for update;
    
    update merchant_credit_account c
       set c.history_id          = his_id,
           c.creditable_banlance = c.creditable_banlance + amt
     where c.account_id = accountId;
     
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from merchant_credit_account c
     where c.account_id = accountId;
  
    save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
    select count(0)
      into transfer_num
      from merchant_credit_account c
     where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg     := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg     := '存储过程执行成功!';
      commit;
    end if;
  end if;

exception
  when others then
    rollback;
    transfer_flag := '9999';
    error_msg     := sqlerrm;
    return;
end;
/

create or replace procedure ccyAc_creditableBalance_sub(transactionId in number,
                                                        accountId     in number,
                                                        amt           in number,
                                                        historyType   in varchar2,
                                                        descStr       in varchar2,
                                                        tableName     in varchar2,
                                                        accountTypeId in number,
                                                        transfer_flag out number,
                                                        error_msg     out varchar2) as
  new_available_balance    number(18, 2);
  new_unavailable_banlance number(18, 2);
  new_creditable_banlance  number(18, 2);
  identityId               number;
  identityType             varchar2(64);
  identityName             varchar2(30);
  his_id                   number;
  transfer_num             number;
begin
  identityName  := '';
  transfer_flag := '0001';
  
  his_id := CCY_AC_BALANCE_ID_SEQ.nextval;
  
  select iar.identity_type, iar.identity_id
    into identityType, identityId
    from identity_account_role iar
   where iar.account_id = accountId
     and iar.relation = 'own';

  if identityType = 'MERCHANT' then
    select mer.merchant_name
      into identityName
      from merchant mer
     where mer.identity_id = identityId;
  elsif identityType = 'SP' then
    select s.sp_name
      into identityName
      from sp s
     where s.identity_id = identityId;
  elsif identityType = 'OPERATOR' then
    select o.operator_name
      into identityName
      from operator o
     where o.identity_id = identityId;
  elsif identityType = 'CUSTOMER' then
    select c.customer_name
      into identityName
      from CUSTOMER c
     where c.identity_id = identityId;
  end if;
  
  if tableName = 'merchant_debit_account' then
		select c.available_balance, c.unavailable_banlance, c.creditable_banlance
	    into new_available_balance,
	         new_unavailable_banlance,
	         new_creditable_banlance
	    from merchant_debit_account c
	   where c.account_id = accountId
	     for update;
	
	  update merchant_debit_account c
	     set c.history_id          = his_id,
	         c.creditable_banlance = c.creditable_banlance - amt
	   where c.account_id = accountId
	     and c.available_balance + c.creditable_banlance - amt >= 0;
	
	  select c.available_balance, c.unavailable_banlance, c.creditable_banlance
	    into new_available_balance,
	         new_unavailable_banlance,
	         new_creditable_banlance
	    from merchant_debit_account c
	   where c.account_id = accountId;
	
	  save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
    select count(0)
      into transfer_num
      from merchant_debit_account c
     where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg     := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg     := '存储过程执行成功!';
      commit;
    end if;
  elsif tableName = 'merchant_credit_account' then
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from merchant_credit_account c
     where c.account_id = accountId
       for update;
  
    update merchant_credit_account c
       set c.history_id          = his_id,
           c.creditable_banlance = c.creditable_banlance - amt
     where c.account_id = accountId
       and c.available_balance + c.creditable_banlance - amt >= 0;
  
    select c.available_balance, c.unavailable_banlance, c.creditable_banlance
      into new_available_balance,
           new_unavailable_banlance,
           new_creditable_banlance
      from merchant_credit_account c
     where c.account_id = accountId;
  
    save_ccyAcBalance_History(transactionId,
                              accountId,
                              new_available_balance,
                              new_unavailable_banlance,
                              new_creditable_banlance,
                              sysdate,
                              historyType,
                              descStr,
                              identityName,
                              his_id,
                              amt,
                              accountTypeId);
    select count(0)
      into transfer_num
      from merchant_credit_account c
     where c.history_id = his_id;
    if transfer_num <= 0 then
      --执行错误
      transfer_flag := '9999';
      error_msg     := '存储过程执行失败!';
      rollback;
    else
      --正常成功
      transfer_flag := '0000';
      error_msg     := '存储过程执行成功!';
      commit;
    end if;
  end if;

exception
  when others then
    rollback;
    transfer_flag := '9999';
    error_msg     := sqlerrm;
    return;
end;
/

