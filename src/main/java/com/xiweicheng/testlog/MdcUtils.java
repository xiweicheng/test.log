package com.xiweicheng.testlog;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.MDC;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;

@Slf4j
public class MdcUtils {

    public static String createTraceId() {
        String uuid = UUID.randomUUID().toString();
        String uuidHex = DigestUtils.md5Hex(uuid);
        String traceId = uuidHex.substring(8, 24);
        log.debug("createTraceId, uuid:{} uuidHex:{} traceId:{}", uuid, uuidHex, traceId);
        return traceId;
    }

    public static void setTraceIdIfAbsent() {
        if (MDC.get(Constants.TRACE_ID) == null) {
            log.debug("traceId not exists.");
            MDC.put(Constants.TRACE_ID, createTraceId());
        } else {
            log.debug("traceId exists.");
        }
    }

    public static void setTraceId() {
        MDC.put(Constants.TRACE_ID, createTraceId());
    }

    public static void setTraceId(String traceId) {
        MDC.put(Constants.TRACE_ID, traceId);
    }

    public static <T> Callable<T> wrap(final Callable<T> callable, final Map<String, String> context) {
        return () -> {
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            setTraceIdIfAbsent();
            try {
                return callable.call();
            } finally {
                MDC.clear();
            }
        };
    }

    public static Runnable wrap(final Runnable runnable, final Map<String, String> context) {
        return () -> {
            if (context == null) {
                MDC.clear();
            } else {
                MDC.setContextMap(context);
            }
            setTraceIdIfAbsent();
            try {
                runnable.run();
            } finally {
                MDC.clear();
            }
        };
    }

}
