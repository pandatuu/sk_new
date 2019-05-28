package cn.jiguang.imui.messages;

import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.jiguang.imui.R;
import cn.jiguang.imui.commons.models.IMessage;
import cn.jiguang.imui.view.RoundImageView;
import cn.jiguang.imui.view.RoundTextView;

public class ExchangeAccountResultHolder<MESSAGE extends IMessage> extends BaseMessageViewHolder<MESSAGE>
        implements MsgListAdapter.DefaultMessageViewHolder {

    private boolean mIsSender;
    private int icoType;
    private int showType;
    private final RoundImageView mImageAvatar_receive;
    private final RoundImageView mImageAvatar_send;

    private RoundTextView mDateTv;
    private ImageView communication_type;
    private TextView communication_content;
    private LinearLayout communication_parent;

    private TextView exchangeAccountCenterButton;

    public ExchangeAccountResultHolder(View itemView, boolean isSender, int showType, int icoType) {
        super(itemView);
        this.mIsSender = isSender;
        this.icoType=icoType;
        this.showType=showType;
        mImageAvatar_receive = (RoundImageView) itemView.findViewById(R.id.aurora_iv_msgitem_avatar_receive);
        mImageAvatar_send = (RoundImageView) itemView.findViewById(R.id.aurora_iv_msgitem_avatar_send);

        mDateTv =  itemView.findViewById(R.id.aurora_tv_msgitem_date);
        communication_type =  itemView.findViewById(R.id.communication_type);
        communication_content=  itemView.findViewById(R.id.communication_content);

        communication_parent=  itemView.findViewById(R.id.communication_parent);

        exchangeAccountCenterButton=itemView.findViewById(R.id.exchangeAccountCenterButton);

    }

    @Override
    public void onBind(final MESSAGE message) {

        if(icoType==MsgListAdapter.PHONE){
            communication_type.setImageResource(R.drawable.ico_phone);
            exchangeAccountCenterButton.setText("呼び出す");
        }else if(icoType==MsgListAdapter.LINE){
            communication_type.setImageResource(R.drawable.ico_line);
            exchangeAccountCenterButton.setText("複製番号");
        }else if(icoType==MsgListAdapter.VIDEO){
            communication_type.setImageResource(R.drawable.ico_video);
        }


        communication_content.setText(message.getText());

        String timeString = message.getTimeString();
        boolean isAvatarExists = message.getFromUser().getAvatarFilePath() != null
                && !message.getFromUser().getAvatarFilePath().isEmpty();


        if (mImageLoader != null) {
            if (isAvatarExists) {
                if(showType==MsgListAdapter.RECEIVE){
                    communication_parent.setGravity(Gravity.LEFT);
                    mImageAvatar_send.setVisibility(View.GONE);
                    mImageAvatar_receive.setVisibility(View.VISIBLE);
                    mImageLoader.loadAvatarImage(mImageAvatar_receive, message.getFromUser().getAvatarFilePath());
                }
                else{
                    communication_parent.setGravity(Gravity.RIGHT);
                    mImageAvatar_receive.setVisibility(View.GONE);
                    mImageAvatar_send.setVisibility(View.VISIBLE);
                    mImageLoader.loadAvatarImage(mImageAvatar_send, message.getFromUser().getAvatarFilePath());
                }
            }
        }



        mDateTv.setVisibility(View.VISIBLE);
        if (timeString != null && !TextUtils.isEmpty(timeString)) {
            mDateTv.setText(timeString);
        } else {
            mDateTv.setVisibility(View.GONE);
        }
    }


    @Override
    public void applyStyle(MessageListStyle style) {


        mDateTv.setTextSize(style.getDateTextSize());
        mDateTv.setTextColor(style.getDateTextColor());
        mDateTv.setPadding(style.getDatePaddingLeft(), style.getDatePaddingTop(), style.getDatePaddingRight(),
                style.getDatePaddingBottom());
        mDateTv.setBgCornerRadius(style.getDateBgCornerRadius());
        mDateTv.setBgColor(style.getDateBgColor());

        mImageAvatar_receive.setBorderRadius(style.getAvatarRadius());
        mImageAvatar_send.setBorderRadius(style.getAvatarRadius());


    }



}