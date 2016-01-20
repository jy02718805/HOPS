package com.yuecheng.hops.common;

public class PermissionConstant
{
    public class Customer
    {
        // 进入添加用户界面
        public static final String CUSTOMER_ADD_SHOW = "customer:add_show";

        // 添加用户操作
        public static final String CUSTOMER_ADD_EXECUTE = "customer:add_execute";

        // 查看用户详情
        public static final String CUSTOMER_VIEW = "customer:view";

        // 进入编辑用户信息界面
        public static final String CUSTOMER_EDIT_SHOW = "customer:edit_show";

        // 编辑用户信息操作
        public static final String CUSTOMER_EDIT_EXECUTE = "customer:edit_execute";

        // 删除用户信息
        public static final String CUSTOMER_DELETE = "customer:delete";

        // 进入用户分配角色界面
        public static final String CUSTOMER_ADDROLES_SHOW = "customer:addroles_show";

        // 执行用户分配角色操作
        public static final String CUSTOMER_ADDROLES_EXECUTE = "customer:addroles_execute";
    }

    public class Product
    {
        // 进入添加产品界面
        public static final String PRODUCT_ADD_SHOW = "product:add_show";

        // 添加产品操作
        public static final String PRODUCT_ADD_EXECUTE = "product:add_execute";

        // 查看产品详情
        public static final String PRODUCT_VIEW = "product:view";

        // 进入编辑产品信息界面
        public static final String PRODUCT_EDIT_SHOW = "product:edit_show";

        // 编辑产品信息操作
        public static final String PRODUCT_EDIT_EXECUTE = "product:edit_execute";

        // 删除产品信息
        public static final String PRODUCT_DELETE = "product:delete";

        // 进入编辑用户信息界面
        public static final String PRODUCT_UPDATE_STATUS_SHOW = "product:updateStatus";
    }
}
