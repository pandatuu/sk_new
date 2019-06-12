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

import com.example.sk_android.R;
import com.example.sk_android.mvp.application.App;

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

public class ResumeMenuFragment extends Fragment {

    private  ResumeMenu menu;
    LinearLayout view;
    LinearLayout resumeItemContainer;
    ViewGroup container;
    LayoutInflater inflater;
    //ImageView logo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = (LinearLayout) inflater.inflate(R.layout.resume_menu, container, false);
        menu= (ResumeMenu) getActivity();
        resumeItemContainer=view.findViewById(R.id.resume_item_container);
        this.container=container;
        this.inflater=inflater;

//        List list=new ArrayList<ResumeListItem>();
//        ResumeListItem item1=new ResumeListItem("視覚デザイン履歴書1","2019.03.01掲載","1.3MB",ResumeListItem.WORD);
//        list.add(item1);
//        ResumeListItem item2=new ResumeListItem("視覚デザイン履歴書2","2019.03.01掲載","1.3MB",ResumeListItem.PDF);
//        list.add(item2);
//        ResumeListItem item3=new ResumeListItem("視覚デザイン履歴書3","2019.03.01掲載","1.3MB",ResumeListItem.JPG);
//        list.add(item3);
//
//
//        for(int i=0;i<list.size();i++){
//
//            View item = inflater.inflate(R.layout.resume_menu_item, container, false);
//            ((TextView)item.findViewById(R.id.resume_item_title)).setText(((ResumeListItem)list.get(i)).getTitle());
//            ((TextView)item.findViewById(R.id.resume_item_size)).setText(((ResumeListItem)list.get(i)).getSize());
//            ((TextView)item.findViewById(R.id.resume_item_time)).setText(((ResumeListItem)list.get(i)).getTime());
//            if(((ResumeListItem)list.get(i)).getType()==ResumeListItem.WORD){
//                ((ImageView)item.findViewById(R.id.resume_item_logo)).setImageResource(R.drawable.word_icon);
//            }else if(((ResumeListItem)list.get(i)).getType()==ResumeListItem.PDF){
//                ((ImageView)item.findViewById(R.id.resume_item_logo)).setImageResource(R.drawable.ico_pdf);
//            }else if(((ResumeListItem)list.get(i)).getType()==ResumeListItem.JPG){
//                ((ImageView)item.findViewById(R.id.resume_item_logo)).setImageResource(R.drawable.jpg_icon);
//            }
//
//            resumeItemContainer.addView(item);
//        }
//
//
//        for(int i=0;i<list.size();i++){
//            final int index=i;
//            resumeItemContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
//                @SuppressLint("ResourceAsColor")
//                @Override
//                public void onClick(View v) {
//                    menu.resumeMenuOnclick(index);
//                }
//            });
//        }
//

        new Thread(new Runnable() {
            @Override
            public void run() {
                getdata();
            }
        }).start();

        return view;
    }


    public interface  ResumeMenu{
        public void resumeMenuOnclick(int i);
    }


    public void getdata(){

        try {
            String res= Http.get("https://job.sk.cgland.top/api/v1/resumes/?type=ATTACHMENT");
            JSONObject resJson=new JSONObject(res);
            if(resJson.has("data")){
                JSONArray data=resJson.getJSONArray("data");

                for(int i=0 ;i<data.length();i++){

                    JSONObject item=data.getJSONObject(i);
                    String name=item.getString("name");
                    String mediaURL=item.getString("mediaURL");
                    String updatedAt=item.getString("updatedAt");
                    String mediaId=item.getString("mediaId");
                    System.out.println(item);
                    String detailRes= Http.get("https://storage.sk.cgland.top/api/v1/storage/"+mediaId);
                    System.out.println("**************************************");
                    System.out.println(detailRes);
                    JSONObject detailJson=new JSONObject(detailRes);
                    int size=detailJson.getInt("size");
                    String mimeType=detailJson.getString("mimeType");
                    System.out.println(mimeType);
                    ResumeListItem itemResum=new ResumeListItem(name,updatedAt,size+"",ResumeListItem.WORD);

                    View itemContainer = inflater.inflate(R.layout.resume_menu_item, container, false);
                    ((TextView)itemContainer.findViewById(R.id.resume_item_title)).setText(itemResum.getTitle());
                    ((TextView)itemContainer.findViewById(R.id.resume_item_size)).setText(itemResum.getSize());
                    ((TextView)itemContainer.findViewById(R.id.resume_item_time)).setText(itemResum.getTime());
                    if(itemResum.getType()==ResumeListItem.WORD){
                        ((ImageView)itemContainer.findViewById(R.id.resume_item_logo)).setImageResource(R.drawable.word_icon);
                    }else if(itemResum.getType()==ResumeListItem.PDF){
                        ((ImageView)itemContainer.findViewById(R.id.resume_item_logo)).setImageResource(R.drawable.ico_pdf);
                    }else if(itemResum.getType()==ResumeListItem.JPG){
                        ((ImageView)itemContainer.findViewById(R.id.resume_item_logo)).setImageResource(R.drawable.jpg_icon);
                    }

                    resumeItemContainer.addView(itemContainer);

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

}




