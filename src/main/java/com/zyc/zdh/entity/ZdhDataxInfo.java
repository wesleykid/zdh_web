package com.zyc.zdh.entity;

public class ZdhDataxInfo extends ZdhBaseInfo{

    //任务记录唯一标识(注意和调度任务的标识不一样)
    private String task_logs_id;


    private TaskLogInstance tli;
    //输入数据源
    private Dsi_Info dsi_Input;

    //etl 任务
    private EtlTaskDataxInfo etlTaskDataxInfo;

    public String getTask_logs_id() {
        return task_logs_id;
    }

    public void setTask_logs_id(String task_logs_id) {
        this.task_logs_id = task_logs_id;
    }

    public TaskLogInstance getTli() {
        return tli;
    }

    public void setTli(TaskLogInstance tli) {
        this.tli = tli;
    }

    public Dsi_Info getDsi_Input() {
        return dsi_Input;
    }

    public void setDsi_Input(Dsi_Info dsi_Input) {
        this.dsi_Input = dsi_Input;
    }



    public EtlTaskDataxInfo getEtlTaskDataxInfo() {
        return etlTaskDataxInfo;
    }

    public void setEtlTaskDataxInfo(EtlTaskDataxInfo etlTaskDataxInfo) {
        this.etlTaskDataxInfo = etlTaskDataxInfo;
    }

    public void setZdhInfo(DataSourcesInfo dataSourcesInfoInput , EtlTaskDataxInfo etlTaskDataxInfo, TaskLogInstance tli){

        // this.dataSourcesInfoInput=dataSourcesInfoInput;

        // this.dataSourcesInfoOutput=dataSourcesInfoOutput;
        this.tli=tli;

        this.etlTaskDataxInfo=etlTaskDataxInfo;
        Dsi_Info dsi_Input=new Dsi_Info();
        dsi_Input.setId(dataSourcesInfoInput.getId());
        dsi_Input.setData_source_context(dataSourcesInfoInput.getData_source_context());
        dsi_Input.setData_source_type(dataSourcesInfoInput.getData_source_type());
        dsi_Input.setDbtable(null);
        dsi_Input.setDriver(dataSourcesInfoInput.getDriver());
        dsi_Input.setUrl(dataSourcesInfoInput.getUrl());
        dsi_Input.setUser(dataSourcesInfoInput.getUsername());
        dsi_Input.setPassword(dataSourcesInfoInput.getPassword());
        dsi_Input.setPaths(null);
        this.dsi_Input=dsi_Input;

    }

}
