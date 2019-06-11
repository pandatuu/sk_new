package imui.jiguang.cn.imuisample.fragment.common;


import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sk_android.R;


public class DropMenuFragment extends Fragment {

    private  DropMenu dropMenu;
    LinearLayout view;
    int groupId=-100;

    public DropMenuFragment(){

    }

    @SuppressLint("ValidFragment")
    public DropMenuFragment(Integer groupId){
        if(groupId!=null){
            this.groupId=groupId;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = (LinearLayout) inflater.inflate(R.layout.drop_down_menu, container, false);
        dropMenu= (DropMenu) getActivity();




        String[] str={"聊得来","还不错","不考虑"};
        for(int i=0;i<str.length;i++){

            View item = inflater.inflate(R.layout.drop_down_menu_item, container, false);
            TextView tv= ((TextView)item.findViewById(R.id.menu_item_text));
            tv.setText(str[i]);

            if(i==groupId){
                (item.findViewById(R.id.menu_item_logo)).setVisibility(View.VISIBLE);
                tv.setTextColor(ContextCompat.getColor(item.getContext(),R.color.themeColor));

            }
            view.addView(item);
        }


        for(int i=0;i<str.length;i++){
            final int index=i;
            view.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int j=0;j<view.getChildCount();j++){

                        TextView t=view.getChildAt(j).findViewById(R.id.menu_item_text);
                        t.setTextColor(ContextCompat.getColor(view.getContext(),R.color.black20));

                        ImageView image=view.getChildAt(j).findViewById(R.id.menu_item_logo);
                        image.setVisibility(View.GONE);
                    }
                    TextView t=view.getChildAt(index).findViewById(R.id.menu_item_text);
                    ImageView image=view.getChildAt(index).findViewById(R.id.menu_item_logo);
                    t.setTextColor(ContextCompat.getColor(view.getContext(),R.color.themeColor));
                    image.setVisibility(View.VISIBLE);
                    dropMenu.dropMenuOnclick(index);
                }
            });
        }





        return view;
    }


    public interface  DropMenu{
         void dropMenuOnclick(int i);
    }

}




