package com.xiweicheng.testlog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class AsyncService {

    public static final TraceThreadPoolExecutor threadPool = new TraceThreadPoolExecutor(5, 5, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100));

    @Async
    public void sendMsgBySpring() {
        log.info("send msg by spring success");
    }

    public void sendMsgByThreadPool() {
        threadPool.execute(() -> log.info("send msg by thread pool success"));
    }
}
