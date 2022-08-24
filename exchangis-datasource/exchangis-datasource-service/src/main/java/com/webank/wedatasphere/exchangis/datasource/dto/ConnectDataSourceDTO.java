package com.webank.wedatasphere.exchangis.datasource.dto;

public class ConnectDataSourceDTO {

    private String userName;

    private String datasourceMsg;


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
    public String toString() {
        return "ConnectDataSourceDTO{" +
                "userName='" + userName + '\'' +
                ", datasourceMsg='" + datasourceMsg + '\'' +
                '}';
    }
}
