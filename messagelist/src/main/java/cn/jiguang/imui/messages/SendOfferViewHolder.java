package cn.jiguang.imui.messages;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import cn.jiguang.imui.R;
import cn.jiguang.imui.commons.models.IMessage;
import cn.jiguang.imui.view.RoundImageView;
import cn.jiguang.imui.view.RoundTextView;


public class SendOfferViewHolder<MESSAGE extends IMessage>
        extends BaseMessageViewHolder<MESSAGE>
        implements MsgListAdapter.DefaultMessageViewHolder {

    private TextView sendOffer_container;
    private final RoundImageView mImageAvatar;
    private RoundTextView mDateTv;

    public SendOfferViewHolder(View itemView, boolean isSender) {
        super(itemView);
        sendOffer_container=itemView.findViewById(R.id.sendOffer_container);
        mImageAvatar = (RoundImageView) itemView.findViewById(R.id.aurora_iv_msgitem_avatar);
        mDateTv =  itemView.findViewById(R.id.aurora_tv_msgitem_date);

    }

    @Override
    public void onBind(MESSAGE message) {

        sendOffer_container.setText(message.getText());

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

    }
}
