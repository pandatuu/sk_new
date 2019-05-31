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

import java.util.ArrayList;
import java.util.List;

import imui.jiguang.cn.imuisample.models.ResumeListItem;

public class ResumeMenuFragment extends Fragment {

    private  ResumeMenu menu;
    LinearLayout view;
    LinearLayout resumeItemContainer;
    //ImageView logo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = (LinearLayout) inflater.inflate(R.layout.resume_menu, container, false);
        menu= (ResumeMenu) getActivity();
        resumeItemContainer=view.findViewById(R.id.resume_item_container);

        List list=new ArrayList<ResumeListItem>();
        ResumeListItem item1=new ResumeListItem("視覚デザイン履歴書1","2019.03.01掲載","1.3MB",ResumeListItem.WORD);
        list.add(item1);
        ResumeListItem item2=new ResumeListItem("視覚デザイン履歴書2","2019.03.01掲載","1.3MB",ResumeListItem.PDF);
        list.add(item2);
        ResumeListItem item3=new ResumeListItem("視覚デザイン履歴書3","2019.03.01掲載","1.3MB",ResumeListItem.JPG);
        list.add(item3);


        for(int i=0;i<list.size();i++){

            View item = inflater.inflate(R.layout.resume_menu_item, container, false);
            ((TextView)item.findViewById(R.id.resume_item_title)).setText(((ResumeListItem)list.get(i)).getTitle());
            ((TextView)item.findViewById(R.id.resume_item_size)).setText(((ResumeListItem)list.get(i)).getSize());
            ((TextView)item.findViewById(R.id.resume_item_time)).setText(((ResumeListItem)list.get(i)).getTime());
            if(((ResumeListItem)list.get(i)).getType()==ResumeListItem.WORD){
                ((ImageView)item.findViewById(R.id.resume_item_logo)).setImageResource(R.drawable.word_icon);
            }else if(((ResumeListItem)list.get(i)).getType()==ResumeListItem.PDF){
                ((ImageView)item.findViewById(R.id.resume_item_logo)).setImageResource(R.drawable.ico_pdf);
            }else if(((ResumeListItem)list.get(i)).getType()==ResumeListItem.JPG){
                ((ImageView)item.findViewById(R.id.resume_item_logo)).setImageResource(R.drawable.jpg_icon);
            }

            resumeItemContainer.addView(item);
        }


        for(int i=0;i<list.size();i++){
            final int index=i;
            resumeItemContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {
                    menu.resumeMenuOnclick(index);
                }
            });
        }





        return view;
    }


    public interface  ResumeMenu{
        public void resumeMenuOnclick(int i);
    }

}




