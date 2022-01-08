package com.zyc.zdh.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zyc.zdh.dao.*;
import com.zyc.zdh.entity.*;
import com.zyc.zdh.job.JobCommon2;
import com.zyc.zdh.job.SnowflakeIdWorker;
import com.zyc.zdh.util.Const;
import com.zyc.zdh.util.HttpUtil;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.*;

@Controller
public class ZdhFlinkController extends BaseController{

    public Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ZdhHaInfoMapper zdhHaInfoMapper;
    @Autowired
    EtlTaskUpdateLogsMapper etlTaskUpdateLogsMapper;
    @Autowired
    EtlTaskFlinkMapper etlTaskFlinkMapper;
    @Autowired
    MetaDatabaseMapper metaDatabaseMapper;

    @RequestMapping("/etl_task_flink_index")
    public String etl_task_flink_index() {

        return "etl/etl_task_flink_index";
    }

    @RequestMapping("/etl_task_flink_add_index")
    public String etl_task_flink_add_index() {

        return "etl/etl_task_flink_add_index";
    }

    /**
     * 模糊查询Sql任务
     * @param sql_context sql任务说明
     * @param id sql任务id
     * @return
     */
    @RequestMapping(value = "/etl_task_flink_list", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String etl_task_flink_list(String sql_context, String id) {

        List<EtlTaskFlinkInfo> sqlTaskInfos = new ArrayList<>();

        sqlTaskInfos = etlTaskFlinkMapper.selectByParams(getUser().getId(), sql_context, id);

        return JSON.toJSONString(sqlTaskInfos);
    }

    /**
     * 批量删除sql任务
     * @param ids
     * @return
     */
    @RequestMapping("/etl_task_flink_delete")
    @ResponseBody
    @Transactional
    public String etl_task_flink_delete(String[] ids) {
        try{
            etlTaskFlinkMapper.deleteBatchById(ids, new Timestamp(new Date().getTime()));
            return ReturnInfo.createInfo(RETURN_CODE.SUCCESS.getCode(),"删除成功", null);
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error(e.getMessage(),e.getCause());
            return ReturnInfo.createInfo(RETURN_CODE.FAIL.getCode(),"删除失败", e);
        }
    }

    /**
     * 新增sql 任务
     * @param etlTaskFlinkInfo
     * @return
     */
    @RequestMapping("/etl_task_flink_add")
    @ResponseBody
    @Transactional
    public String etl_task_flink_add(EtlTaskFlinkInfo etlTaskFlinkInfo) {
        //String json_str=JSON.toJSONString(request.getParameterMap());
        try{
            String owner = getUser().getId();
            etlTaskFlinkInfo.setOwner(owner);
            etlTaskFlinkInfo.setId(SnowflakeIdWorker.getInstance().nextId() + "");
            etlTaskFlinkInfo.setCreate_time(new Timestamp(new Date().getTime()));
            etlTaskFlinkInfo.setUpdate_time(new Timestamp(new Date().getTime()));
            etlTaskFlinkInfo.setIs_delete(Const.NOT_DELETE);

            debugInfo(etlTaskFlinkInfo);
            etlTaskFlinkMapper.insert(etlTaskFlinkInfo);


            if (etlTaskFlinkInfo.getUpdate_context() != null && !etlTaskFlinkInfo.getUpdate_context().equals("")) {
                //插入更新日志表
                EtlTaskUpdateLogs etlTaskUpdateLogs = new EtlTaskUpdateLogs();
                etlTaskUpdateLogs.setId(etlTaskFlinkInfo.getId());
                etlTaskUpdateLogs.setUpdate_context(etlTaskFlinkInfo.getUpdate_context());
                etlTaskUpdateLogs.setUpdate_time(new Timestamp(new Date().getTime()));
                etlTaskUpdateLogs.setOwner(getUser().getId());
                etlTaskUpdateLogsMapper.insert(etlTaskUpdateLogs);
            }
            return ReturnInfo.createInfo(RETURN_CODE.SUCCESS.getCode(),"新增成功", null);
        }catch (Exception e){
            logger.error(e.getMessage(),e.getCause());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ReturnInfo.createInfo(RETURN_CODE.FAIL.getCode(),"新增失败", e);
        }
    }

    /**
     * 更新sql 任务
     * @param etlTaskFlinkInfo
     * @return
     */
    @RequestMapping("/etl_task_flink_update")
    @ResponseBody
    @Transactional
    public String etl_task_flink_update(EtlTaskFlinkInfo etlTaskFlinkInfo) {
        //String json_str=JSON.toJSONString(request.getParameterMap());
        try{
            String owner = getUser().getId();
            etlTaskFlinkInfo.setOwner(owner);
            etlTaskFlinkInfo.setUpdate_time(new Timestamp(new Date().getTime()));
            etlTaskFlinkInfo.setIs_delete(Const.NOT_DELETE);
            debugInfo(etlTaskFlinkInfo);

            etlTaskFlinkMapper.updateByPrimaryKey(etlTaskFlinkInfo);

            EtlTaskFlinkInfo sti = etlTaskFlinkMapper.selectByPrimaryKey(etlTaskFlinkInfo.getId());

            if (etlTaskFlinkInfo.getUpdate_context() != null && !etlTaskFlinkInfo.getUpdate_context().equals("")
                    && !etlTaskFlinkInfo.getUpdate_context().equals(sti.getUpdate_context())) {
                //插入更新日志表
                EtlTaskUpdateLogs etlTaskUpdateLogs = new EtlTaskUpdateLogs();
                etlTaskUpdateLogs.setId(etlTaskFlinkInfo.getId());
                etlTaskUpdateLogs.setUpdate_context(etlTaskFlinkInfo.getUpdate_context());
                etlTaskUpdateLogs.setUpdate_time(new Timestamp(new Date().getTime()));
                etlTaskUpdateLogs.setOwner(getUser().getId());
                etlTaskUpdateLogsMapper.insert(etlTaskUpdateLogs);
            }
            return ReturnInfo.createInfo(RETURN_CODE.SUCCESS.getCode(),"更新成功", null);
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error(e.getMessage(),e.getCause());
            return ReturnInfo.createInfo(RETURN_CODE.FAIL.getCode(),"更新失败", e);
        }
    }



    private void debugInfo(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0, len = fields.length; i < len; i++) {
            // 对于每个属性，获取属性名
            String varName = fields[i].getName();
            try {
                // 获取原来的访问控制权限
                boolean accessFlag = fields[i].isAccessible();
                // 修改访问控制权限
                fields[i].setAccessible(true);
                // 获取在对象f中属性fields[i]对应的对象中的变量
                Object o;
                try {
                    o = fields[i].get(obj);
                    System.err.println("传入的对象中包含一个如下的变量：" + varName + " = " + o);
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    String error = "类:"+Thread.currentThread().getStackTrace()[1].getClassName()+" 函数:"+Thread.currentThread().getStackTrace()[1].getMethodName();
                    logger.error(error, e.getCause());
                }
                // 恢复访问控制权限
                fields[i].setAccessible(accessFlag);
            } catch (IllegalArgumentException e) {
                 logger.error("类:"+Thread.currentThread().getStackTrace()[1].getClassName()+" 函数:"+Thread.currentThread().getStackTrace()[1].getMethodName(), e.getCause());
            }
        }
    }

}
