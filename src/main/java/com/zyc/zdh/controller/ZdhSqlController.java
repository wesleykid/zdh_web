package com.zyc.zdh.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zyc.zdh.dao.EtlTaskUpdateLogsMapper;
import com.zyc.zdh.dao.MetaDatabaseMapper;
import com.zyc.zdh.dao.SqlTaskMapper;
import com.zyc.zdh.dao.ZdhHaInfoMapper;
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
public class ZdhSqlController extends BaseController {

    public Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ZdhHaInfoMapper zdhHaInfoMapper;
    @Autowired
    EtlTaskUpdateLogsMapper etlTaskUpdateLogsMapper;
    @Autowired
    SqlTaskMapper sqlTaskMapper;
    @Autowired
    MetaDatabaseMapper metaDatabaseMapper;

    @RequestMapping("/sql_task_index")
    public String etl_task_ssh_index() {

        return "etl/sql_task_index";
    }

    @RequestMapping("/sql_task_add_index")
    public String sql_task_add_index() {

        return "etl/sql_task_add_index";
    }


    /**
     * 模糊查询Sql任务
     *
     * @param sql_context sql任务说明
     * @param id          sql任务id
     * @return
     */
    @RequestMapping(value = "/sql_task_list", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String sql_task_list(String sql_context, String id) {

        List<SqlTaskInfo> sqlTaskInfos = new ArrayList<>();

        sqlTaskInfos = sqlTaskMapper.selectByParams(getUser().getId(), sql_context, id);

        return JSON.toJSONString(sqlTaskInfos);
    }

    /**
     * 批量删除sql任务
     *
     * @param ids
     * @return
     */
    @RequestMapping("/sql_task_delete")
    @ResponseBody
    @Transactional
    public String sql_task_delete(String[] ids) {
        try {
            sqlTaskMapper.deleteBatchById(ids, new Timestamp(new Date().getTime()));

            return ReturnInfo.createInfo(RETURN_CODE.SUCCESS.getCode(), "删除成功", null);
        } catch (Exception e) {
            logger.error("删除异常:{}", e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ReturnInfo.createInfo(RETURN_CODE.FAIL.getCode(), "删除失败", e);
        }
    }

    /**
     * 新增sql 任务
     *
     * @param sqlTaskInfo
     * @return
     */
    @RequestMapping("/sql_task_add")
    @ResponseBody
    @Transactional
    public String sql_task_add(SqlTaskInfo sqlTaskInfo) {
        //String json_str=JSON.toJSONString(request.getParameterMap());
        try {
            String owner = getUser().getId();
            sqlTaskInfo.setOwner(owner);
            debugInfo(sqlTaskInfo);

            sqlTaskInfo.setId(SnowflakeIdWorker.getInstance().nextId() + "");
            sqlTaskInfo.setCreate_time(new Timestamp(new Date().getTime()));
            sqlTaskInfo.setUpdate_time(new Timestamp(new Date().getTime()));
            sqlTaskInfo.setIs_delete(Const.NOT_DELETE);

            sqlTaskMapper.insert(sqlTaskInfo);


            if (sqlTaskInfo.getUpdate_context() != null && !sqlTaskInfo.getUpdate_context().equals("")) {
                //插入更新日志表
                EtlTaskUpdateLogs etlTaskUpdateLogs = new EtlTaskUpdateLogs();
                etlTaskUpdateLogs.setId(sqlTaskInfo.getId());
                etlTaskUpdateLogs.setUpdate_context(sqlTaskInfo.getUpdate_context());
                etlTaskUpdateLogs.setUpdate_time(new Timestamp(new Date().getTime()));
                etlTaskUpdateLogs.setOwner(getUser().getId());
                etlTaskUpdateLogsMapper.insert(etlTaskUpdateLogs);
            }
            return ReturnInfo.createInfo(RETURN_CODE.SUCCESS.getCode(), "新增成功", null);
        } catch (Exception e) {
            String error = "类:" + Thread.currentThread().getStackTrace()[1].getClassName() + " 函数:" + Thread.currentThread().getStackTrace()[1].getMethodName() + " 异常:" + e.getMessage() + ", 异常详情:{}";
            logger.error(error, e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ReturnInfo.createInfo(RETURN_CODE.FAIL.getCode(), "新增失败", e);
        }
    }

    /**
     * 更新sql 任务
     *
     * @param sqlTaskInfo
     * @return
     */
    @RequestMapping("/sql_task_update")
    @ResponseBody
    public String sql_task_update(SqlTaskInfo sqlTaskInfo) {
        //String json_str=JSON.toJSONString(request.getParameterMap());
        try {
            String owner = getUser().getId();
            sqlTaskInfo.setOwner(owner);
            sqlTaskInfo.setUpdate_time(new Timestamp(new Date().getTime()));
            sqlTaskInfo.setIs_delete(Const.NOT_DELETE);
            debugInfo(sqlTaskInfo);

            sqlTaskMapper.updateByPrimaryKey(sqlTaskInfo);

            SqlTaskInfo sti = sqlTaskMapper.selectByPrimaryKey(sqlTaskInfo.getId());

            if (sqlTaskInfo.getUpdate_context() != null && !sqlTaskInfo.getUpdate_context().equals("")
                    && !sqlTaskInfo.getUpdate_context().equals(sti.getUpdate_context())) {
                //插入更新日志表
                EtlTaskUpdateLogs etlTaskUpdateLogs = new EtlTaskUpdateLogs();
                etlTaskUpdateLogs.setId(sqlTaskInfo.getId());
                etlTaskUpdateLogs.setUpdate_context(sqlTaskInfo.getUpdate_context());
                etlTaskUpdateLogs.setUpdate_time(new Timestamp(new Date().getTime()));
                etlTaskUpdateLogs.setOwner(getUser().getId());
                etlTaskUpdateLogsMapper.insert(etlTaskUpdateLogs);
            }
            return ReturnInfo.createInfo(RETURN_CODE.SUCCESS.getCode(), "更新成功", null);
        } catch (Exception e) {
            String error = "类:" + Thread.currentThread().getStackTrace()[1].getClassName() + " 函数:" + Thread.currentThread().getStackTrace()[1].getMethodName() + " 异常:" + e.getMessage() + ", 异常详情:{}";
            logger.error(error, e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ReturnInfo.createInfo(RETURN_CODE.FAIL.getCode(), "更新失败", e);
        }
    }


    /**
     * 加载元数据信息
     *
     * @return
     */
    @RequestMapping(value = "/load_meta_databases", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String load_meta_databases() {
        JSONObject js = new JSONObject();
        if (!SecurityUtils.getSubject().isPermitted("function:load_meta_databases()")) {
            js.put("data", "您没有权限访问,请联系管理员添加权限");
            return js.toJSONString();
        }
        String url = JobCommon2.getZdhUrl(zdhHaInfoMapper, "").getZdh_url();
        try {
            String databases = HttpUtil.postJSON(url + "/show_databases", new JSONObject().toJSONString());

            List<meta_database_info> meta_database_infos = new ArrayList<meta_database_info>();

            System.out.println("databases:" + databases);
            JSONArray jary = JSON.parseArray(databases);
            for (Object o : jary) {
                JSONObject jo = new JSONObject();
                String tableNames = HttpUtil.postJSON(url + "/show_tables", "{\"databaseName\":\"" + o.toString() + "\"}");
                JSONArray tableAry = JSON.parseArray(tableNames);
                meta_database_info meta_database_info_d = new meta_database_info();
                meta_database_info_d.setOwner(getUser().getId());
                metaDatabaseMapper.delete(meta_database_info_d);
                if (tableAry.isEmpty()) {
                    meta_database_info meta_database_info = new meta_database_info();
                    meta_database_info.setDb_name(o.toString());
                    meta_database_info.setTb_name("");
                    meta_database_info.setOwner(getUser().getId());
                    meta_database_info.setCreate_time(new Timestamp(new Date().getTime()));
                    metaDatabaseMapper.insert(meta_database_info);
                }

                for (Object t : tableAry) {
                    meta_database_info meta_database_info = new meta_database_info();
                    meta_database_info.setDb_name(o.toString());
                    meta_database_info.setTb_name(t.toString());
                    meta_database_info.setOwner(getUser().getId());
                    meta_database_info.setCreate_time(new Timestamp(new Date().getTime()));
                    meta_database_infos.add(meta_database_info);
                    metaDatabaseMapper.insert(meta_database_info);
                }
            }
            return ReturnInfo.createInfo(RETURN_CODE.SUCCESS.getCode(), "同步成功", null);

        } catch (Exception e) {
            String error = "类:" + Thread.currentThread().getStackTrace()[1].getClassName() + " 函数:" + Thread.currentThread().getStackTrace()[1].getMethodName() + " 异常:" + e.getMessage() + ", 异常详情:{}";
            logger.error(error, e);
            return ReturnInfo.createInfo(RETURN_CODE.FAIL.getCode(), "同步失败", e);
        }
    }

    /**
     * 查询当前数据仓库的所有数据库
     *
     * @return
     */
    @RequestMapping("/show_databases")
    @ResponseBody
    public String show_databases() {
        meta_database_info meta_database_info = new meta_database_info();
        meta_database_info.setOwner(getUser().getId());
        List<meta_database_info> meta_database_infos = metaDatabaseMapper.select(meta_database_info);
        try {
            JSONArray jsa = new JSONArray();
            Map<String, String> dbMap = new HashMap<>();

            for (meta_database_info o : meta_database_infos) {
                dbMap.put(o.getDb_name(), "");
                JSONObject jo1 = new JSONObject();
                jo1.put("id", o.getDb_name() + "." + o.getTb_name());
                jo1.put("parent", o.getDb_name());
                jo1.put("text", o.getTb_name());
                jsa.add(jo1);
            }

            for (Map.Entry<String, String> entry : dbMap.entrySet()) {
                String dbName = entry.getKey();
                JSONObject jo1 = new JSONObject();
                jo1.put("id", dbName);
                jo1.put("parent", "#");
                jo1.put("text", dbName);
                jsa.add(jo1);
            }

            return jsa.toJSONString();

        } catch (Exception e) {
            String error = "类:" + Thread.currentThread().getStackTrace()[1].getClassName() + " 函数:" + Thread.currentThread().getStackTrace()[1].getMethodName() + " 异常:" + e.getMessage() + ", 异常详情:{}";
            logger.error(error, e);

        }


        return "";
    }

    /**
     * 此服务停用
     *
     * @return
     */
    @RequestMapping("/show_tables")
    @ResponseBody
    public String show_tables() {

        String url = JobCommon2.getZdhUrl(zdhHaInfoMapper, "").getZdh_url();
        try {
            String tableNames = HttpUtil.postJSON(url + "/show_tables", "");

            return tableNames;

        } catch (Exception e) {
            String error = "类:" + Thread.currentThread().getStackTrace()[1].getClassName() + " 函数:" + Thread.currentThread().getStackTrace()[1].getMethodName() + " 异常:" + e.getMessage() + ", 异常详情:{}";
            logger.error(error, e);
        }


        return "";
    }


    /**
     * 获取表结构说明
     *
     * @param table
     * @return
     */
    @RequestMapping("/desc_table")
    @ResponseBody
    public String desc_table(String table) {

        String url = JobCommon2.getZdhUrl(zdhHaInfoMapper, "").getZdh_url();
        try {
            JSONObject p = new JSONObject();
            p.put("table", table);
            String desc_table = HttpUtil.postJSON(url + "/desc_table", p.toJSONString());
            return desc_table;
        } catch (Exception e) {
            String error = "类:" + Thread.currentThread().getStackTrace()[1].getClassName() + " 函数:" + Thread.currentThread().getStackTrace()[1].getMethodName() + " 异常:" + e.getMessage() + ", 异常详情:{}";
            logger.error(error, e);
        }


        return "";
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
                    String error = "类:" + Thread.currentThread().getStackTrace()[1].getClassName() + " 函数:" + Thread.currentThread().getStackTrace()[1].getMethodName() + " 异常:" + e.getMessage() + ", 异常详情:{}";
                    logger.error(error, e);
                }
                // 恢复访问控制权限
                fields[i].setAccessible(accessFlag);
            } catch (IllegalArgumentException e) {
                logger.error("类:" + Thread.currentThread().getStackTrace()[1].getClassName() + " 函数:" + Thread.currentThread().getStackTrace()[1].getMethodName() + " 异常:" + e.getMessage() + ", 异常详情:{}", e);
            }
        }
    }

}
