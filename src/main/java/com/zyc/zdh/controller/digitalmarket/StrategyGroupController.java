package com.zyc.zdh.controller.digitalmarket;

import com.alibaba.fastjson.JSONObject;
import com.zyc.zdh.annotation.White;
import com.zyc.zdh.controller.BaseController;
import com.zyc.zdh.dao.StrategyGroupInstanceMapper;
import com.zyc.zdh.dao.StrategyGroupMapper;
import com.zyc.zdh.dao.StrategyInstanceMapper;
import com.zyc.zdh.entity.*;
import com.zyc.zdh.job.JobDigitalMarket;
import com.zyc.zdh.job.JobStatus;
import com.zyc.zdh.job.ScheduleSource;
import com.zyc.zdh.job.SnowflakeIdWorker;
import com.zyc.zdh.util.Const;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
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

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 智能营销-策略组服务
 */
@Controller
public class StrategyGroupController extends BaseController {

    public Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StrategyGroupMapper strategyGroupMapper;

    @Autowired
    private StrategyGroupInstanceMapper strategyGroupInstanceMapper;

    @Autowired
    private StrategyInstanceMapper strategyInstanceMapper;

    /**
     * 策略组列表首页
     * @return
     */
    @RequestMapping(value = "/strategy_group_index", method = RequestMethod.GET)
    public String strategy_group_index() {

        return "digitalmarket/strategy_group_index";
    }

    /**
     * 策略组列表
     * @param group_context 关键字
     * @return
     */
    @RequestMapping(value = "/strategy_group_list", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String strategy_group_list(String group_context) {
        Example example=new Example(StrategyGroupInfo.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("is_delete", Const.NOT_DELETE);
        Example.Criteria criteria2=example.createCriteria();
        if(!StringUtils.isEmpty(group_context)){
            criteria2.orLike("group_context", getLikeCondition(group_context));
        }
        example.and(criteria2);

        List<StrategyGroupInfo> strategyGroupInfos = strategyGroupMapper.selectByExample(example);

        return JSONObject.toJSONString(strategyGroupInfos);
    }

    /**
     * 策略组新增首页
     * @return
     */
    @RequestMapping(value = "/strategy_group_add_index", method = RequestMethod.GET)
    public String strategy_group_add_index() {

        return "digitalmarket/strategy_group_add_index";
    }


    /**
     * 策略组明细
     * @param id 主键ID
     * @return
     */
    @RequestMapping(value = "/strategy_group_detail", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ReturnInfo strategy_group_detail(String id) {
        try {
            StrategyGroupInfo strategyGroupInfo = strategyGroupMapper.selectByPrimaryKey(id);
            return ReturnInfo.build(RETURN_CODE.SUCCESS.getCode(), "查询成功", strategyGroupInfo);
        } catch (Exception e) {
            return ReturnInfo.build(RETURN_CODE.FAIL.getCode(), "查询失败", e);
        }
    }

    /**
     * 策略组更新
     * @param strategyGroupInfo
     * @return
     */
    @RequestMapping(value = "/strategy_group_update", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @Transactional(propagation= Propagation.NESTED)
    public ReturnInfo strategy_group_update(StrategyGroupInfo strategyGroupInfo) {
        try {

            StrategyGroupInfo oldStrategyGroupInfo = strategyGroupMapper.selectByPrimaryKey(strategyGroupInfo.getId());

            //strategyGroupInfo.setRule_json(jsonArray.toJSONString());
            strategyGroupInfo.setOwner(oldStrategyGroupInfo.getOwner());
            strategyGroupInfo.setMisfire(oldStrategyGroupInfo.getMisfire());
            strategyGroupInfo.setSchedule_source(oldStrategyGroupInfo.getSchedule_source());
            strategyGroupInfo.setCreate_time(oldStrategyGroupInfo.getCreate_time());
            strategyGroupInfo.setUpdate_time(new Timestamp(new Date().getTime()));
            strategyGroupInfo.setIs_delete(Const.NOT_DELETE);
            strategyGroupMapper.updateByPrimaryKey(strategyGroupInfo);

            return ReturnInfo.build(RETURN_CODE.SUCCESS.getCode(), "更新成功", strategyGroupInfo);
        } catch (Exception e) {
            logger.error("类:" + Thread.currentThread().getStackTrace()[1].getClassName() + " 函数:" + Thread.currentThread().getStackTrace()[1].getMethodName() + " 异常: {}" , e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ReturnInfo.build(RETURN_CODE.FAIL.getCode(), "更新失败", e);
        }
    }


    /**
     * 策略组新增
     * @param strategyGroupInfo
     * @return
     */
    @RequestMapping(value = "/strategy_group_add", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @Transactional(propagation= Propagation.NESTED)
    public ReturnInfo strategy_group_add(StrategyGroupInfo strategyGroupInfo) {
        try {

            strategyGroupInfo.setId(SnowflakeIdWorker.getInstance().nextId()+"");
            strategyGroupInfo.setOwner(getOwner());
            strategyGroupInfo.setIs_delete(Const.NOT_DELETE);
            strategyGroupInfo.setMisfire("0");
            strategyGroupInfo.setSchedule_source(ScheduleSource.SYSTEM.getCode());
            strategyGroupInfo.setCreate_time(new Timestamp(new Date().getTime()));
            strategyGroupInfo.setUpdate_time(new Timestamp(new Date().getTime()));
            strategyGroupMapper.insert(strategyGroupInfo);
            return ReturnInfo.build(RETURN_CODE.SUCCESS.getCode(), "新增成功", null);
        } catch (Exception e) {
            logger.error("类:" + Thread.currentThread().getStackTrace()[1].getClassName() + " 函数:" + Thread.currentThread().getStackTrace()[1].getMethodName() + " 异常: {}" , e);
            return ReturnInfo.build(RETURN_CODE.FAIL.getCode(), "新增失败", e.getMessage());
        }
    }

    /**
     * 策略组删除
     * @param ids id数组
     * @return
     */
    @RequestMapping(value = "/strategy_group_delete", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @Transactional(propagation= Propagation.NESTED)
    public ReturnInfo strategy_group_delete(String[] ids) {
        try {
            strategyGroupMapper.deleteLogicByIds("strategy_group_info",ids, new Timestamp(new Date().getTime()));
            return ReturnInfo.build(RETURN_CODE.SUCCESS.getCode(), "删除成功", null);
        } catch (Exception e) {
            logger.error("类:" + Thread.currentThread().getStackTrace()[1].getClassName() + " 函数:" + Thread.currentThread().getStackTrace()[1].getMethodName() + " 异常: {}" , e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ReturnInfo.build(RETURN_CODE.FAIL.getCode(), "删除失败", e.getMessage());
        }
    }

    /**
     * 策略组手动执行页面
     * @return
     */
    @White
    @RequestMapping(value = "/strategy_group_task_exe_detail_index", method = RequestMethod.GET)
    public String strategy_group_task_exe_detail_index() {

        return "digitalmarket/strategy_group_task_exe_detail_index";
    }


    /**
     * 策略组手动执行
     * @param strategyGroupInfo
     * @return
     */
    @White
    @RequestMapping(value = "/strategy_group_task_execute", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    @Transactional(propagation= Propagation.NESTED)
    public ReturnInfo strategy_group_task_execute(StrategyGroupInfo strategyGroupInfo) {
        try {

            StrategyGroupInfo sgi=strategyGroupMapper.selectByPrimaryKey(strategyGroupInfo.getId());
            sgi.setStart_time(strategyGroupInfo.getStart_time());
            sgi.setCur_time(strategyGroupInfo.getStart_time());

            StrategyGroupInstance strategyGroupInstance = new StrategyGroupInstance();
            BeanUtils.copyProperties(strategyGroupInstance, sgi);

            strategyGroupInstance.setId(SnowflakeIdWorker.getInstance().nextId() + "");

            strategyGroupInstance.setStatus(JobStatus.NON.getValue());
            strategyGroupInstance.setSchedule_source(ScheduleSource.MANUAL.getCode());
            strategyGroupInstance.setRun_time(new Timestamp(new Date().getTime()));//实例开始时间
            strategyGroupInstance.setUpdate_time(new Timestamp(new Date().getTime()));
            strategyGroupInstance.setMisfire("0");

            debugInfo(strategyGroupInstance);
            strategyGroupInstanceMapper.insert(strategyGroupInstance);

            List<StrategyInstance> strategyInstances=JobDigitalMarket.sub_strategy_instance(strategyGroupInstance, null);

            strategyGroupInstance.setStatus(JobStatus.CREATE.getValue());
            strategyGroupInstanceMapper.updateStatus2Create(new String[]{strategyGroupInstance.getId()});

            return ReturnInfo.build(RETURN_CODE.SUCCESS.getCode(), "新增成功", null);
        } catch (Exception e) {
            logger.error("类:" + Thread.currentThread().getStackTrace()[1].getClassName() + " 函数:" + Thread.currentThread().getStackTrace()[1].getMethodName() + " 异常: {}" , e);
            return ReturnInfo.build(RETURN_CODE.FAIL.getCode(), "新增失败", e.getMessage());
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
                    logger.error("类:" + Thread.currentThread().getStackTrace()[1].getClassName() + " 函数:" + Thread.currentThread().getStackTrace()[1].getMethodName() + " 异常: {}" , e);
                }
                // 恢复访问控制权限
                fields[i].setAccessible(accessFlag);
            } catch (IllegalArgumentException e) {
                logger.error("类:" + Thread.currentThread().getStackTrace()[1].getClassName() + " 函数:" + Thread.currentThread().getStackTrace()[1].getMethodName() + " 异常: {}" , e);
            }
        }
    }

}
