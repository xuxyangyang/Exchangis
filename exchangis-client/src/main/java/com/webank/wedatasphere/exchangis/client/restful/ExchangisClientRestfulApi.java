package com.webank.wedatasphere.exchangis.client.restful;

import com.webank.wedatasphere.exchangis.common.linkis.ClientConfiguration;
import com.webank.wedatasphere.exchangis.datasource.core.utils.Json;
import com.webank.wedatasphere.exchangis.job.domain.ExchangisClientJobInfo;
import com.webank.wedatasphere.exchangis.job.domain.ExchangisJobInfo;
import com.webank.wedatasphere.exchangis.job.server.exception.ExchangisJobServerException;
import com.webank.wedatasphere.exchangis.job.server.service.impl.DefaultJobExecuteService;
import com.webank.wedatasphere.exchangis.job.server.vo.ExchangisJobProgressVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.linkis.httpclient.dws.authentication.TokenAuthenticationStrategy;
import org.apache.linkis.httpclient.dws.config.DWSClientConfig;
import org.apache.linkis.httpclient.dws.config.DWSClientConfigBuilder;
import org.apache.linkis.server.Message;
import org.apache.linkis.ujes.client.UJESClient;
import org.apache.linkis.ujes.client.UJESClientImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author jefftlin
 * @create 2022-10-20
 **/
@RestController
@RequestMapping(value = "/dss/exchangis/main/client")
public class ExchangisClientRestfulApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExchangisClientRestfulApi.class);

    private static final String Token_Code = "EXCHANGIS-AUTH";

    private final static String JOB_ID_LIST = "JOB_ID_LIST";

    @Resource
    private DefaultJobExecuteService executeService;


    @RequestMapping(value = "/encrypt", method = RequestMethod.POST)
    public Message excrypt(@RequestParam(value = "information") String information,
                           HttpServletRequest request) {

        return null;
    }

    @RequestMapping(value = "/template", method = RequestMethod.GET)
    public Message getTemplate(@RequestParam(value = "source") String source,
                               @RequestParam(value = "sink") String sink,
                               HttpServletRequest request) {

        return null;
    }

    @RequestMapping(value = "/execute", method = RequestMethod.POST,
            headers = {"content-type=multipart/dorm-data"})
    public Message executeJob(@RequestParam(value = "sourceAuthFile", required = false) MultipartFile sourceAuthFile,
                              @RequestParam(value = "sinkAuthFile", required = false) MultipartFile sinkAuthFile,
                              @RequestParam(value = "job") MultipartFile jobFile,
                              HttpServletRequest request) {
        Message message = Message.ok("Submitted succeed(提交成功)！");

        String userName = request.getHeader("SC_AUTH_ID");
        String password = request.getHeader("SC_AUTH_PWD");
        //todo validate um username and password


        // Linkis gateway
        String PROXY_USER = userName;

        DWSClientConfig clientConfig = ((DWSClientConfigBuilder) (DWSClientConfigBuilder.newBuilder()
                .addServerUrl(ClientConfiguration.LINKIS_SERVER_URL.getValue())
                .connectionTimeout(30000)
                .discoveryEnabled(true).discoveryFrequency(1, TimeUnit.MINUTES)
                .loadbalancerEnabled(true)
                .maxConnectionSize(5)
                .retryEnabled(false).readTimeout(30000)
                .setAuthenticationStrategy(new TokenAuthenticationStrategy())
                .setAuthTokenKey(ClientConfiguration.LINKIS_TOKEN_VALUE.getValue())
                .setAuthTokenValue(PROXY_USER)))
                .setDWSVersion("v1").build();
        UJESClient client = new UJESClientImpl(clientConfig);

        if (Objects.isNull(clientConfig) || Objects.isNull(client)) {
            return Message.error("udes.domain.jobInfo.vldNum.notNull");
        }


        // Execute job
        LOGGER.info("Parsing job file: " + jobFile.getOriginalFilename() + ", userName: " + userName);
        ExchangisClientJobInfo exchangisClientJobInfo = null;
        ExchangisJobInfo exchangisJobInfo = null;
        try {
            exchangisClientJobInfo = Json.fromJson(String.valueOf(jobFile.getInputStream()), ExchangisClientJobInfo.class);

            exchangisJobInfo = new ExchangisJobInfo(exchangisClientJobInfo);
            exchangisJobInfo.setJobContent(Json.toJson(exchangisClientJobInfo.getJobContent(), null));

            String jobExecutionId = executeService.executeJob(exchangisJobInfo, exchangisJobInfo.getExecuteUser());

            message.data("jobExecutionId", jobExecutionId);

            //todo dm number valid
            if (StringUtils.isBlank(exchangisClientJobInfo.getVldNum())) {
                return Message.error("udes.domain.jobInfo.vldNum.notNull");
            }
        } catch (IOException e) {
            return Message.error("Exception happened while parsing job File: " + jobFile.getOriginalFilename() + ", username: " + userName);
        } catch (ExchangisJobServerException e) {
            String errorMessage;
            if (Objects.nonNull(exchangisClientJobInfo)) {
                errorMessage = "Error occur while executing job: [id: " + exchangisClientJobInfo.getId() + " name: " + exchangisClientJobInfo.getJobName() + "]";
                message = Message.error(errorMessage + "(执行任务出错), reason: " + e.getMessage());
            } else {
                errorMessage = "Error to get the job detail (获取任务信息出错)";
                message = Message.error(errorMessage);
            }
            LOGGER.error(errorMessage, e);
            throw new RuntimeException(e);
        }

        return message;
    }

    @RequestMapping(value = "/status", method = RequestMethod.PUT)
    public Message getExecutionJobStatus(@RequestBody String jobExecutionId,
                                         HttpServletRequest request) {
        Message message = Message.ok("Get job list succeed(获取任务状态成功)！");

        String username = request.getHeader("SC_AUTH_ID");
        String password = request.getHeader("SC_AUTH_PWD");
        //todo um校验

        try {
            ExchangisJobProgressVo jobStatus = executeService.getJobStatus(jobExecutionId);
            message.setMethod("/api/rest_j/v1/dss/exchangis/main/client/status");
            message.data("status", jobStatus.getStatus());
            message.data("progress", jobStatus.getProgress());
        } catch (ExchangisJobServerException e) {
            String errorMessage = "Error occur while getting job status: [job_execution_id: " + jobExecutionId + "]";
            LOGGER.error(errorMessage, e);
            message = Message.error(message + ", reason: " + e.getMessage());
        }
        return message;
    }


}
