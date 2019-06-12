package imui.jiguang.cn.imuisample.utils;

import com.example.sk_android.mvp.application.App;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Http {

    static String authorization="";
    static{
        App application = App.Companion.getInstance();
        authorization= "Bearer " + application.getToken();
    }

    public static String get(String url){
        final OkHttpClient client = new OkHttpClient();
        Response response=null;
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("Authorization", authorization)
                    .get()
                    .build();


            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                //请求成功
                System.out.println("请求简历列表接口成功!");
            } else {
                System.out.println("请求简历列表接口失败!");
            }
            String res=response.body().string();
            response.close();
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            if(response!=null){
                response.close();
            }
        }
        return null;
    }


}