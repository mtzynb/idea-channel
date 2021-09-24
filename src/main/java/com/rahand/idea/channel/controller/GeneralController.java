package com.rahand.idea.channel.controller;

import com.rahand.idea.channel.utils.LogUtils;
import io.swagger.v3.oas.annotations.Hidden;
import org.slf4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
public class GeneralController {

    private Logger logger = LogUtils.getLogger();

    @PostMapping(value = "/banks/branches", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getBankBranchList(@RequestBody String body) {
        return "BranchList"; //TODO: complete implementation
    }
}
