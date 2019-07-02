package imui.jiguang.cn.imuisample.fragment.common;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
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
import com.example.sk_android.mvp.api.message.Infoexchanges;
import com.example.sk_android.mvp.application.App;

import com.example.sk_android.utils.RetrofitUtils;
import imui.jiguang.cn.imuisample.messages.MessageListActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
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
    List<ResumeListItem> resumeList = new ArrayList<ResumeListItem>();
    Integer choseIndex = null;
    int chooseTyp=0;

    public ResumeMenuFragment(){
    }



    //ImageView logo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = getArguments();

        chooseTyp=bundle.getInt("type");

        view = (LinearLayout) inflater.inflate(R.layout.resume_menu, container, false);
        menu = (ResumeMenu) getActivity();
        resumeItemContainer = view.findViewById(R.id.resume_item_container);
        this.container = container;
        this.inflater = inflater;


        TextView resume_send_button = view.findViewById(R.id.resume_send_button);
        resume_send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("====================================");
                System.out.println(choseIndex);
                if (choseIndex == null) {
                    Toast.makeText(view.getContext(),
                            "请选择你的简历",
                            Toast.LENGTH_SHORT).show();
                } else {
                    ResumeListItem choosenOne = resumeList.get(choseIndex);
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

        //请求公司信息
        RetrofitUtils requestCompany = new RetrofitUtils(getActivity(), this.getString(R.string.jobUrl));
        requestCompany.create(Infoexchanges.class)
                .getMyResume(
                        "ATTACHMENT"
                )
                .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                .subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        System.out.println("简历信息请求成功");
                        System.out.println(o.toString());
                        JSONObject json = new JSONObject(o.toString());

                        if (json.has("data")) {
                            JSONArray data = json.getJSONArray("data");

                            List<Boolean> flag = new ArrayList<Boolean>();
                            for (int i = 0; i < data.length(); i++) {
                                flag.add(false);
                            }

                            List<View> viewList = new ArrayList<View>();
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject item = data.getJSONObject(i);
                                String name = item.getString("name");
                                String mediaURL = item.getString("mediaURL");
                                Long updatedAt = item.getLong("updatedAt");
                                String mediaId = item.getString("mediaId");
                                String id = item.getString("id");

                                System.out.println(item);
                                //请求得到文件详情

                                final int i_final = i;
                                RetrofitUtils requestCompany = new RetrofitUtils(getActivity(), getString(R.string.storageUrl));
                                requestCompany.create(Infoexchanges.class)
                                        .getFileDetail(
                                                mediaId
                                        )
                                        .subscribeOn(Schedulers.io()) //被观察者 开子线程请求网络
                                        .observeOn(AndroidSchedulers.mainThread()) //观察者 切换到主线程
                                        .subscribe(new Consumer() {
                                            @Override
                                            public void accept(Object o) throws Exception {
                                                System.out.println("文件详细信息请求成功");
                                                System.out.println(o.toString());

                                                JSONObject json = new JSONObject(o.toString());

                                                String sizeStr="";
                                                Integer size = json.getInt("size") / 1024;
                                                if(size<1024){
                                                    sizeStr=size + "KB";
                                                }else{
                                                    sizeStr=(int)(size / 1024) + "M";

                                                }

                                                String attachmentType="";
                                                String mimeTypeStr = json.getString("mimeType");

                                                int mimeType = IMessage.MIMETYPE_JPG;

                                                if (mimeTypeStr.contains("pdf")) {
                                                    mimeType = IMessage.MIMETYPE_PDF;
                                                    attachmentType="pdf";
                                                } else if (mimeTypeStr.contains("word")) {
                                                    mimeType = IMessage.MIMETYPE_WORD;
                                                    attachmentType="word";

                                                } else {
                                                    attachmentType="jpg";
                                                }
//                        JSONObject detailJson=new JSONObject(detailRes);
//                        int size=detailJson.getInt("size");
//                        String attachmentType=detailJson.getString("mimeType");

//                            if(attachmentType!=null && attachmentType.contains("pdf")){
//                                mimeType= IMessage.MIMETYPE_PDF;
//                            }else if(attachmentType!=null && attachmentType.contains("word")){
//                                mimeType= IMessage.MIMETYPE_WORD;
//                            }else if(attachmentType!=null && attachmentType.contains("jpg")){
//                                mimeType= IMessage.MIMETYPE_JPG;
//                            }


                                                System.out.println(mimeType);
                                                ResumeListItem itemResum = new ResumeListItem(id, mediaId, name, updatedAt, sizeStr, mimeType, attachmentType, mediaURL,chooseTyp);

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


                                                flag.set(i_final, true);

                                                for (int j = 0; j < flag.size(); j++) {
                                                    if (flag.get(j) == false) {
                                                        break;
                                                    }
                                                    if (j == flag.size() - 1) {
                                                        System.out.println("添加视图--->");
                                                        System.out.println(viewList.size());
                                                        for (int i = 0; i < viewList.size(); i++) {
                                                            System.out.println("添加视图");
                                                            final int i_f = i;
                                                            runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    View thisView = viewList.get(i_f);
                                                                    thisView.setOnClickListener(new View.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(View v) {
                                                                            for (int j = 0; j < viewList.size(); j++) {
                                                                                viewList.get(j).findViewById(R.id.resume_item_checked).setVisibility(View.GONE);
                                                                            }
                                                                            thisView.findViewById(R.id.resume_item_checked).setVisibility(View.VISIBLE);
                                                                            choseIndex = i_f;
                                                                        }
                                                                    });
                                                                    resumeItemContainer.addView(viewList.get(i_f));
                                                                }
                                                            });
                                                        }

                                                    }
                                                }
                                            }
                                        }, new Consumer() {
                                            @Override
                                            public void accept(Object o) throws Exception {
                                                System.out.println("文件详细信息请求失败");
                                                System.out.println(o.toString());
                                                flag.set(i_final, true);
                                                for (int j = 0; j < flag.size(); j++) {
                                                    if (flag.get(j) == false) {
                                                        break;
                                                    }
                                                    if (j == flag.size() - 1) {
                                                        System.out.println("添加视图--->");
                                                        System.out.println(viewList.size());
                                                        for (int i = 0; i < viewList.size(); i++) {
                                                            System.out.println("添加视图");
                                                            final int i_f = i;
                                                            runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    View thisView = viewList.get(i_f);
                                                                    thisView.setOnClickListener(new View.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(View v) {
                                                                            for (int j = 0; j < viewList.size(); j++) {
                                                                                viewList.get(j).findViewById(R.id.resume_item_checked).setVisibility(View.GONE);
                                                                            }
                                                                            thisView.findViewById(R.id.resume_item_checked).setVisibility(View.VISIBLE);
                                                                            choseIndex = i_f;
                                                                        }
                                                                    });
                                                                    resumeItemContainer.addView(viewList.get(i_f));
                                                                }
                                                            });
                                                        }

                                                    }
                                                }
                                            }
                                        });

                            }


                        }

                    }
                }, new Consumer() {
                    @Override
                    public void accept(Object o) throws Exception {
                        System.out.println("简历信息请求失败");
                        System.out.println(o.toString());

                    }
                });


    }

}




