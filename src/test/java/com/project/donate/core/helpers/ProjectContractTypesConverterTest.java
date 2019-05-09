package com.project.donate.core.helpers;

import org.apache.commons.codec.DecoderException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProjectContractTypesConverterTest {

    @Test
    public void shouldMapProjectId() throws DecoderException {
        //given
        long projectId = 34;

        //when
        byte[] bytes = ProjectContractTypesConverter.convertProjectId(projectId);

        //then
        assertThat(bytes).hasSize(32);
    }

    @Test
    public void shouldMapProjectHash() throws DecoderException {
        //given
        String projectHash = "ce3c2b274c17068c1b3eb1eae4b9cfb06bf685f7470001e8e0fe9d7a8d3bf5d5";

        //when
        byte[] bytes = ProjectContractTypesConverter.convertProjectHash(projectHash);

        //then
        assertThat(bytes).hasSize(32);
    }
}