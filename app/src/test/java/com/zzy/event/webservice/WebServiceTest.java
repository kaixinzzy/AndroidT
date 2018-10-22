package com.zzy.event.webservice;

import com.zzy.webservice.WebService;

import org.junit.Test;

public class WebServiceTest {

    @Test
    public void getRemoteInfo() {
        WebService webService = new WebService();
        webService.getRemoteInfo("13661101013", 5);
    }

    @Test
    public void getRemainingSum() {
        WebService webService = new WebService();
        webService.getRemainingSum("TBCL03", "123456789", 5);
    }

}
