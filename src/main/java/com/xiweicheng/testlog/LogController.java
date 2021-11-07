package com.xiweicheng.testlog;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping
public class LogController {

    @Autowired
    AsyncService asyncService;

    @GetMapping("a")
    public Object testa() {
        log.debug("test log -- a.");

//        asyncService.sendMsgBySpring();

        try {
            // A中调用B
            return HttpUtils.get("http://localhost:8080/b");
        } catch (Exception e) {
            log.error("call b error", e);
        }

        return "OK -- a";
    }

    @GetMapping("b")
    public Object testb() {
        log.debug("test log -- b.");

        asyncService.sendMsgBySpring();

        asyncService.sendMsgByThreadPool();

        return "OK -- b";
    }

}