package com.servitization.commons.util.crypto.params;

import com.servitization.commons.util.crypto.CipherParameters;

public class KeyParameter implements CipherParameters {
    private byte[] key;

    public KeyParameter(byte[] key) {
        this(key, 0, key.length);
    }

    public KeyParameter(byte[] key, int keyOff, int keyLen) {
        this.key = new byte[keyLen];

        System.arraycopy(key, keyOff, this.key, 0, keyLen);
    }

    public byte[] getKey() {
        return key;
    }
}
