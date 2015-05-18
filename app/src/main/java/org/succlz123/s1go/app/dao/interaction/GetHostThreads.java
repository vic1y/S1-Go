package org.succlz123.s1go.app.dao.interaction;

import org.succlz123.s1go.app.bean.hostthreads.HotThreadsObject;
import org.succlz123.s1go.app.dao.api.MyOkHttp;
import org.succlz123.s1go.app.dao.helper.S1Url;

import java.util.HashMap;

/**
 * Created by fashi on 2015/4/12.
 */
public class GetHostThreads {

    public static HotThreadsObject getHostPost(HashMap<String, String> paramss) {
        String url = S1Url.HOT_POST;
        String json = MyOkHttp.getInstance().doGet(url,paramss);
        HotThreadsObject hotThreadsObject = HotThreadsObject.parseJson(json);
        return hotThreadsObject;
    }
}
