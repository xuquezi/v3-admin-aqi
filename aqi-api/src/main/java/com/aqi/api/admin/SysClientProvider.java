package com.aqi.api.admin;

import com.aqi.api.response.SysClientRes;

public interface SysClientProvider {

    SysClientRes getClientByKey(String clientKey);

}
