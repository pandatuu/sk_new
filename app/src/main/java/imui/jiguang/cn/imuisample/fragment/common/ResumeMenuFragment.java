package imui.jiguang.cn.imuisample.fragment.common;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.widget.Toast;
import cn.jiguang.imui.commons.models.IMessage;
import com.example.sk_android.R;
import com.example.sk_android.mvp.application.App;

import imui.jiguang.cn.imuisample.messages.MessageListActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import imui.jiguang.cn.imuisample.models.ResumeListItem;
import imui.jiguang.cn.imuisample.utils.Http;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.facebook.react.bridge.UiThreadUtil.runOnUiThread;

public class ResumeMenuFragment extends Fragment {

    private ResumeMenu menu;
    LinearLayout view;
    LinearLayout resumeItemContainer;
    ViewGroup container;
    LayoutInflater inflater;
    List<ResumeListItem> resumeList=new ArrayList<ResumeListItem>();
    Integer choseIndex=null;

    //ImageView logo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = (LinearLayout) inflater.inflate(R.layout.resume_menu, container, false);
        menu = (ResumeMenu) getActivity();
        resumeItemContainer = view.findViewById(R.id.resume_item_container);
        this.container = container;
        this.inflater = inflater;


        TextView resume_send_button=view.findViewById(R.id.resume_send_button);
        resume_send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("====================================");
                System.out.println(choseIndex);
                if(choseIndex==null){
                    Toast.makeText(view.getContext(),
                            "请选择你的简历",
                            Toast.LENGTH_SHORT).show();
                }else{
                    ResumeListItem choosenOne=resumeList.get(choseIndex);
                    menu.resumeMenuOnclick(choosenOne);
                }
            }
        });


        new Thread(new Runnable() {
            @Override
            public void run() {
                getdata();
            }
        }).start();

        return view;
    }


    public interface ResumeMenu {
        public void resumeMenuOnclick(ResumeListItem choosenOne);
    }


    public void getdata() {

        try {
            //请求得到简历列表
            String res = Http.get("https://job.sk.cgland.top/api/v1/resumes/?type=ATTACHMENT");
            if (res != null) {
                JSONObject resJson = new JSONObject(res);

                if (resJson.has("data")) {
                    JSONArray data = resJson.getJSONArray("data");

                    List<View> viewList=new ArrayList<View>();
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        String name = item.getString("name");
                        String mediaURL = item.getString("mediaURL");
                        Long updatedAt = item.getLong("updatedAt");
                        String mediaId = item.getString("mediaId");
                        System.out.println(item);
                        //请求得到文件详情
                        String detailRes = Http.get("https://storage.sk.cgland.top/api/v1/storage/" + mediaId);
                        System.out.println("**************************************");
                        System.out.println(detailRes);
                        if (true || detailRes != null) {
//                        JSONObject detailJson=new JSONObject(detailRes);
//                        int size=detailJson.getInt("size");
//                        String attachmentType=detailJson.getString("mimeType");
                            int mimeType = IMessage.MIMETYPE_JPG;

//                            if(attachmentType!=null && attachmentType.contains("pdf")){
//                                mimeType= IMessage.MIMETYPE_PDF;
//                            }else if(attachmentType!=null && attachmentType.contains("word")){
//                                mimeType= IMessage.MIMETYPE_WORD;
//                            }else if(attachmentType!=null && attachmentType.contains("jpg")){
//                                mimeType= IMessage.MIMETYPE_JPG;
//                            }

                            int size = 20;
                            System.out.println(mimeType);
                            ResumeListItem itemResum = new ResumeListItem(name, updatedAt, size + "KB", mimeType,"","");

                            View itemContainer = inflater.inflate(R.layout.resume_menu_item, container, false);
                            ((TextView) itemContainer.findViewById(R.id.resume_item_title)).setText(itemResum.getTitle());
                            ((TextView) itemContainer.findViewById(R.id.resume_item_size)).setText(itemResum.getSize());
                            ((TextView) itemContainer.findViewById(R.id.resume_item_time)).setText(itemResum.getTime());
                            if (itemResum.getType() == IMessage.MIMETYPE_WORD) {
                                ((ImageView) itemContainer.findViewById(R.id.resume_item_logo)).setImageResource(R.drawable.word_icon);
                            } else if (itemResum.getType() == IMessage.MIMETYPE_PDF) {
                                ((ImageView) itemContainer.findViewById(R.id.resume_item_logo)).setImageResource(R.drawable.ico_pdf);
                            } else if (itemResum.getType() == IMessage.MIMETYPE_JPG) {
                                ((ImageView) itemContainer.findViewById(R.id.resume_item_logo)).setImageResource(R.drawable.jpg_icon);
                            }

                            resumeList.add(itemResum);
                            viewList.add(itemContainer);

                        }


                    }

                    for(int i =0;i<viewList.size();i++){
                        System.out.println("====================================================================================================");
                        final int  i_f=i;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                View thisView=viewList.get(i_f);
                                thisView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        for(int j =0;j<viewList.size();j++){
                                            viewList.get(j ).findViewById(R.id.resume_item_checked).setVisibility(View.GONE);
                                        }
                                        thisView.findViewById(R.id.resume_item_checked).setVisibility(View.VISIBLE);
                                        choseIndex=i_f;
                                    }
                                });
                                resumeItemContainer.addView(viewList.get(i_f));
                            }
                        });
                    }

                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}




