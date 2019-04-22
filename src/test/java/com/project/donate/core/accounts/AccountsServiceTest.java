package com.project.donate.core.accounts;

import org.junit.Test;
import org.web3j.crypto.CipherException;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


public class AccountsServiceTest {

    private AccountsService accountsService = new AccountsService();

    @Test
    public void shouldCreateAccount() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, CipherException, IOException {
        //given
        String password = "account2";

        //when
        Optional<String> walletResult = accountsService.createWalletFile(password);

        //then
        assertThat(walletResult).isPresent();
    }
}