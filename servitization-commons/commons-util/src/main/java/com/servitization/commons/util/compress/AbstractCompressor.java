package com.servitization.commons.util.compress;

import java.io.InputStream;

public abstract class AbstractCompressor implements Compressor {
    public AbstractCompressor(InputStream input) {
        this.input = input;
    }

    protected InputStream input;
}
