package com.webank.wedatasphere.exchangis.datasource.Utils;

import com.google.common.util.concurrent.RateLimiter;
import com.webank.wedatasphere.exchangis.datasource.core.exception.ExchangisDataSourceException;
import com.webank.wedatasphere.exchangis.datasource.core.exception.ExchangisDataSourceExceptionCode;
import com.webank.wedatasphere.exchangis.datasource.dto.ConnectDataSourceDTO;
import com.webank.wedatasphere.exchangis.datasource.dto.ConnectDataSourceLimeterDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConnectDataSourceLimitUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectDataSourceLimitUtil.class);

    public static Map<ConnectDataSourceDTO, ConnectDataSourceLimeterDTO> dataSourceConnectRateLimiter = new ConcurrentHashMap();

    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static Calendar calendar = Calendar.getInstance();

    private static Integer countPerSecond = 5;

    public static boolean tryConnect(ConnectDataSourceDTO connectDataSourceDTO) throws ExchangisDataSourceException {
        Date currentTime = null;
        try {
            currentTime = sdf.parse(sdf.format(calendar.getTime()));
        } catch (ParseException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
        if (dataSourceConnectRateLimiter.containsKey(connectDataSourceDTO)) {
            ConnectDataSourceLimeterDTO connectDataSourceLimeterDTO = dataSourceConnectRateLimiter.get(connectDataSourceDTO);
            // new day
            if (currentTime.compareTo(connectDataSourceLimeterDTO.getCurrentDate()) > 0) {
                connectDataSourceLimeterDTO.setCurrentDate(currentTime);
                connectDataSourceLimeterDTO.resetAttemptTime();
            } else {
                // today
                if (connectDataSourceLimeterDTO.isLimit()) {
                    throw new ExchangisDataSourceException(ExchangisDataSourceExceptionCode.DATA_SOURCE_CONNECT_ERROR.getCode(), "The number of attempts to connect to the datasource in the current day is exhausted. Please try again tomorrow.(当日尝试连接数据源次数用完，请明日再试!)");
                }
            }

            boolean isAcquire = connectDataSourceLimeterDTO.getRateLimiter().tryAcquire();
            if (isAcquire) {
                connectDataSourceLimeterDTO.addAttemptTime();
                return true;
            } else {
                throw new ExchangisDataSourceException(ExchangisDataSourceExceptionCode.DATA_SOURCE_CONNECT_ERROR.getCode(), "Attempting to connect to the datasource is too frequent, please try again later.(尝试连接数据源太频繁，请稍后再试!)");
            }
        }

        // new user
        RateLimiter rateLimiter = RateLimiter.create(countPerSecond);
        ConnectDataSourceLimeterDTO newConnectDataSourceLimeterDTO = new ConnectDataSourceLimeterDTO(rateLimiter, currentTime);
        dataSourceConnectRateLimiter.put(connectDataSourceDTO, newConnectDataSourceLimeterDTO);
        return rateLimiter.tryAcquire();
    }

}
