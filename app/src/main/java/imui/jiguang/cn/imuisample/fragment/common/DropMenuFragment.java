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


public class DropMenuFragment extends Fragment {

    private  DropMenu dropMenu;
    LinearLayout view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = (LinearLayout) inflater.inflate(R.layout.drop_down_menu, container, false);
        dropMenu= (DropMenu) getActivity();

        String[] str={"聊得来","还不错","不考虑","其他"};
        for(int i=0;i<4;i++){

            View item = inflater.inflate(R.layout.drop_down_menu_item, container, false);
            ((TextView)item.findViewById(R.id.menu_item_text)).setText(str[i]);
            if(i==2){
                ((ImageView)item.findViewById(R.id.menu_item_logo)).setVisibility(View.VISIBLE);
            }
            view.addView(item);
        }


        for(int i=0;i<4;i++){
            final int index=i;
            view.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {
                    for(int i=0;i<view.getChildCount();i++){
                        TextView t=view.getChildAt(i).findViewById(R.id.menu_item_text);
                        t.setTextColor(R.color.black20);

                        ImageView image=view.getChildAt(i).findViewById(R.id.menu_item_logo);
                        image.setVisibility(View.GONE);
                    }
                    TextView t=v.findViewById(R.id.menu_item_text);
                    ImageView image=v.findViewById(R.id.menu_item_logo);
                    t.setTextColor(R.color.themeColor);
                    image.setVisibility(View.VISIBLE);
                    //接口参数
                    int param=index+4;
                    if(index==3){
                        param=0;
                    }
                    dropMenu.dropMenuOnclick(param);

                }
            });
        }





        return view;
    }


    public interface  DropMenu{
         void dropMenuOnclick(int i);
    }

}




