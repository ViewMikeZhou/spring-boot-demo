package com.hengda.screen.inducation;


import com.hengda.screen.model.ScreenRequest;
import com.hengda.screen.model.response.ScreenResponse;

import java.util.List;

public  interface ScreenHelper {
     void init() throws Exception;
     ScreenResponse send(ScreenRequest request);
     ScreenResponse send(List<ScreenRequest> requestList);
}
