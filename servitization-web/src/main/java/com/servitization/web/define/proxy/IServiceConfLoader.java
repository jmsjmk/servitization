package com.servitization.web.define.proxy;

import java.util.List;

public interface IServiceConfLoader {
    List<ServiceModule> loadServiceConf();

    List<ServiceModule> loadServiceConf(int mod);

    int getMax_Proxy_Index();
}
