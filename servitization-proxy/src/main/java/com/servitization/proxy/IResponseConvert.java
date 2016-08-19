package com.servitization.proxy;

import com.servitization.embedder.immobile.ImmobileResponse;

import java.io.IOException;

public interface IResponseConvert {
    void convertResponseContext(ImmobileResponse response, String servicePath) throws IOException;
}
