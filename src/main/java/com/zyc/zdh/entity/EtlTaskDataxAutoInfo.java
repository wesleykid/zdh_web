package com.zyc.zdh.entity;

import java.sql.Timestamp;
import javax.persistence.*;

@Table(name = "etl_task_datax_auto_info")
public class EtlTaskDataxAutoInfo {
    @Id
    private String id;

    /**
     * 任务说明
     */
    private String etl_context;

    /**
     * 输入数据源id
     */
    private String data_sources_choose_input;

    /**
     * 输入数据源类型
     */
    private String data_source_type_input;

    /**
     * 输入数据源表名
     */
    private String data_sources_table_name_input;

    /**
     * 输入数据源文件名
     */
    private String data_sources_file_name_input;

    /**
     * 输入数据源参数
     */
    private String data_sources_params_input;

    /**
     * 输入数据源过滤条件
     */
    private String data_sources_filter_input;

    /**
     * 输出数据源id
     */
    private String data_sources_choose_output;

    /**
     * 输出数据源类型
     */
    private String data_source_type_output;

    /**
     * 输出数据源表名
     */
    private String data_sources_table_name_output;

    /**
     * 输出数据源文件名
     */
    private String data_sources_file_name_output;

    /**
     * 输出数据源参数
     */
    private String data_sources_params_output;

    /**
     * 数据源数据源删除条件
     */
    private String data_sources_clear_output;

    /**
     * 拥有者
     */
    private String owner;

    /**
     * 创建时间
     */
    private Timestamp create_time;

    /**
     * 表所属公司
     */
    private String company;

    /**
     * 表所属部门
     */
    private String section;

    /**
     * 表所属服务
     */
    private String service;

    /**
     * 更新说明
     */
    private String update_context;

    /**
     * 字段个数
     */
    private String column_size;

    /**
     * 行数范围
     */
    private String rows_range;

    /**
     * 错误率
     */
    private String error_rate;

    /**
     * 是否开启质量检测
     */
    private String enable_quality;

    /**
     * 去重字段
     */
    private String duplicate_columns;

    /**
     * 不可重复字段
     */
    private String primary_columns;

    /**
     * 输入文件类型
     */
    private String file_type_input;

    /**
     * 输入文件编码
     */
    private String encoding_input;

    /**
     * 输入分割符
     */
    private String sep_input;

    /**
     * 输出文件类型
     */
    private String file_type_output;

    /**
     * 输出文件编码
     */
    private String encoding_output;

    /**
     * 输出文件分割符
     */
    private String sep_output;

    /**
     * 输入是否包含表头
     */
    private String header_input;

    /**
     * 输出是否包含表头
     */
    private String header_output;

    /**
     * 洗牌字段默认空
     */
    private String repartition_cols_input;

    /**
     * 写入模式默认空
     */
    private String model_output;

    /**
     * 分区字段默认空
     */
    private String partition_by_output;

    /**
     * 合并小文件默认-1 不合并
     */
    private String merge_output;

    /**
     * 更新时间
     */
    private Timestamp update_time;

    /**
     * 是否删除,0:未删除,1:删除
     */
    private String is_delete;

    /**
     * 输入数据源表列名
     */
    private String data_sources_table_columns;

    /**
     * 输入输出自定映射关系
     */
    private String column_datas;

    /**
     * 归属组
     */
    private String dim_group;

    /**
     * 归属产品
     */
    private String product_code;

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
     * 获取任务说明
     *
     * @return etl_context - 任务说明
     */
    public String getEtl_context() {
        return etl_context;
    }

    /**
     * 设置任务说明
     *
     * @param etl_context 任务说明
     */
    public void setEtl_context(String etl_context) {
        this.etl_context = etl_context;
    }

    /**
     * 获取输入数据源id
     *
     * @return data_sources_choose_input - 输入数据源id
     */
    public String getData_sources_choose_input() {
        return data_sources_choose_input;
    }

    /**
     * 设置输入数据源id
     *
     * @param data_sources_choose_input 输入数据源id
     */
    public void setData_sources_choose_input(String data_sources_choose_input) {
        this.data_sources_choose_input = data_sources_choose_input;
    }

    /**
     * 获取输入数据源类型
     *
     * @return data_source_type_input - 输入数据源类型
     */
    public String getData_source_type_input() {
        return data_source_type_input;
    }

    /**
     * 设置输入数据源类型
     *
     * @param data_source_type_input 输入数据源类型
     */
    public void setData_source_type_input(String data_source_type_input) {
        this.data_source_type_input = data_source_type_input;
    }

    /**
     * 获取输入数据源表名
     *
     * @return data_sources_table_name_input - 输入数据源表名
     */
    public String getData_sources_table_name_input() {
        return data_sources_table_name_input;
    }

    /**
     * 设置输入数据源表名
     *
     * @param data_sources_table_name_input 输入数据源表名
     */
    public void setData_sources_table_name_input(String data_sources_table_name_input) {
        this.data_sources_table_name_input = data_sources_table_name_input;
    }

    /**
     * 获取输入数据源文件名
     *
     * @return data_sources_file_name_input - 输入数据源文件名
     */
    public String getData_sources_file_name_input() {
        return data_sources_file_name_input;
    }

    /**
     * 设置输入数据源文件名
     *
     * @param data_sources_file_name_input 输入数据源文件名
     */
    public void setData_sources_file_name_input(String data_sources_file_name_input) {
        this.data_sources_file_name_input = data_sources_file_name_input;
    }

    /**
     * 获取输入数据源参数
     *
     * @return data_sources_params_input - 输入数据源参数
     */
    public String getData_sources_params_input() {
        return data_sources_params_input;
    }

    /**
     * 设置输入数据源参数
     *
     * @param data_sources_params_input 输入数据源参数
     */
    public void setData_sources_params_input(String data_sources_params_input) {
        this.data_sources_params_input = data_sources_params_input;
    }

    /**
     * 获取输入数据源过滤条件
     *
     * @return data_sources_filter_input - 输入数据源过滤条件
     */
    public String getData_sources_filter_input() {
        return data_sources_filter_input;
    }

    /**
     * 设置输入数据源过滤条件
     *
     * @param data_sources_filter_input 输入数据源过滤条件
     */
    public void setData_sources_filter_input(String data_sources_filter_input) {
        this.data_sources_filter_input = data_sources_filter_input;
    }

    /**
     * 获取输出数据源id
     *
     * @return data_sources_choose_output - 输出数据源id
     */
    public String getData_sources_choose_output() {
        return data_sources_choose_output;
    }

    /**
     * 设置输出数据源id
     *
     * @param data_sources_choose_output 输出数据源id
     */
    public void setData_sources_choose_output(String data_sources_choose_output) {
        this.data_sources_choose_output = data_sources_choose_output;
    }

    /**
     * 获取输出数据源类型
     *
     * @return data_source_type_output - 输出数据源类型
     */
    public String getData_source_type_output() {
        return data_source_type_output;
    }

    /**
     * 设置输出数据源类型
     *
     * @param data_source_type_output 输出数据源类型
     */
    public void setData_source_type_output(String data_source_type_output) {
        this.data_source_type_output = data_source_type_output;
    }

    /**
     * 获取输出数据源表名
     *
     * @return data_sources_table_name_output - 输出数据源表名
     */
    public String getData_sources_table_name_output() {
        return data_sources_table_name_output;
    }

    /**
     * 设置输出数据源表名
     *
     * @param data_sources_table_name_output 输出数据源表名
     */
    public void setData_sources_table_name_output(String data_sources_table_name_output) {
        this.data_sources_table_name_output = data_sources_table_name_output;
    }

    /**
     * 获取输出数据源文件名
     *
     * @return data_sources_file_name_output - 输出数据源文件名
     */
    public String getData_sources_file_name_output() {
        return data_sources_file_name_output;
    }

    /**
     * 设置输出数据源文件名
     *
     * @param data_sources_file_name_output 输出数据源文件名
     */
    public void setData_sources_file_name_output(String data_sources_file_name_output) {
        this.data_sources_file_name_output = data_sources_file_name_output;
    }

    /**
     * 获取输出数据源参数
     *
     * @return data_sources_params_output - 输出数据源参数
     */
    public String getData_sources_params_output() {
        return data_sources_params_output;
    }

    /**
     * 设置输出数据源参数
     *
     * @param data_sources_params_output 输出数据源参数
     */
    public void setData_sources_params_output(String data_sources_params_output) {
        this.data_sources_params_output = data_sources_params_output;
    }

    /**
     * 获取数据源数据源删除条件
     *
     * @return data_sources_clear_output - 数据源数据源删除条件
     */
    public String getData_sources_clear_output() {
        return data_sources_clear_output;
    }

    /**
     * 设置数据源数据源删除条件
     *
     * @param data_sources_clear_output 数据源数据源删除条件
     */
    public void setData_sources_clear_output(String data_sources_clear_output) {
        this.data_sources_clear_output = data_sources_clear_output;
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
     * 获取表所属公司
     *
     * @return company - 表所属公司
     */
    public String getCompany() {
        return company;
    }

    /**
     * 设置表所属公司
     *
     * @param company 表所属公司
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * 获取表所属部门
     *
     * @return section - 表所属部门
     */
    public String getSection() {
        return section;
    }

    /**
     * 设置表所属部门
     *
     * @param section 表所属部门
     */
    public void setSection(String section) {
        this.section = section;
    }

    /**
     * 获取表所属服务
     *
     * @return service - 表所属服务
     */
    public String getService() {
        return service;
    }

    /**
     * 设置表所属服务
     *
     * @param service 表所属服务
     */
    public void setService(String service) {
        this.service = service;
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
     * 获取字段个数
     *
     * @return column_size - 字段个数
     */
    public String getColumn_size() {
        return column_size;
    }

    /**
     * 设置字段个数
     *
     * @param column_size 字段个数
     */
    public void setColumn_size(String column_size) {
        this.column_size = column_size;
    }

    /**
     * 获取行数范围
     *
     * @return rows_range - 行数范围
     */
    public String getRows_range() {
        return rows_range;
    }

    /**
     * 设置行数范围
     *
     * @param rows_range 行数范围
     */
    public void setRows_range(String rows_range) {
        this.rows_range = rows_range;
    }

    /**
     * 获取错误率
     *
     * @return error_rate - 错误率
     */
    public String getError_rate() {
        return error_rate;
    }

    /**
     * 设置错误率
     *
     * @param error_rate 错误率
     */
    public void setError_rate(String error_rate) {
        this.error_rate = error_rate;
    }

    /**
     * 获取是否开启质量检测
     *
     * @return enable_quality - 是否开启质量检测
     */
    public String getEnable_quality() {
        return enable_quality;
    }

    /**
     * 设置是否开启质量检测
     *
     * @param enable_quality 是否开启质量检测
     */
    public void setEnable_quality(String enable_quality) {
        this.enable_quality = enable_quality;
    }

    /**
     * 获取去重字段
     *
     * @return duplicate_columns - 去重字段
     */
    public String getDuplicate_columns() {
        return duplicate_columns;
    }

    /**
     * 设置去重字段
     *
     * @param duplicate_columns 去重字段
     */
    public void setDuplicate_columns(String duplicate_columns) {
        this.duplicate_columns = duplicate_columns;
    }

    /**
     * 获取不可重复字段
     *
     * @return primary_columns - 不可重复字段
     */
    public String getPrimary_columns() {
        return primary_columns;
    }

    /**
     * 设置不可重复字段
     *
     * @param primary_columns 不可重复字段
     */
    public void setPrimary_columns(String primary_columns) {
        this.primary_columns = primary_columns;
    }

    /**
     * 获取输入文件类型
     *
     * @return file_type_input - 输入文件类型
     */
    public String getFile_type_input() {
        return file_type_input;
    }

    /**
     * 设置输入文件类型
     *
     * @param file_type_input 输入文件类型
     */
    public void setFile_type_input(String file_type_input) {
        this.file_type_input = file_type_input;
    }

    /**
     * 获取输入文件编码
     *
     * @return encoding_input - 输入文件编码
     */
    public String getEncoding_input() {
        return encoding_input;
    }

    /**
     * 设置输入文件编码
     *
     * @param encoding_input 输入文件编码
     */
    public void setEncoding_input(String encoding_input) {
        this.encoding_input = encoding_input;
    }

    /**
     * 获取输入分割符
     *
     * @return sep_input - 输入分割符
     */
    public String getSep_input() {
        return sep_input;
    }

    /**
     * 设置输入分割符
     *
     * @param sep_input 输入分割符
     */
    public void setSep_input(String sep_input) {
        this.sep_input = sep_input;
    }

    /**
     * 获取输出文件类型
     *
     * @return file_type_output - 输出文件类型
     */
    public String getFile_type_output() {
        return file_type_output;
    }

    /**
     * 设置输出文件类型
     *
     * @param file_type_output 输出文件类型
     */
    public void setFile_type_output(String file_type_output) {
        this.file_type_output = file_type_output;
    }

    /**
     * 获取输出文件编码
     *
     * @return encoding_output - 输出文件编码
     */
    public String getEncoding_output() {
        return encoding_output;
    }

    /**
     * 设置输出文件编码
     *
     * @param encoding_output 输出文件编码
     */
    public void setEncoding_output(String encoding_output) {
        this.encoding_output = encoding_output;
    }

    /**
     * 获取输出文件分割符
     *
     * @return sep_output - 输出文件分割符
     */
    public String getSep_output() {
        return sep_output;
    }

    /**
     * 设置输出文件分割符
     *
     * @param sep_output 输出文件分割符
     */
    public void setSep_output(String sep_output) {
        this.sep_output = sep_output;
    }

    /**
     * 获取输入是否包含表头
     *
     * @return header_input - 输入是否包含表头
     */
    public String getHeader_input() {
        return header_input;
    }

    /**
     * 设置输入是否包含表头
     *
     * @param header_input 输入是否包含表头
     */
    public void setHeader_input(String header_input) {
        this.header_input = header_input;
    }

    /**
     * 获取输出是否包含表头
     *
     * @return header_output - 输出是否包含表头
     */
    public String getHeader_output() {
        return header_output;
    }

    /**
     * 设置输出是否包含表头
     *
     * @param header_output 输出是否包含表头
     */
    public void setHeader_output(String header_output) {
        this.header_output = header_output;
    }

    /**
     * 获取洗牌字段默认空
     *
     * @return repartition_cols_input - 洗牌字段默认空
     */
    public String getRepartition_cols_input() {
        return repartition_cols_input;
    }

    /**
     * 设置洗牌字段默认空
     *
     * @param repartition_cols_input 洗牌字段默认空
     */
    public void setRepartition_cols_input(String repartition_cols_input) {
        this.repartition_cols_input = repartition_cols_input;
    }

    /**
     * 获取写入模式默认空
     *
     * @return model_output - 写入模式默认空
     */
    public String getModel_output() {
        return model_output;
    }

    /**
     * 设置写入模式默认空
     *
     * @param model_output 写入模式默认空
     */
    public void setModel_output(String model_output) {
        this.model_output = model_output;
    }

    /**
     * 获取分区字段默认空
     *
     * @return partition_by_output - 分区字段默认空
     */
    public String getPartition_by_output() {
        return partition_by_output;
    }

    /**
     * 设置分区字段默认空
     *
     * @param partition_by_output 分区字段默认空
     */
    public void setPartition_by_output(String partition_by_output) {
        this.partition_by_output = partition_by_output;
    }

    /**
     * 获取合并小文件默认-1 不合并
     *
     * @return merge_output - 合并小文件默认-1 不合并
     */
    public String getMerge_output() {
        return merge_output;
    }

    /**
     * 设置合并小文件默认-1 不合并
     *
     * @param merge_output 合并小文件默认-1 不合并
     */
    public void setMerge_output(String merge_output) {
        this.merge_output = merge_output;
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
     * 获取输入数据源表列名
     *
     * @return data_sources_table_columns - 输入数据源表列名
     */
    public String getData_sources_table_columns() {
        return data_sources_table_columns;
    }

    /**
     * 设置输入数据源表列名
     *
     * @param data_sources_table_columns 输入数据源表列名
     */
    public void setData_sources_table_columns(String data_sources_table_columns) {
        this.data_sources_table_columns = data_sources_table_columns;
    }

    /**
     * 获取输入输出自定映射关系
     *
     * @return column_datas - 输入输出自定映射关系
     */
    public String getColumn_datas() {
        return column_datas;
    }

    /**
     * 设置输入输出自定映射关系
     *
     * @param column_datas 输入输出自定映射关系
     */
    public void setColumn_datas(String column_datas) {
        this.column_datas = column_datas;
    }

    public String getDim_group() {
        return dim_group;
    }

    public void setDim_group(String dim_group) {
        this.dim_group = dim_group;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }
}