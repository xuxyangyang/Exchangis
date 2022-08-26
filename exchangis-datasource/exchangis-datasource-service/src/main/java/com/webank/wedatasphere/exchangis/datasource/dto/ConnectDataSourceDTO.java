package com.webank.wedatasphere.exchangis.datasource.dto;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class ConnectDataSourceDTO {

    private String userName;

    private String datasourceMsg;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDatasourceMsg() {
        return datasourceMsg;
    }

    public void setDatasourceMsg(String datasourceMsg) {
        this.datasourceMsg = datasourceMsg;
    }

    public ConnectDataSourceDTO() {
    }

    public ConnectDataSourceDTO(String userName, Object... msg) {
        this.userName = userName;
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < msg.length; i++) {
            sb.append(msg[i]);
        }
        this.datasourceMsg = sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ConnectDataSourceDTO) {
            ConnectDataSourceDTO conn = (ConnectDataSourceDTO) obj;
            if (StringUtils.equals(conn.getUserName(), this.userName) && StringUtils.equals(conn.getDatasourceMsg(), this.datasourceMsg)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName);
    }

    @Override
    public String toString() {
        return "ConnectDataSourceDTO{" +
                "userName='" + userName + '\'' +
                ", datasourceMsg='" + datasourceMsg + '\'' +
                '}';
    }
}
