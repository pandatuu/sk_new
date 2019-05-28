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

import imui.jiguang.cn.imuisample.R;

public class DropMenuFragment extends Fragment {

    private  DropMenu dropMenu;
    LinearLayout view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = (LinearLayout) inflater.inflate(R.layout.drop_down_menu, container, false);
        dropMenu= (DropMenu) getActivity();

        String[] str={"気が合う","まあまあ","その他","マークを取り消し"};
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
                    t.setTextColor(R.color.theme);
                    image.setVisibility(View.VISIBLE);
                    dropMenu.dropMenuOnclick(index);

                }
            });
        }





        return view;
    }


    public interface  DropMenu{
        public void dropMenuOnclick(int i);
    }

}




