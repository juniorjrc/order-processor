package com.juniorjrc.orderprocessor.config.log;

import feign.Logger;
import feign.Request;
import feign.Response;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static feign.Logger.Level.HEADERS;

@Slf4j
public class CustomFeignRequestLogging extends Logger {

    private static final String REQUEST_MESSAGE = "---> %s %s HTTP/1.1 (%s-byte body) ";
    private static final String RESPONSE_MESSAGE = "<--- %s %s HTTP/1.1 %s (%sms) ";

    @Override
    protected void logRequest(String configKey, Level logLevel, Request request) {

        if (logLevel.ordinal() >= HEADERS.ordinal()) {
            super.logRequest(configKey, logLevel, request);
        } else {
            int bodyLength = 0;
            if (request.body() != null && request.body().length > 0) {
                bodyLength = request.body().length;
            }
            log(configKey, REQUEST_MESSAGE, request.httpMethod().name(), request.url(), bodyLength);
        }
    }

    @Override
    protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response,
                                              long elapsedTime)
            throws IOException {
        if (logLevel.ordinal() >= HEADERS.ordinal()) {
            super.logAndRebufferResponse(configKey, logLevel, response, elapsedTime);
        } else {
            int status = response.status();
            Request request = response.request();
            log(configKey, RESPONSE_MESSAGE, request.httpMethod().name(), request.url(), status,
                    elapsedTime);
        }
        return response;
    }


    @Override
    protected void log(String configKey, String format, Object... args) {
        log.info(format(configKey, format, args));
    }

    protected String format(String configKey, String format, Object... args) {
        String configKeyFormat = methodTag(configKey) + format;
        return String.format(configKeyFormat, args);
    }
}
