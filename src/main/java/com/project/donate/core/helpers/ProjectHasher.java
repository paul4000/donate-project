package com.project.donate.core.helpers;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public class ProjectHasher {

    public static String hashProject(byte[] dataBytes) {

        HashFunction hashFunction = Hashing.sha256();

        return hashFunction.newHasher()
                .putBytes(dataBytes)
                .hash()
                .toString();
    }

}
