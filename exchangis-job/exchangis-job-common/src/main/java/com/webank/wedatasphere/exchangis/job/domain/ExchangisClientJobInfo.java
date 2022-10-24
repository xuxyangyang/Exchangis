package com.webank.wedatasphere.exchangis.job.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author jefftlin
 * @create 2022-10-20
 **/
public class ExchangisClientJobInfo {
    private Long id;

    /**
     * Job name
     */
    @NotNull
    @Size(max = 100)
    private String jobName;

    /**
     * Job description
     */
    private String jobDesc;

    /**
     * Job type
     */
    private String jobType = "OFFLINE";

    /**
     * Engine type
     */
    private String engineType = "DATAX";

    /**
     * Job content
     */
    private ExchangisClientJobContent jobContent;

    /**
     * Create user
     */
    private String createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private String modifyUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifyTime;


    /**
     * Source type and sink type
     */
    private String dataSourceType;

    private String dataSinkType;

    /**
     * User used to execute
     */
    private String execUser;

    /**
     * Nodes used to execute
     */
    private List<String> execNodeNames = new ArrayList<>();

    /**
     * Validate
     */
    private String itsm;//todo

    private String vldNum;

    private String vldType = "datamap";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public ExchangisClientJobContent getJobContent() {
        return jobContent;
    }

    public void setJobContent(ExchangisClientJobContent jobContent) {
        this.jobContent = jobContent;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getDataSourceType() {
        return dataSourceType;
    }

    public void setDataSourceType(String dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

    public String getDataSinkType() {
        return dataSinkType;
    }

    public void setDataSinkType(String dataSinkType) {
        this.dataSinkType = dataSinkType;
    }

    public String getExecUser() {
        return execUser;
    }

    public void setExecUser(String execUser) {
        this.execUser = execUser;
    }

    public List<String> getExecNodeNames() {
        return execNodeNames;
    }

    public void setExecNodeNames(List<String> execNodeNames) {
        this.execNodeNames = execNodeNames;
    }

    public String getItsm() {
        return itsm;
    }

    public void setItsm(String itsm) {
        this.itsm = itsm;
    }

    public String getVldNum() {
        return vldNum;
    }

    public void setVldNum(String vldNum) {
        this.vldNum = vldNum;
    }

    public String getVldType() {
        return vldType;
    }

    public void setVldType(String vldType) {
        this.vldType = vldType;
    }
}
