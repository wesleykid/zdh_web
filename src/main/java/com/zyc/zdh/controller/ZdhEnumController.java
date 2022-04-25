package com.zyc.zdh.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zyc.zdh.dao.EnumMapper;
import com.zyc.zdh.entity.EnumInfo;
import com.zyc.zdh.entity.RETURN_CODE;
import com.zyc.zdh.entity.ReturnInfo;
import com.zyc.zdh.job.SnowflakeIdWorker;
import com.zyc.zdh.util.Const;
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

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 枚举服务
 */
@Controller
public class ZdhEnumController extends BaseController{

    public Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    EnumMapper enumMapper;


    /**
     * 枚举首页
     * @return
     */
    @RequestMapping("enum_index")
    public String enum_index() {

        return "service/enum_index";
    }

    /**
     * 获取枚举详情
     * @param id 主键
     * @return
     */
    @RequestMapping(value = "/enum_detail", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String enum_detail(String id) {
        try{
            EnumInfo enumInfo=enumMapper.selectByPrimaryKey(id);
            return ReturnInfo.createInfo(RETURN_CODE.SUCCESS.getCode(), "查询成功", enumInfo);
        }catch (Exception e){
            String error = "类:"+Thread.currentThread().getStackTrace()[1].getClassName()+" 函数:"+Thread.currentThread().getStackTrace()[1].getMethodName()+ " 异常: {}";
            logger.error(error, e);
            return ReturnInfo.createInfo(RETURN_CODE.FAIL.getCode(), "查询失败", e);
        }

    }

    /**
     * 获取枚举列表
     * @param enum_context 关键字
     * @return
     */
    @RequestMapping(value = "/enum_list", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String enum_list(String enum_context) {
        List<EnumInfo> list = new ArrayList<>();
        Example example=new Example(EnumInfo.class);
        Example.Criteria criteria= example.createCriteria();
        if(!StringUtils.isEmpty(enum_context)){
            criteria.orLike("enum_context", getLikeCondition(enum_context));
            criteria.orLike("enum_json", getLikeCondition(enum_context));
            criteria.orLike("enum_code", getLikeCondition(enum_context));
        }

        list = enumMapper.selectByExample(example);
        return JSON.toJSONString(list);
    }

    /**
     * 批量删除枚举信息
     * @param ids 主键数组
     * @return
     */
    @RequestMapping(value = "/enum_delete", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    @Transactional(propagation= Propagation.NESTED)
    public String enum_delete(String[] ids) {
        try{
            enumMapper.deleteBatchByIds(ids);
            return ReturnInfo.createInfo(RETURN_CODE.SUCCESS.getCode(),RETURN_CODE.SUCCESS.getDesc(), null);
        }catch (Exception e){
            String error = "类:"+Thread.currentThread().getStackTrace()[1].getClassName()+" 函数:"+Thread.currentThread().getStackTrace()[1].getMethodName()+ " 异常: {}";
			logger.error(error, e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ReturnInfo.createInfo(RETURN_CODE.FAIL.getCode(),e.getMessage(), null);
        }
    }

    /**
     * 枚举新增首页
     * @return
     */
    @RequestMapping("/enum_add_index")
    public String enum_add() {

        return "service/enum_add_index";
    }


    /**
     * 新增枚举
     * @param enumInfo
     * @return
     */
    @RequestMapping(value="/enum_add", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    @Transactional(propagation= Propagation.NESTED)
    public String enum_add(EnumInfo enumInfo,String[] enum_value, String[] enum_value_context) {

        try{
            String owner = getUser().getId();
            enumInfo.setOwner(owner);
            debugInfo(enumInfo);

            JSONArray jsonArray=new JSONArray();

            for(int i=0;i<enum_value.length;i++){
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("enum_value", enum_value[i]);
                jsonObject.put("enum_value_context", enum_value_context[i]);
                jsonArray.add(jsonObject);
            }

            enumInfo.setEnum_json(jsonArray.toJSONString());
            enumInfo.setId(SnowflakeIdWorker.getInstance().nextId() + "");
            enumInfo.setCreate_time(new Timestamp(new Date().getTime()));
            enumInfo.setUpdate_time(new Timestamp(new Date().getTime()));
            enumInfo.setIs_delete(Const.NOT_DELETE);
            enumMapper.insert(enumInfo);
            return ReturnInfo.createInfo(RETURN_CODE.SUCCESS.getCode(),RETURN_CODE.SUCCESS.getDesc(), null);
        }catch (Exception e){
            String error = "类:"+Thread.currentThread().getStackTrace()[1].getClassName()+" 函数:"+Thread.currentThread().getStackTrace()[1].getMethodName()+ " 异常: {}";
            logger.error(error, e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ReturnInfo.createInfo(RETURN_CODE.FAIL.getCode(),e.getMessage(), null);
        }
    }




    /**
     * 枚举更新
     * @param enumInfo
     * @return
     */
    @RequestMapping(value="/enum_update", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    @Transactional(propagation= Propagation.NESTED)
    public String enum_update(EnumInfo enumInfo,String[] enum_value, String[] enum_value_context) {
        try{
            String owner = getUser().getId();
            enumInfo.setOwner(owner);
            enumInfo.setIs_delete(Const.NOT_DELETE);
            enumInfo.setUpdate_time(new Timestamp(new Date().getTime()));
            JSONArray jsonArray=new JSONArray();

            for(int i=0;i<enum_value.length;i++){
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("enum_value", enum_value[i]);
                jsonObject.put("enum_value_context", enum_value_context[i]);
                jsonArray.add(jsonObject);
            }

            enumInfo.setEnum_json(jsonArray.toJSONString());
            debugInfo(enumInfo);
            enumMapper.updateByPrimaryKey(enumInfo);
            return ReturnInfo.createInfo(RETURN_CODE.SUCCESS.getCode(),RETURN_CODE.SUCCESS.getDesc(), null);
        }catch (Exception e){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            String error = "类:"+Thread.currentThread().getStackTrace()[1].getClassName()+" 函数:"+Thread.currentThread().getStackTrace()[1].getMethodName()+ " 异常: {}";
			logger.error(error, e);
            return ReturnInfo.createInfo(RETURN_CODE.FAIL.getCode(),e.getMessage(), null);
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
                    String error = "类:"+Thread.currentThread().getStackTrace()[1].getClassName()+" 函数:"+Thread.currentThread().getStackTrace()[1].getMethodName()+ " 异常: {}";
                    logger.error(error, e);
                }
                // 恢复访问控制权限
                fields[i].setAccessible(accessFlag);
            } catch (IllegalArgumentException e) {
                 logger.error("类:"+Thread.currentThread().getStackTrace()[1].getClassName()+" 函数:"+Thread.currentThread().getStackTrace()[1].getMethodName()+ " 异常: {}", e);
            }
        }
    }

}
