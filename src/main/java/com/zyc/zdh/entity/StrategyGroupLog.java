package com.zyc.zdh.entity;

import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Table(name = "strategy_group_log")
public class StrategyGroupLog {
    @Id
    private String id;

    /**
     * log对象主键id
     */
    private Long log_object_id;

    /**
     * 日志分类
     */
    private String log_type;

    /**
     * 日志分类
     */
    private Integer log_version;

    /**
     * 拥有者
     */
    private String owner;

    /**
     * 创建时间
     */
    private Timestamp create_time;

    /**
     * 更新时间
     */
    private Timestamp update_time;

    /**
     * 更新说明
     */
    private String update_context;

    /**
     * 是否删除,0:未删除,1:删除
     */
    private String is_delete;

    /**
     * 产品code
     */
    private String product_code;

    /**
     * 用户组
     */
    private String dim_group;

    /**
     * 日志信息
     */
    private String log_json;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取log对象主键id
     *
     * @return log_object_id - log对象主键id
     */
    public Long getLog_object_id() {
        return log_object_id;
    }

    /**
     * 设置log对象主键id
     *
     * @param log_object_id log对象主键id
     */
    public void setLog_object_id(Long log_object_id) {
        this.log_object_id = log_object_id;
    }

    /**
     * 获取日志分类
     *
     * @return log_type - 日志分类
     */
    public String getLog_type() {
        return log_type;
    }

    /**
     * 设置日志分类
     *
     * @param log_type 日志分类
     */
    public void setLog_type(String log_type) {
        this.log_type = log_type;
    }

    /**
     * 获取日志分类
     *
     * @return log_version - 日志分类
     */
    public Integer getLog_version() {
        return log_version;
    }

    /**
     * 设置日志分类
     *
     * @param log_version 日志分类
     */
    public void setLog_version(Integer log_version) {
        this.log_version = log_version;
    }

    /**
     * 获取拥有者
     *
     * @return owner - 拥有者
     */
    public String getOwner() {
        return owner;
    }

    /**
     * 设置拥有者
     *
     * @param owner 拥有者
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Timestamp getCreate_time() {
        return create_time;
    }

    /**
     * 设置创建时间
     *
     * @param create_time 创建时间
     */
    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Timestamp getUpdate_time() {
        return update_time;
    }

    /**
     * 设置更新时间
     *
     * @param update_time 更新时间
     */
    public void setUpdate_time(Timestamp update_time) {
        this.update_time = update_time;
    }

    /**
     * 获取更新说明
     *
     * @return update_context - 更新说明
     */
    public String getUpdate_context() {
        return update_context;
    }

    /**
     * 设置更新说明
     *
     * @param update_context 更新说明
     */
    public void setUpdate_context(String update_context) {
        this.update_context = update_context;
    }

    /**
     * 获取是否删除,0:未删除,1:删除
     *
     * @return is_delete - 是否删除,0:未删除,1:删除
     */
    public String getIs_delete() {
        return is_delete;
    }

    /**
     * 设置是否删除,0:未删除,1:删除
     *
     * @param is_delete 是否删除,0:未删除,1:删除
     */
    public void setIs_delete(String is_delete) {
        this.is_delete = is_delete;
    }

    /**
     * 获取产品code
     *
     * @return product_code - 产品code
     */
    public String getProduct_code() {
        return product_code;
    }

    /**
     * 设置产品code
     *
     * @param product_code 产品code
     */
    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    /**
     * 获取用户组
     *
     * @return dim_group - 用户组
     */
    public String getDim_group() {
        return dim_group;
    }

    /**
     * 设置用户组
     *
     * @param dim_group 用户组
     */
    public void setDim_group(String dim_group) {
        this.dim_group = dim_group;
    }

    /**
     * 获取日志信息
     *
     * @return log_json - 日志信息
     */
    public String getLog_json() {
        return log_json;
    }

    /**
     * 设置日志信息
     *
     * @param log_json 日志信息
     */
    public void setLog_json(String log_json) {
        this.log_json = log_json;
    }
}