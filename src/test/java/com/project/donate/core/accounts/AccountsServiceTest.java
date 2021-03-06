package com.project.donate.core.accounts;

import com.project.donate.core.repositories.UsersRepository;
import com.project.donate.core.service.accounts.AccountsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.web3j.crypto.CipherException;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class AccountsServiceTest {

    @Mock
    UsersRepository usersRepository;

    @InjectMocks
    private AccountsService accountsService;

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