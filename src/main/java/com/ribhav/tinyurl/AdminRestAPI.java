package com.ribhav.tinyurl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * User : ribhavpahuja
 * Date : 05/11/21
 * Time : 4:22 PM
 */
@RestController
public class AdminRestAPI {

    private final URLService URLService;

    @Autowired
    public AdminRestAPI(URLService URLService) {
        this.URLService = URLService;
    }

    @RequestMapping("/generate")
    public String generateURL(@RequestParam String url) {
        return URLService.generateKeyForUrl(url);
    }
}
