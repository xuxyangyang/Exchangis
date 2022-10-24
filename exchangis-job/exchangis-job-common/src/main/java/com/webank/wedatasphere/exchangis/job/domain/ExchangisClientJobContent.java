package com.webank.wedatasphere.exchangis.job.domain;

import java.util.HashMap;

/**
 * @author jefftlin
 * @create 2022-10-21
 **/
public class ExchangisClientJobContent {
    /**
     * Source parameters
     */
    private HashMap<Object, Object> sourceDataSource;

    /**
     * Sink parameters
     */
    private HashMap<Object, Object> sinkDataSource;

    private Advance advance;

    private Limit limit;

    private Boolean syncMeta;

    private String transportType;

    private String usePostProcess;

    class Advance {
        private Integer mMemory;

        private Integer mParallel;

        public Integer getmMemory() {
            return mMemory;
        }

        public void setmMemory(Integer mMemory) {
            this.mMemory = mMemory;
        }

        public Integer getmParallel() {
            return mParallel;
        }

        public void setmParallel(Integer mParallel) {
            this.mParallel = mParallel;
        }
    }

    class Limit {
        private Integer mBytes;

        private Integer recordLimit;

        private Integer errorRecordLimit;

        public Integer getmBytes() {
            return mBytes;
        }

        public void setmBytes(Integer mBytes) {
            this.mBytes = mBytes;
        }

        public Integer getRecordLimit() {
            return recordLimit;
        }

        public void setRecordLimit(Integer recordLimit) {
            this.recordLimit = recordLimit;
        }

        public Integer getErrorRecordLimit() {
            return errorRecordLimit;
        }

        public void setErrorRecordLimit(Integer errorRecordLimit) {
            this.errorRecordLimit = errorRecordLimit;
        }
    }

    public HashMap<Object, Object> getSourceDataSource() {
        return sourceDataSource;
    }

    public void setSourceDataSource(HashMap<Object, Object> sourceDataSource) {
        this.sourceDataSource = sourceDataSource;
    }

    public HashMap<Object, Object> getSinkDataSource() {
        return sinkDataSource;
    }

    public void setSinkDataSource(HashMap<Object, Object> sinkDataSource) {
        this.sinkDataSource = sinkDataSource;
    }

    public Advance getAdvance() {
        return advance;
    }

    public void setAdvance(Advance advance) {
        this.advance = advance;
    }

    public Limit getLimit() {
        return limit;
    }

    public void setLimit(Limit limit) {
        this.limit = limit;
    }

    public Boolean getSyncMeta() {
        return syncMeta;
    }

    public void setSyncMeta(Boolean syncMeta) {
        this.syncMeta = syncMeta;
    }

    public String getTransportType() {
        return transportType;
    }

    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    public String getUsePostProcess() {
        return usePostProcess;
    }

    public void setUsePostProcess(String usePostProcess) {
        this.usePostProcess = usePostProcess;
    }
}
