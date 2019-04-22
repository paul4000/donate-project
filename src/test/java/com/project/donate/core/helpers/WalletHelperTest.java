package com.project.donate.core.helpers;

import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class WalletHelperTest {

    @Test
    public void shouldExtractAccountAddressTest() {
        //given
        String walletName = "UTC--2019-02-24T21-03-15.812871500Z--61665f9c0d08cfeaad3213da647bcd1a56603c7e";

        //when
        Optional<String> accountAddress = WalletHelper.extractAccountAddress(walletName);

        //then
        assertThat(accountAddress).isPresent();
        assertThat(accountAddress.get()).isEqualTo("0x61665f9c0d08cfeaad3213da647bcd1a56603c7e");
    }

}