package com.zyc.notscan.base;

import com.zyc.notscan.BaseMapper;

/**
 * ClassName: BaseApprovalAuditorMapper
 * @author zyc-admin
 * @date 2017年12月26日  
 * @Description: TODO  
 */
public interface BaseApprovalAuditorMapper<T> extends BaseMapper<T> {
    @Override
    default String getTable(){
        return "approval_auditor_info";
    }
}
