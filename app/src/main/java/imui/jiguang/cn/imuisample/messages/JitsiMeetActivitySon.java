package imui.jiguang.cn.imuisample.messages;


import android.os.Bundle;
import android.util.Log;
import org.jitsi.meet.sdk.JitsiMeetActivity;

import java.util.Map;


class JitsiMeetActivitySon extends JitsiMeetActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onBackPressed(){


        System.out.println("xxxxxxxxxxxxxxxxbbbbbbbbbbbbbbbbbbbb");
        System.out.println("xxxxxxxxxxxxxxxxbbbbbbbbbbbbbbbbbbbb");


    }

    @Override
    public void onConferenceTerminated(Map<String, Object> data) {
        Log.d(TAG, "Conference terminated:惺惺惜惺惺寻寻寻寻寻寻寻寻寻寻寻寻寻寻寻 " + data);
        this.finish();
    }



}
