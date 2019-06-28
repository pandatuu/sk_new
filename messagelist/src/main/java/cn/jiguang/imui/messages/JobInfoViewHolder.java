package cn.jiguang.imui.messages;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import cn.jiguang.imui.BuildConfig;
import cn.jiguang.imui.R;
import cn.jiguang.imui.commons.models.IMessage;
import cn.jiguang.imui.model.JobInfoModel;
import cn.jiguang.imui.view.RoundImageView;
import cn.jiguang.imui.view.RoundTextView;

public class JobInfoViewHolder<MESSAGE extends IMessage> extends BaseMessageViewHolder<MESSAGE>
        implements MsgListAdapter.DefaultMessageViewHolder {

    private boolean mIsSender;


    private TextView userPositionName;
    private TextView companyName;


    private ImageView haveTraffic;
    private ImageView haveSocialInsurance;
    private ImageView haveClub;
    private ImageView haveCanteen;
    private ImageView  isNew;

    private TextView  showSalaryMinToMax;
    private TextView  salaryType;
    private TextView  address;
    private TextView  workingExperience;
    private TextView  educationalBackground;
    private TextView  userPositionNameAndUserName;

    public JobInfoViewHolder(View itemView, boolean isSender) {
        super(itemView);
        this.mIsSender = isSender;



        userPositionName =  itemView.findViewById(R.id.userPositionName);
        companyName =  itemView.findViewById(R.id.companyName);
        showSalaryMinToMax =  itemView.findViewById(R.id.showSalaryMinToMax);
        salaryType=  itemView.findViewById(R.id.salaryType);
        address=  itemView.findViewById(R.id.address);
        workingExperience=  itemView.findViewById(R.id.workingExperience);
        educationalBackground=  itemView.findViewById(R.id.educationalBackground);
        userPositionNameAndUserName=  itemView.findViewById(R.id.userPositionNameAndUserName);



        haveTraffic =  itemView.findViewById(R.id.haveTraffic);
        haveSocialInsurance =  itemView.findViewById(R.id.haveSocialInsurance);
        haveClub =  itemView.findViewById(R.id.haveClub);
        haveCanteen =  itemView.findViewById(R.id.haveCanteen);
        isNew=  itemView.findViewById(R.id.isNew);



    }

    @Override
    public void onBind(final MESSAGE message) {


        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        JobInfoModel model= message.getJsobInfo();

        if(model!=null){
            userPositionName.setText(model.getUserPositionName());
            companyName.setText(model.getCompanyName());
            showSalaryMinToMax.setText(model.getShowSalaryMinToMax());
            salaryType.setText(model.getSalaryType());


            if(!model.getHaveCanteen()){
                haveCanteen.setVisibility(View.GONE);
            }else{
                haveCanteen.setVisibility(View.VISIBLE);
            }

            if(!model.getHaveClub()){
                haveClub.setVisibility(View.GONE);
            }else{
                haveClub.setVisibility(View.VISIBLE);
            }


            if(!model.getHaveSocialInsurance()){
                haveSocialInsurance.setVisibility(View.GONE);
            }else{
                haveSocialInsurance.setVisibility(View.VISIBLE);
            }


            if(!model.getHaveTraffic()){
                haveTraffic.setVisibility(View.GONE);
            }else{
                haveTraffic.setVisibility(View.VISIBLE);
            }


            if(model.getAddress()!=null && !"".equals(model.getAddress())){
                address.setText(model.getAddress());
            }

            if(model.getWorkingExperience()!=null && !"".equals(model.getWorkingExperience())){
                workingExperience.setText(model.getWorkingExperience());
            }

            if(model.getEducationalBackground()!=null && !"".equals(model.getEducationalBackground())){
                educationalBackground.setText(model.getEducationalBackground());
            }

        }



    }

    @Override
    public void applyStyle(MessageListStyle style) {
    }


}