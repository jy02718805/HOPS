package com.yuecheng.hops.security.repository;

public class OracleSql
{
    public class SecurityCredentialRule
    {
        public static final String PAGE_SQL = "select * from(select a.*,rownum rn from ("
                                              + "select t.* from security_credential_rule t where 1=1 and t.Status <> 2"
                                              + "and (t.security_rule_name like '%'||:ruleName||'%' or :ruleName is null) "
                                              + "and (t.LETTER = :letter or :letter is null) "
                                              + "and (t.FIGURE = :figure or :figure is null) "
                                              + "and (t.SPECIAL_CHARACTER = :specialCharacter or :specialCharacter is null)"
                                              + "and (t.STATUS = :status or :status is null)"
                                              + "order by t.SECURITY_RULE_ID desc) a where rownum <= :pageNumber*:pageSize)where rn >:pageNumber * :pageSize - :pageSize";

        public static final String All_SQL = "select t.* from security_credential_rule t where 1=1 and t.Status <> 2"
                                             + "and (t.security_rule_name like '%'||:ruleName||'%' or :ruleName is null) "
                                             + "and (t.LETTER = :letter or :letter is null) "
                                             + "and (t.FIGURE = :figure or :figure is null) "
                                             + "and (t.SPECIAL_CHARACTER = :specialCharacter or :specialCharacter is null)"
                                             + "and (t.STATUS = :status or :status is null)"
                                             + "order by t.SECURITY_RULE_ID desc";
    }

    public class SecurityCredentialType
    {
        public static final String PAGE_SQL = "select * from(select a.*,rownum rn from ("
                                              + "select t.* from security_credential_type t where 1=1 and t.Status <> 2"
                                              + "and (t.security_type_name like '%'||:typeName||'%' or :typeName is null) "
                                              + "and (t.model_type = :modelType or :modelType is null) "
                                              + "and (t.ENCRYPT_TYPE = :encryptType or :encryptType is null) "
                                              + "and (t.SECURITY_RULE_ID = :secutityRuleId or :secutityRuleId is null)"
                                              + "and (t.STATUS = :status or :status is null)"
                                              + "order by t.SECURITY_TYPE_ID desc) a where rownum <= :pageNumber*:pageSize)where rn >:pageNumber * :pageSize - :pageSize";

        public static final String All_SQL = "select t.* from security_credential_type t where 1=1 and t.Status <> 2"
                                             + "and (t.security_type_name like '%'||:typeName||'%' or :typeName is null) "
                                             + "and (t.model_type = :modelType or :modelType is null) "
                                             + "and (t.ENCRYPT_TYPE = :encryptType or :encryptType is null) "
                                             + "and (t.SECURITY_RULE_ID = :secutityRuleId or :secutityRuleId is null)"
                                             + "and (t.STATUS = :status or :status is null)"
                                             + "order by t.SECURITY_TYPE_ID desc";
    }
}
