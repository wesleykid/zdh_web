package com.zyc.zdh.controller;

import com.alibaba.fastjson.JSON;
import com.zyc.zdh.dao.ApprovalAuditorMapper;
import com.zyc.zdh.dao.ApprovalConfigMapper;
import com.zyc.zdh.dao.ApprovalEventMapper;
import com.zyc.zdh.entity.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Controller
public class ZdhApprovalController extends BaseController{

    public Logger logger= LoggerFactory.getLogger(this.getClass());
    @Autowired
    ApprovalConfigMapper approvalConfigMapper;

    @Autowired
    ApprovalAuditorMapper approvalAuditorMapper;

    @Autowired
    ApprovalEventMapper approvalEventMapper;

    /**
     * 审批节点首页
     * @return
     */
    @RequestMapping("/approval_config_index")
    public String approve_config_index() {

        return "admin/approval_config_index";
    }


    @RequestMapping(value = "/approval_config_list", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String approve_config_list(String approval_context) {
        try{
            Example example=new Example(ApprovalConfigInfo.class);
            Example.Criteria criteria = example.createCriteria();
            if(!StringUtils.isEmpty(approval_context)){
                criteria.orLike("code", getLikeCondition(approval_context));
                criteria.orLike("code_name", getLikeCondition(approval_context));
                criteria.orLike("id", getLikeCondition(approval_context));
            }
            List<ApprovalConfigInfo> approvalConfigInfos=approvalConfigMapper.selectByExample(example);
            return JSON.toJSONString(approvalConfigInfos);
        }catch (Exception e){
             logger.error("类:"+Thread.currentThread().getStackTrace()[1].getClassName()+" 函数:"+Thread.currentThread().getStackTrace()[1].getMethodName()+ " 异常: {}", e);
            return ReturnInfo.createInfo(RETURN_CODE.FAIL.getCode(), "查询失败", e);
        }
    }


    @RequestMapping("/approval_config_add_index")
    public String approval_config_add_index() {

        return "admin/approval_config_add_index";
    }

    @RequestMapping(value = "/approval_config_detail", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String approval_config_detail(String id) {
        try{
            ApprovalConfigInfo approvalConfigInfo=approvalConfigMapper.selectByPrimaryKey(id);
            return ReturnInfo.createInfo(RETURN_CODE.SUCCESS.getCode(), "查询成功", approvalConfigInfo);
        }catch (Exception e){
             logger.error("类:"+Thread.currentThread().getStackTrace()[1].getClassName()+" 函数:"+Thread.currentThread().getStackTrace()[1].getMethodName()+ " 异常: {}", e);
            return ReturnInfo.createInfo(RETURN_CODE.FAIL.getCode(), "查询失败", e);
        }
    }


    @RequestMapping(value = "/approval_config_add", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String approval_config_add(ApprovalConfigInfo approvalConfigInfo) {
        try{
            List<ApprovalConfigInfo> approvalConfigInfos=approvalConfigMapper.select(approvalConfigInfo);
            if(approvalConfigInfos!=null && approvalConfigInfos.size()>0){
                throw new Exception("审批节点以存在");
            }
            approvalConfigInfo.setEmployee_id(getUser().getId());
            approvalConfigInfo.setCreate_time(new Timestamp(new Date().getTime()));
            approvalConfigMapper.insert(approvalConfigInfo);
            return ReturnInfo.createInfo(RETURN_CODE.SUCCESS.getCode(), "新增成功", null);
        }catch (Exception e){
             logger.error("类:"+Thread.currentThread().getStackTrace()[1].getClassName()+" 函数:"+Thread.currentThread().getStackTrace()[1].getMethodName()+ " 异常: {}", e);
            return ReturnInfo.createInfo(RETURN_CODE.FAIL.getCode(), "新增失败", e);
        }
    }

    @RequestMapping(value = "/approval_config_update", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String approval_config_update(ApprovalConfigInfo approvalConfigInfo) {
        try{
            ApprovalConfigInfo apc=approvalConfigMapper.selectByPrimaryKey(approvalConfigInfo.getId());
            BeanUtils.copyProperties(apc, approvalConfigInfo);
            approvalConfigInfo.setEmployee_id(getUser().getId());
            approvalConfigMapper.updateByPrimaryKey(approvalConfigInfo);
            return ReturnInfo.createInfo(RETURN_CODE.SUCCESS.getCode(), "更新成功", null);
        }catch (Exception e){
             logger.error("类:"+Thread.currentThread().getStackTrace()[1].getClassName()+" 函数:"+Thread.currentThread().getStackTrace()[1].getMethodName()+ " 异常: {}", e);
            return ReturnInfo.createInfo(RETURN_CODE.FAIL.getCode(), "更新失败", e);
        }
    }

    @RequestMapping("/approval_auditor_index")
    public String approval_auditor_index() {

        return "admin/approval_auditor_index";
    }

    @RequestMapping(value = "/approval_auditor_list", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String approval_auditor_list(String approval_context, String auditor_id) {
        try{
            if(!StringUtils.isEmpty(approval_context)){
                approval_context = getLikeCondition(approval_context);
            }
            List<ApprovalAuditorInfo> approvalAuditorInfos=approvalAuditorMapper.selectByContext(approval_context, auditor_id);
            return JSON.toJSONString(approvalAuditorInfos);
        }catch (Exception e){
             logger.error("类:"+Thread.currentThread().getStackTrace()[1].getClassName()+" 函数:"+Thread.currentThread().getStackTrace()[1].getMethodName()+ " 异常: {}", e);
            return ReturnInfo.createInfo(RETURN_CODE.FAIL.getCode(), "查询失败", e);
        }
    }

    @RequestMapping("/approval_auditor_add_index")
    public String approval_auditor_add_index() {

        return "admin/approval_auditor_add_index";
    }


    @RequestMapping(value = "/approval_auditor_add", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String approval_auditor_add(ApprovalAuditorInfo approvalAuditorInfo) {
        try{
            List<ApprovalAuditorInfo> approvalAuditorInfos=approvalAuditorMapper.select(approvalAuditorInfo);
            if(approvalAuditorInfos!=null && approvalAuditorInfos.size()>0){
                throw new Exception("审批节点以存在");
            }
            approvalAuditorInfo.setCreate_time(new Timestamp(new Date().getTime()));
            approvalAuditorMapper.insert(approvalAuditorInfo);
            return ReturnInfo.createInfo(RETURN_CODE.SUCCESS.getCode(), "新增成功", null);
        }catch (Exception e){
             logger.error("类:"+Thread.currentThread().getStackTrace()[1].getClassName()+" 函数:"+Thread.currentThread().getStackTrace()[1].getMethodName()+ " 异常: {}", e);
            return ReturnInfo.createInfo(RETURN_CODE.FAIL.getCode(), "新增失败", e);
        }
    }

    @RequestMapping(value = "/approval_auditor_delete", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    @Transactional(propagation= Propagation.NESTED)
    public String approval_auditor_delete(String[] ids) {
        try{
            approvalAuditorMapper.deleteByPrimaryKeys(ids);
            return ReturnInfo.createInfo(RETURN_CODE.SUCCESS.getCode(), "删除成功", null);
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
             logger.error("类:"+Thread.currentThread().getStackTrace()[1].getClassName()+" 函数:"+Thread.currentThread().getStackTrace()[1].getMethodName()+ " 异常: {}", e);
            return ReturnInfo.createInfo(RETURN_CODE.FAIL.getCode(), "删除失败", e);
        }
    }

    @RequestMapping(value = "/approval_auditor_update", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String approval_auditor_update(ApprovalAuditorInfo approvalAuditorInfo) {
        try{
            approvalAuditorMapper.updateByPrimaryKey(approvalAuditorInfo);
            return ReturnInfo.createInfo(RETURN_CODE.SUCCESS.getCode(), "更新成功", null);
        }catch (Exception e){
             logger.error("类:"+Thread.currentThread().getStackTrace()[1].getClassName()+" 函数:"+Thread.currentThread().getStackTrace()[1].getMethodName()+ " 异常: {}", e);
            return ReturnInfo.createInfo(RETURN_CODE.FAIL.getCode(), "更新失败", e);
        }
    }

    @RequestMapping(value = "/approval_auditor_detail", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String approval_auditor_detail(String id) {
        try{
            ApprovalAuditorInfo approvalAuditorInfo=approvalAuditorMapper.selectById(id);
            return ReturnInfo.createInfo(RETURN_CODE.SUCCESS.getCode(), "查询成功", approvalAuditorInfo);
        }catch (Exception e){
             logger.error("类:"+Thread.currentThread().getStackTrace()[1].getClassName()+" 函数:"+Thread.currentThread().getStackTrace()[1].getMethodName()+ " 异常: {}", e);
            return ReturnInfo.createInfo(RETURN_CODE.FAIL.getCode(), "查询失败", e);
        }
    }

    @RequestMapping("/approval_event_index")
    public String approval_event_index() {

        return "admin/approval_event_index";
    }

    @RequestMapping("/approval_event_add_index")
    public String approval_event_add_index() {

        return "admin/approval_event_add_index";
    }


    @RequestMapping(value = "/approval_event_add", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String approval_event_add(ApprovalEventInfo approvalEventInfo) {
        try{
            List<ApprovalEventInfo> approvalEventInfos=approvalEventMapper.select(approvalEventInfo);
            if(approvalEventInfos!=null && approvalEventInfos.size()>0){
                throw new Exception("审批事件已存在");
            }
            approvalEventInfo.setCreate_time(new Timestamp(new Date().getTime()));
            approvalEventInfo.setUpdate_time(new Timestamp(new Date().getTime()));
            approvalEventMapper.insert(approvalEventInfo);
            return ReturnInfo.createInfo(RETURN_CODE.SUCCESS.getCode(), "新增成功", null);
        }catch (Exception e){
             logger.error("类:"+Thread.currentThread().getStackTrace()[1].getClassName()+" 函数:"+Thread.currentThread().getStackTrace()[1].getMethodName()+ " 异常: {}", e);
            return ReturnInfo.createInfo(RETURN_CODE.FAIL.getCode(), "新增失败", e);
        }
    }

    @RequestMapping(value = "/approval_event_detail", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String approval_event_detail(String id) {
        try{
            ApprovalEventInfo approvalEventInfo=approvalEventMapper.selectById(id);
            return ReturnInfo.createInfo(RETURN_CODE.SUCCESS.getCode(), "查询成功", approvalEventInfo);
        }catch (Exception e){
             logger.error("类:"+Thread.currentThread().getStackTrace()[1].getClassName()+" 函数:"+Thread.currentThread().getStackTrace()[1].getMethodName()+ " 异常: {}", e);
            return ReturnInfo.createInfo(RETURN_CODE.FAIL.getCode(), "查询失败", e);
        }
    }

    @RequestMapping(value = "/approval_event_list", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String approval_event_list(String event_context) {
        try{
            if(!StringUtils.isEmpty(event_context)){
                event_context = getLikeCondition(event_context);
            }
            List<ApprovalEventInfo> approvalEventInfos=approvalEventMapper.selectByContext(event_context);
            return JSON.toJSONString(approvalEventInfos);
        }catch (Exception e){
             logger.error("类:"+Thread.currentThread().getStackTrace()[1].getClassName()+" 函数:"+Thread.currentThread().getStackTrace()[1].getMethodName()+ " 异常: {}", e);
            return ReturnInfo.createInfo(RETURN_CODE.FAIL.getCode(), "查询失败", e);
        }
    }

    @RequestMapping(value = "/approval_event_update", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String approval_event_update(ApprovalEventInfo approvalEventInfo) {
        try{
            ApprovalEventInfo old = approvalEventMapper.selectByPrimaryKey(approvalEventInfo.getId());
            approvalEventInfo.setCreate_time(old.getCreate_time());
            approvalEventInfo.setUpdate_time(new Timestamp(new Date().getTime()));
            approvalEventMapper.updateByPrimaryKey(approvalEventInfo);
            return ReturnInfo.createInfo(RETURN_CODE.SUCCESS.getCode(), "更新成功", approvalEventInfo);
        }catch (Exception e){
             logger.error("类:"+Thread.currentThread().getStackTrace()[1].getClassName()+" 函数:"+Thread.currentThread().getStackTrace()[1].getMethodName()+ " 异常: {}", e);
            return ReturnInfo.createInfo(RETURN_CODE.FAIL.getCode(), "更新失败", e);
        }
    }

}
