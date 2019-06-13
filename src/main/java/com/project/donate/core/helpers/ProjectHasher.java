package com.project.donate.core.helpers;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.primitives.Bytes;

public class ProjectHasher {

    public static String hashProject(byte[] dataBytes, String summary) {

        HashFunction hashFunction = Hashing.sha256();

        byte[] summaryBytes = summary.getBytes();

        byte[] concatProjectBytes = Bytes.concat(dataBytes, summaryBytes);

        return hashFunction.newHasher()
                .putBytes(concatProjectBytes)
                .hash()
                .toString();
    }

}
