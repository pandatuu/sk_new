package cn.jiguang.imui.messages;

import android.text.TextUtils;
import android.view.View;

import cn.jiguang.imui.R;
import cn.jiguang.imui.commons.models.IMessage;
import cn.jiguang.imui.view.RoundImageView;
import cn.jiguang.imui.view.RoundTextView;

public class CommunicationViewHolder<MESSAGE extends IMessage> extends BaseMessageViewHolder<MESSAGE>
        implements MsgListAdapter.DefaultMessageViewHolder {

    private boolean mIsSender;
    private int icoType;
    private int showType;
    private final RoundImageView mImageAvatar;
    private RoundTextView mDateTv;

    public CommunicationViewHolder(View itemView, boolean isSender,int showType,int icoType) {
        super(itemView);
        this.mIsSender = isSender;
        this.icoType=icoType;
        this.showType=showType;
        mImageAvatar = (RoundImageView) itemView.findViewById(R.id.aurora_iv_msgitem_avatar);
        mDateTv =  itemView.findViewById(R.id.aurora_tv_msgitem_date);


    }

    @Override
    public void onBind(final MESSAGE message) {
        String timeString = message.getTimeString();
        boolean isAvatarExists = message.getFromUser().getAvatarFilePath() != null
                && !message.getFromUser().getAvatarFilePath().isEmpty();
        if (mImageLoader != null) {
            if (isAvatarExists) {
                mImageLoader.loadAvatarImage(mImageAvatar, message.getFromUser().getAvatarFilePath());
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

        mImageAvatar.setBorderRadius(style.getAvatarRadius());

    }



}