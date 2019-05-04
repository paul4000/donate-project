package com.project.donate.core.helpers;


import org.apache.commons.codec.DecoderException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.codec.binary.Hex;

import java.util.stream.Collectors;

public class ProjectContractTypesConverter {

    public static byte[] convertProjectId(long projectId) throws DecoderException {

        String stringifyOfProjectId = String.valueOf(projectId);
        String hexId = stringifyOfProjectId.chars()
                .mapToObj(Integer::toHexString)
                .collect(Collectors.joining());

        String paddedId = StringUtils.leftPad(hexId, 64, "0");

        return Hex.decodeHex(paddedId);
    }

    public static byte[] convertProjectHash(String projectHash) throws DecoderException {

        return Hex.decodeHex(projectHash);
    }

}
