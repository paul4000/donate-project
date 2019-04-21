package com.project.donate.core;

import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.http.HttpService;

@Component
public class Web3jServiceSupplier {

    private Web3jService service = new HttpService("http://localhost:8545");
    private Web3j web3j = Web3j.build(service);

    public Web3j getWeb3j() {
        return web3j;
    }
}
