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
import cn.jiguang.imui.view.RoundImageView;
import cn.jiguang.imui.view.RoundTextView;

public class JobInfoViewHolder<MESSAGE extends IMessage> extends BaseMessageViewHolder<MESSAGE>
        implements MsgListAdapter.DefaultMessageViewHolder {

    private boolean mIsSender;

    public JobInfoViewHolder(View itemView, boolean isSender) {
        super(itemView);
        this.mIsSender = isSender;

    }

    @Override
    public void onBind(final MESSAGE message) {
    }

    @Override
    public void applyStyle(MessageListStyle style) {
    }



}