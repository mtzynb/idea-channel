package com.rahand.idea.channel.controller;

import com.rahand.idea.channel.enums.LoggerHelp;
import com.rahand.idea.channel.utils.LogUtils;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Created by z.mohammadtabar on 21/09/2021.
 */
@ApiIgnore
@RestController
public class HomeController {

    private Logger logger = LogUtils.getLogger();

    @GetMapping(value = "/")
    public String home() {
        logger.info(LoggerHelp.CHANNEL_START_LOG + "/idea-channel -GET Method- Started...");
        String res = "Idea-Channel App Home Page!  for more information Go to: http://IP:PORT/idea-channel/swagger-ui/";
        logger.info(LoggerHelp.CHANNEL_START_LOG + "/idea-channel -GET Method Ended. Response is: " + res);
        return res;
    }

    @GetMapping(value = "/deposits")
    public String depositsHome() {
        logger.info(LoggerHelp.CHANNEL_START_LOG + "/deposits -GET Method- Started...");
        String res = "Deposit services home page in Idea-Channel App!  for more information Go to: http://IP:PORT/idea-channel/swagger-ui/";
        logger.info(LoggerHelp.CHANNEL_START_LOG + "/deposits -GET Method Ended. Response is: " + res);
        return res;
    }

}
