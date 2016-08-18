package com.servitization.proxy;

import com.servitization.embedder.immobile.ImmobileResponse;

import java.io.IOException;

/**
 * Created by jiamingku on 16/7/15.
 */
public interface IResponseConvert {
    void convertResponseContext(ImmobileResponse response, String servicePath) throws IOException;
}
