package imui.jiguang.cn.imuisample.messages;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.sk_android.R;
import com.example.sk_android.mvp.api.message.ChatApi;
import com.example.sk_android.mvp.application.App;
import com.example.sk_android.mvp.listener.message.RecieveMessageListener;
import com.example.sk_android.mvp.view.activity.message.MessageChatRecordActivity;
import com.example.sk_android.utils.MimeType;
import com.example.sk_android.utils.RetrofitUtils;
import com.example.sk_android.utils.UploadPic;
import com.google.common.net.MediaType;
import com.google.gson.JsonObject;
import com.jaeger.library.StatusBarUtil;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.security.auth.DestroyFailedException;

import cn.jiguang.imui.chatinput.ChatInputView;
import cn.jiguang.imui.chatinput.listener.OnCameraCallbackListener;
import cn.jiguang.imui.chatinput.listener.OnMenuClickListener;
import cn.jiguang.imui.chatinput.listener.RecordVoiceListener;
import cn.jiguang.imui.chatinput.model.FileItem;
import cn.jiguang.imui.chatinput.model.VideoItem;
import cn.jiguang.imui.commons.ImageLoader;
import cn.jiguang.imui.commons.models.IMessage;
import cn.jiguang.imui.messages.MessageList;
import cn.jiguang.imui.messages.MsgListAdapter;
import cn.jiguang.imui.messages.ptr.PtrHandler;
import cn.jiguang.imui.messages.ptr.PullToRefreshLayout;
import cn.jiguang.imui.messages.ViewHolderController;
import imui.jiguang.cn.imuisample.fragment.common.DropMenuFragment;
import imui.jiguang.cn.imuisample.fragment.common.ResumeMenuFragment;
import imui.jiguang.cn.imuisample.fragment.common.ShadowFragment;
import imui.jiguang.cn.imuisample.models.DefaultUser;
import imui.jiguang.cn.imuisample.models.MyMessage;
import imui.jiguang.cn.imuisample.utils.AsyncHttpClientCallback;
import imui.jiguang.cn.imuisample.utils.HttpBaseUtil;
import imui.jiguang.cn.imuisample.utils.HttpClientUtil;
import imui.jiguang.cn.imuisample.views.ChatView;
import io.github.sac.Ack;
import io.github.sac.Emitter;
import io.github.sac.Socket;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static java.sql.DriverManager.println;

@SuppressWarnings("ALL")
public class MessageListActivity extends Activity implements View.OnTouchListener,
        EasyPermissions.PermissionCallbacks, SensorEventListener, ShadowFragment.ShadowScreen, DropMenuFragment.DropMenu, ResumeMenuFragment.ResumeMenu {

    private final static String TAG = "MessageListActivity";
    private final int RC_RECORD_VOICE = 0x0001;
    private final int RC_CAMERA = 0x0002;
    private final int RC_PHOTO = 0x0003;

    int LINE_EXCHANGE = 1;
    int PHONE_EXCHANGE = 2;
    int SCROLL = 1;

    private ChatView mChatView;
    private MsgListAdapter<MyMessage> mAdapter;
    private List<MyMessage> mData;
    private InputMethodManager mImm;
    private Window mWindow;
    private HeadsetDetectReceiver mReceiver;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private PowerManager mPowerManager;
    private PowerManager.WakeLock mWakeLock;
    private PullToRefreshLayout pullToRefreshLayout;
    private LinearLayout message_middle_select_bar4;
    private LinearLayout message_middle_select_bar3;
    private LinearLayout message_middle_select_bar2;
    private LinearLayout message_middle_select_bar1;
    private LinearLayout topPart;
    private LinearLayout bottomPartContainer;
    private MessageList msg_list;


    boolean isInitHistory = true;
    boolean isFirstRequestHistory = true;


    JSONArray historyMessage;
    String lastShowedMessageId;
    String topBlankMessageId = null;
    private ArrayList<String> mPathList = new ArrayList<>();
    private ArrayList<String> mMsgIdList = new ArrayList<>();

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            MyMessage communicationResult1 = null;
            if (msg.what == LINE_EXCHANGE) {
                communicationResult1 = new MyMessage("line交換は成功しました。●●様の電話番号は：13888888888", IMessage.MessageType.RECEIVE_ACCOUNT_LINE.ordinal());
            } else if (msg.what == PHONE_EXCHANGE) {
                communicationResult1 = new MyMessage("電話番号交換は成功しました。●●様の電話番号は：13888888888", IMessage.MessageType.RECEIVE_ACCOUNT_PHONE.ordinal());
            }
            communicationResult1.setUserInfo(new DefaultUser("1", "Ironman", "R.drawable.deadpool"));
            mAdapter.addToStart(communicationResult1, true);

        }
    };

    ShadowFragment fragmentShadow = null;
    DropMenuFragment dropMenuFragment = null;

    ResumeMenuFragment resumeMenuFragment = null;

    JSONObject sendMessageModel = new JSONObject();

    App application;
    Socket socket;
    String messageId = "";

    Socket.Channel channelSend = null;

    String MY_ID = "589daa8b-79bd-4cae-bf67-765e6e786a72";
    String HIS_ID = "";


    @Override
    protected void onStart() {
        super.onStart();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //设置已读
                setAsRead(HIS_ID);
                //加载历史
                loadNextPage(null);
            }
        }, 10);

        Toolbar toolbar = findViewById(R.id.message_toolBar);
        setActionBar(toolbar);
        getActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//返回
            }
        });

        StatusBarUtil.setTranslucentForImageView(this, 0, toolbar);
        getWindow().getDecorView()
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initMessageChannel();

        this.mImm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mWindow = getWindow();
        registerProximitySensorListener();

        //主聊天控件
        mChatView = (ChatView) findViewById(R.id.chat_view);
        mChatView.initModule();
        mChatView.setOnTouchListener(this);
        initChatViewMenuClickListener();
        initChatViewRecordVoiceListener();
        initChatViewCameraCallbackListener();
        mChatView.getChatInputView().
                getInputView().
                setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        scrollToBottom();
                        return false;
                    }
                });
        mChatView.getSelectAlbumBtn().
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });


        pullToRefreshLayout = findViewById(R.id.pull_to_refresh_layout);
        topPart = findViewById(R.id.topPart);
        bottomPartContainer = findViewById(R.id.bottomPartContainer);

        msg_list = findViewById(R.id.msg_list);
        msg_list.setScrollToTopListener(new MessageList.ScrollToTopListener() {
            @Override
            public void hitTop() {
                loadNextPage(lastShowedMessageId);
            }
        });

        //顶部菜单
        message_middle_select_bar1 = findViewById(R.id.message_middle_select_bar1);
        message_middle_select_bar2 = findViewById(R.id.message_middle_select_bar2);
        message_middle_select_bar3 = findViewById(R.id.message_middle_select_bar3);
        message_middle_select_bar4 = findViewById(R.id.message_middle_select_bar4);
        //初始化顶部菜单的点击事件
        initTopMenuClickListener();

        mData = getMessages();
        initMsgAdapter();
        mReceiver = new HeadsetDetectReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(mReceiver, intentFilter);
    }


    @SuppressLint("InvalidWakeLockTag")
    private void registerProximitySensorListener() {
        try {
            mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);
            mWakeLock = mPowerManager.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, TAG);
            mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        try {
            if (audioManager.isBluetoothA2dpOn() || audioManager.isWiredHeadsetOn()) {
                return;
            }
            if (mAdapter.getMediaPlayer().isPlaying()) {
                float distance = event.values[0];
                if (distance >= mSensor.getMaximumRange()) {
                    mAdapter.setAudioPlayByEarPhone(0);
                    setScreenOn();
                } else {
                    mAdapter.setAudioPlayByEarPhone(2);
                    ViewHolderController.getInstance().replayVoice();
                    setScreenOff();
                }
            } else {
                if (mWakeLock != null && mWakeLock.isHeld()) {
                    mWakeLock.release();
                    mWakeLock = null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setScreenOn() {
        if (mWakeLock != null) {
            mWakeLock.setReferenceCounted(false);
            mWakeLock.release();
            mWakeLock = null;
        }
    }

    @SuppressLint("InvalidWakeLockTag")
    private void setScreenOff() {
        if (mWakeLock == null) {
            mWakeLock = mPowerManager.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, TAG);
        }
        mWakeLock.acquire();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void shadowOnclick() {
        if (dropMenuFragment != null) {
            hideDropMenu();
        } else if (resumeMenuFragment != null) {
            hideResumeMenu();
        }
    }


    @SuppressLint("ResourceType")
    public void hideDropMenu() {
        FragmentTransaction mTransaction = getFragmentManager().beginTransaction();

        //  mTransaction.setCustomAnimations(R.anim.fade_in_out_a,  R.anim.fade_in_out_a);
        if (fragmentShadow != null)
            mTransaction.remove(fragmentShadow);

        mTransaction.setCustomAnimations(R.anim.top_in_a, R.anim.top_out_a);

        if (dropMenuFragment != null)
            mTransaction.remove(dropMenuFragment);


        dropMenuFragment = null;
        fragmentShadow = null;

        mTransaction.commit();
    }


    @SuppressLint("ResourceType")
    public void hideResumeMenu() {
        FragmentTransaction mTransaction = getFragmentManager().beginTransaction();


        //  mTransaction.setCustomAnimations(R.anim.fade_in_out_a,  R.anim.fade_in_out_a);
        if (fragmentShadow != null)
            mTransaction.remove(fragmentShadow);


        mTransaction.setCustomAnimations(R.anim.bottom_out_a, R.anim.bottom_out_a);
        if (resumeMenuFragment != null)
            mTransaction.remove(resumeMenuFragment);


        resumeMenuFragment = null;
        fragmentShadow = null;

        mTransaction.commit();
    }

    private class HeadsetDetectReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
                if (intent.hasExtra("state")) {
                    int state = intent.getIntExtra("state", 0);
                    mAdapter.setAudioPlayByEarPhone(state);
                }
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    private List<MyMessage> getMessages() {
        List<MyMessage> list = new ArrayList<>();
        Resources res = getResources();
        String[] messages = res.getStringArray(R.array.messages_array);
        for (int i = 0; i < messages.length; i++) {
            MyMessage message;
            if (i % 2 == 0) {
                message = new MyMessage(messages[i], IMessage.MessageType.RECEIVE_TEXT.ordinal());
                message.setUserInfo(new DefaultUser("0", "DeadPool", "R.drawable.deadpool"));
            } else {
                message = new MyMessage(messages[i], IMessage.MessageType.SEND_TEXT.ordinal());
                message.setUserInfo(new DefaultUser("1", "IronMan", "R.drawable.ironman"));
            }
            message.setTimeString(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
            list.add(message);
        }
        return list;
    }

    private void initMsgAdapter() {
        final float density = getResources().getDisplayMetrics().density;
        final float MIN_WIDTH = 60 * density;
        final float MAX_WIDTH = 200 * density;
        final float MIN_HEIGHT = 60 * density;
        final float MAX_HEIGHT = 200 * density;
        ImageLoader imageLoader = new ImageLoader() {
            @Override
            public void loadAvatarImage(ImageView avatarImageView, String string) {
                //加载展示图片
                // You can use other image load libraries.
                if (string.contains("R.drawable")) {
                    Integer resId = getResources().getIdentifier(string.replace("R.drawable.", ""),
                            "drawable", getPackageName());
                    avatarImageView.setImageResource(resId);
                } else {
                    UploadPic.Companion.loadPicFromNet(string, avatarImageView);
//                    Glide.with(MessageListActivity.this)
//                            .load(string)
//                            .apply(new RequestOptions().placeholder(R.drawable.aurora_picture_not_found))
//                            .into(avatarImageView);
                }
            }

            //Load image message
            @Override
            public void mLoadImage(final ImageView imageView, String string) {
                // You can use other image load libraries.
                Glide.with(getApplicationContext())
                        .asBitmap()
                        .load(string)
                        .apply(new RequestOptions().fitCenter().placeholder(R.drawable.aurora_picture_not_found))
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                int imageWidth = resource.getWidth();
                                int imageHeight = resource.getHeight();
                                Log.d(TAG, "Image width " + imageWidth + " height: " + imageHeight);

                                // 裁剪 bitmap
                                float width, height;
                                if (imageWidth > imageHeight) {
                                    if (imageWidth > MAX_WIDTH) {
                                        float temp = MAX_WIDTH / imageWidth * imageHeight;
                                        height = temp > MIN_HEIGHT ? temp : MIN_HEIGHT;
                                        width = MAX_WIDTH;
                                    } else if (imageWidth < MIN_WIDTH) {
                                        float temp = MIN_WIDTH / imageWidth * imageHeight;
                                        height = temp < MAX_HEIGHT ? temp : MAX_HEIGHT;
                                        width = MIN_WIDTH;
                                    } else {
                                        float ratio = imageWidth / imageHeight;
                                        if (ratio > 3) {
                                            ratio = 3;
                                        }
                                        height = imageHeight * ratio;
                                        width = imageWidth;
                                    }
                                } else {
                                    if (imageHeight > MAX_HEIGHT) {
                                        float temp = MAX_HEIGHT / imageHeight * imageWidth;
                                        width = temp > MIN_WIDTH ? temp : MIN_WIDTH;
                                        height = MAX_HEIGHT;
                                    } else if (imageHeight < MIN_HEIGHT) {
                                        float temp = MIN_HEIGHT / imageHeight * imageWidth;
                                        width = temp < MAX_WIDTH ? temp : MAX_WIDTH;
                                        height = MIN_HEIGHT;
                                    } else {
                                        float ratio = imageHeight / imageWidth;
                                        if (ratio > 3) {
                                            ratio = 3;
                                        }
                                        width = imageWidth * ratio;
                                        height = imageHeight;
                                    }
                                }
                                ViewGroup.LayoutParams params = imageView.getLayoutParams();
                                params.width = (int) width;
                                params.height = (int) height;
                                imageView.setLayoutParams(params);
                                Matrix matrix = new Matrix();
                                float scaleWidth = width / imageWidth;
                                float scaleHeight = height / imageHeight;
                                matrix.postScale(scaleWidth, scaleHeight);
                                imageView.setImageBitmap(Bitmap.createBitmap(resource, 0, 0, imageWidth, imageHeight, matrix, true));
                            }
                        });
            }

            /**
             * Load video message
             * @param imageCover Video message's image cover
             * @param uri Local path or url.
             */
            @Override
            public void loadVideo(ImageView imageCover, String uri) {
                long interval = 5000 * 1000;
                Glide.with(MessageListActivity.this)
                        .asBitmap()
                        .load(uri)
                        // Resize image view by change override size.
                        .apply(new RequestOptions().frame(interval).override(200, 400))
                        .into(imageCover);
            }
        };

        // Use default layout
        MsgListAdapter.HoldersConfig holdersConfig = new MsgListAdapter.HoldersConfig();
        mAdapter = new MsgListAdapter<>("0", holdersConfig, imageLoader);
        // If you want to customise your layout, try to create custom ViewHolder:
        // holdersConfig.setSenderTxtMsg(CustomViewHolder.class, layoutRes);
        // holdersConfig.setReceiverTxtMsg(CustomViewHolder.class, layoutRes);
        // CustomViewHolder must extends ViewHolders defined in MsgListAdapter.
        // Current ViewHolders are TxtViewHolder, VoiceViewHolder.

        mAdapter.setOnMsgClickListener(new MsgListAdapter.OnMsgClickListener<MyMessage>() {
            @Override
            public void onMessageClick(MyMessage message) {
                // do something
                if (message.getType() == IMessage.MessageType.RECEIVE_VIDEO.ordinal()
                        || message.getType() == IMessage.MessageType.SEND_VIDEO.ordinal()) {
                    if (!TextUtils.isEmpty(message.getMediaFilePath())) {
                        Intent intent = new Intent(MessageListActivity.this, VideoActivity.class);
                        intent.putExtra(VideoActivity.VIDEO_PATH, message.getMediaFilePath());
                        startActivity(intent);
                    }
                } else if (message.getType() == IMessage.MessageType.RECEIVE_IMAGE.ordinal()
                        || message.getType() == IMessage.MessageType.SEND_IMAGE.ordinal()) {


                    //点击图片，放大/缩小，fragemt来处理
                    Intent intent = new Intent(MessageListActivity.this, BrowserImageActivity.class);
                    intent.putExtra("msgId", message.getMsgId());
                    intent.putStringArrayListExtra("pathList", mPathList);
                    intent.putStringArrayListExtra("idList", mMsgIdList);
                    startActivity(intent);


                } else if (message.getType() == IMessage.MessageType.SEND_VIDEO.ordinal()
                        || message.getType() == IMessage.MessageType.RECEIVE_VOICE.ordinal()) {

                } else {
                    Toast.makeText(getApplicationContext(),
                            getApplicationContext().getString(R.string.message_click_hint),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        mAdapter.setMsgLongClickListener(new MsgListAdapter.OnMsgLongClickListener<MyMessage>() {
            @Override
            public void onMessageLongClick(View view, MyMessage message) {
                Toast.makeText(getApplicationContext(),
                        getApplicationContext().getString(R.string.message_long_click_hint),
                        Toast.LENGTH_SHORT).show();
                // do something
            }
        });

        mAdapter.setOnAvatarClickListener(new MsgListAdapter.OnAvatarClickListener<MyMessage>() {
            @Override
            public void onAvatarClick(MyMessage message) {
                DefaultUser userInfo = (DefaultUser) message.getFromUser();
                Toast.makeText(getApplicationContext(),
                        getApplicationContext().getString(R.string.avatar_click_hint),
                        Toast.LENGTH_SHORT).show();
                // do something
            }
        });

        mAdapter.setMsgStatusViewClickListener(new MsgListAdapter.OnMsgStatusViewClickListener<MyMessage>() {
            @Override
            public void onStatusViewClick(MyMessage message) {
                // message status view click, resend or download here
            }
        });

//        MyMessage message = new MyMessage("Hello World", IMessage.MessageType.RECEIVE_TEXT.ordinal());
//        message.setUserInfo(new DefaultUser("0", "Deadpool", "R.drawable.deadpool"));
//        mAdapter.addToStart(message, true);
//
//
//        MyMessage voiceMessage = new MyMessage("", IMessage.MessageType.RECEIVE_VOICE.ordinal());
//        voiceMessage.setUserInfo(new DefaultUser("0", "Deadpool", "R.drawable.deadpool"));
//        voiceMessage.setMediaFilePath(Environment.getExternalStorageDirectory().getAbsolutePath() + "/voice/2018-02-28-105103.m4a");
//        voiceMessage.setDuration(4);
//        mAdapter.addToStart(voiceMessage, true);
//
//        MyMessage sendVoiceMsg = new MyMessage("", IMessage.MessageType.SEND_VOICE.ordinal());
//        sendVoiceMsg.setUserInfo(new DefaultUser("1", "Ironman", "R.drawable.ironman"));
//        sendVoiceMsg.setMediaFilePath(Environment.getExternalStorageDirectory().getAbsolutePath() + "/voice/2018-02-28-105103.m4a");
//        sendVoiceMsg.setDuration(4);
//        sendVoiceMsg.setMessageStatus(IMessage.MessageStatus.SEND_SUCCEED);
//        mAdapter.addToStart(sendVoiceMsg, true);
//
//        MyMessage eventMsg = new MyMessage("haha", IMessage.MessageType.EVENT.ordinal());
//        mAdapter.addToStart(eventMsg, true);
//
//
//        MyMessage RESET = new MyMessage("haha", IMessage.MessageType.RESET.ordinal());
//        mAdapter.addToStart(RESET, true);
//
//
//        MyMessage RESET1 = new MyMessage("相手の電話番号交換申請を同意しまし", IMessage.MessageType.EVENT.ordinal());
//        mAdapter.addToStart(RESET1, true);
//
//
//        MyMessage RESET2 = new MyMessage("交換電話の送信を要求します", IMessage.MessageType.EVENT.ordinal());
//        mAdapter.addToStart(RESET2, true);
//
//
//        MyMessage RESET3 = new MyMessage("相手はあなたとの電話交換に同意しました", IMessage.MessageType.EVENT.ordinal());
//        mAdapter.addToStart(RESET3, true);
//
//
//        MyMessage jobInfo = new MyMessage("", IMessage.MessageType.JOB_INFO.ordinal());
//        mAdapter.addToStart(jobInfo, true);
//
//
//        MyMessage communicationRequest = new MyMessage("向こうはあなたに電話番号交換の申請を出しました。同意しますか。", IMessage.MessageType.RECEIVE_COMMUNICATION_PHONE.ordinal());
//        communicationRequest.setUserInfo(new DefaultUser("1", "Ironman", "R.drawable.ironman"));
//        mAdapter.addToStart(communicationRequest, true);
//
//
//        MyMessage communicationRequest1 = new MyMessage("向こうはあなたにline交換の申請を出しました。同意しますか。", IMessage.MessageType.RECEIVE_COMMUNICATION_LINE.ordinal());
//        communicationRequest1.setUserInfo(new DefaultUser("1", "Ironman", "R.drawable.ironman"));
//        mAdapter.addToStart(communicationRequest1, true);
//
//        MyMessage communicationRequest2 = new MyMessage("向こうはあなたをビデオ面接にさそっていますが、受けてよろしいですか。", IMessage.MessageType.RECEIVE_COMMUNICATION_VIDEO.ordinal());
//        communicationRequest2.setUserInfo(new DefaultUser("1", "Ironman", "R.drawable.ironman"));
//        mAdapter.addToStart(communicationRequest2, true);
//
//
//        MyMessage communicationResult = new MyMessage("電話番号交換は成功しました。●●様の電話番号は：13888888888", IMessage.MessageType.RECEIVE_ACCOUNT_PHONE.ordinal());
//        communicationResult.setUserInfo(new DefaultUser("1", "Ironman", "R.drawable.ironman"));
//        mAdapter.addToStart(communicationResult, true);
//
//
//        MyMessage communicationResult1 = new MyMessage("line交換は成功しました。●●様の電話番号は：13888888888", IMessage.MessageType.RECEIVE_ACCOUNT_LINE.ordinal());
//        communicationResult1.setUserInfo(new DefaultUser("1", "Ironman", "R.drawable.deadpool"));
//        mAdapter.addToStart(communicationResult1, true);


//        MyMessage receiveVideo = new MyMessage("", IMessage.MessageType.RECEIVE_VIDEO.ordinal());
//        receiveVideo.setMediaFilePath(Environment.getExternalStorageDirectory().getPath() + "/Pictures/Hangouts/video-20170407_135638.3gp");
//        receiveVideo.setDuration(4);
//        receiveVideo.setUserInfo(new DefaultUser("0", "Deadpool", "R.drawable.deadpool"));
//        mAdapter.addToStart(receiveVideo, true);

//
//        MyMessage EVENT1 = new MyMessage("相手とのビデオ面接申請を同意しました、まもなくビデオがアクセスします。", IMessage.MessageType.EVENT.ordinal());
//        mAdapter.addToStart(EVENT1, true);
//
//
//        MyMessage interview = new MyMessage("厳選なる審査の結果、あなたを正社員として採用することになりました。後ほどoffをお送りいたします。おめでとうございます！", IMessage.MessageType.INTERVIEW_SUCCESS.ordinal());
//        interview.setUserInfo(new DefaultUser("1", "Ironman", "R.drawable.deadpool"));
//        mAdapter.addToStart(interview, true);
//
//
//        MyMessage offer = new MyMessage("清水さんからのofferが着信しました！！！！！！！", IMessage.MessageType.SEND_OFFER.ordinal());
//        offer.setUserInfo(new DefaultUser("1", "Ironman", "R.drawable.deadpool"));
//        mAdapter.addToStart(offer, true);
//
//
//        MyMessage interview1 = new MyMessage("今回は採用を見送る事になりましたのでご了承のほど、宜しくお願い致します", IMessage.MessageType.INTERVIEW_FAIL.ordinal());
//        interview1.setUserInfo(new DefaultUser("1", "Ironman", "R.drawable.deadpool"));
//        mAdapter.addToStart(interview1, true);
//
//
//        MyMessage pic = new MyMessage("今回は採用を見送る事になりましたのでご了承のほど、宜しくお願い致します", IMessage.MessageType.SEND_IMAGE.ordinal());
//        pic.setMediaFilePath("R.drawable.ppp");
//        pic.setUserInfo(new DefaultUser("1", "Ironman", "R.drawable.deadpool"));
//        mAdapter.addToStart(pic, true);
//

        // mAdapter.addHistoryList(mData);


        PullToRefreshLayout layout = mChatView.getPtrLayout();
        layout.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PullToRefreshLayout layout) {
                Log.i("MessageListActivity", "Loading next page");
                loadNextPage(lastShowedMessageId);
            }
        });
        // Deprecated, should use onRefreshBegin to load next page
        mAdapter.setOnLoadMoreListener(new MsgListAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore(int page, int totalCount) {
//                Log.i("MessageListActivity", "Loading next page");
//                loadNextPage();
            }
        });

        mChatView.setAdapter(mAdapter);
        mAdapter.getLayoutManager().scrollToPosition(0);
        scrollToBottom();

    }

    //聊天控件相机回调
    private void initChatViewCameraCallbackListener() {
        //发送照相机照的照片
        mChatView.setOnCameraCallbackListener(new OnCameraCallbackListener() {
            @Override
            public void onTakePictureCompleted(String photoPath) {
                //发送图片
                topPart.setVisibility(View.VISIBLE);
                if (photoPath != null) {
                    //发送照片
                    UploadPic uploadPic = new UploadPic();
                    String[] str = photoPath.split(".");
                    sendImageMessage(uploadPic, photoPath, str[str.length - 1]);
                }
            }

            @Override
            public void onStartVideoRecord() {

            }

            @Override
            public void onFinishVideoRecord(String videoPath) {

            }

            @Override
            public void onCancelVideoRecord() {

            }

            @Override
            public void onCancelTakePicture() {
                topPart.setVisibility(View.VISIBLE);
                scrollToBottom();
            }

            @Override
            public void openRecord() {
                //打开摄像机 为摄像机布局腾出空间
                topPart.setVisibility(View.GONE);

            }
        });
    }

    //聊天控件录音事件
    private void initChatViewRecordVoiceListener() {
        mChatView.setRecordVoiceListener(new RecordVoiceListener() {
            @Override
            public void onStartRecord() {
                // set voice file path, after recording, audio file will save here
                String path = Environment.getExternalStorageDirectory().getPath() + "/voice";
                File destDir = new File(path);
                if (!destDir.exists()) {
                    destDir.mkdirs();
                }
                mChatView.setRecordVoiceFile(destDir.getPath(), DateFormat.format("yyyy-MM-dd-hhmmss",
                        Calendar.getInstance(Locale.CHINA)) + "");
            }

            @Override
            public void onFinishRecord(File voiceFile, int duration) {


                Toast.makeText(MessageListActivity.this, voiceFile.getAbsolutePath() + "",
                        Toast.LENGTH_SHORT).show();

                MyMessage message = new MyMessage(null, IMessage.MessageType.SEND_VOICE.ordinal());
                message.setUserInfo(new DefaultUser("1", "Ironman", "R.drawable.ironman"));
                message.setMediaFilePath(voiceFile.getPath());

                message.setDuration(duration);
                message.setTimeString(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
                message.setMessageStatus(IMessage.MessageStatus.SEND_GOING);

                mAdapter.addToStart(message, true);
            }

            @Override
            public void onCancelRecord() {

            }

            /**
             * In preview record voice layout, fires when click cancel button
             * Add since chatinput v0.7.3
             */
            @Override
            public void onPreviewCancel() {

            }

            /**
             * In preview record voice layout, fires when click send button
             * Add since chatinput v0.7.3
             */
            @Override
            public void onPreviewSend() {

            }
        });
    }

    //聊天控件菜单点击事件
    private void initChatViewMenuClickListener() {
        mChatView.setMenuClickListener(new OnMenuClickListener() {
            //文字消息
            @Override
            public boolean onSendTextMessage(CharSequence input) {
                if (input.length() == 0) {
                    return false;
                }
                try {
                    JSONObject sendMessage = sendMessageModel;
                    ((JSONObject) sendMessage.get("content")).put("msg", input.toString());
                    //Socket.Channel channelSend = socket.getChannelByName("p_e42c10f3-f005-403d-81d6-bac73edc6673");
                    MyMessage message = new MyMessage(input.toString(), IMessage.MessageType.SEND_TEXT.ordinal());
                    message.setUserInfo(new DefaultUser("1", "Ironman", "R.drawable.ironman"));
                    message.setTimeString(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
                    message.setMessageStatus(IMessage.MessageStatus.SEND_GOING);
                    mAdapter.addToStart(message, true);
                    messageId = message.getMsgId();

                    channelSend.publish(sendMessage, new Ack() {
                        public void call(String channelName, Object error, Object data) {
                            if (error == null) {
                                //成功
                                System.out.println("Published message to channel " + channelName + " successfully");
                                System.out.println(data);

                            } else {
                                //失败

                            }
                        }
                    });
                } catch (Exception R) {
                }
                return true;
            }

            //发送图片消息
            @Override
            public void onSendFiles(List<FileItem> list) {
                if (list == null || list.isEmpty()) {
                    return;
                }
                UploadPic uploadPic = new UploadPic();
                //遍历选择的文件
                for (FileItem item : list) {
                    if (item.getType() == FileItem.Type.Image) {
                        sendImageMessage(uploadPic, item.getFilePath(), item.getFileName());
                    }
//                  else if (it.getType() == FileItem.Type.Video) {
//                        message = new MyMessage(null, IMessage.MessageType.SEND_VIDEO.ordinal());
//                        message.setDuration(((VideoItem) it).getDuration());
//                  } else {
//                        throw new RuntimeException("Invalid FileItem type. Must be Type.Image or Type.Video");
//                  }
                }
            }

            @Override
            public boolean switchToMicrophoneMode() {
                scrollToBottom();
                String[] perms = new String[]{
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                };

                if (!EasyPermissions.hasPermissions(MessageListActivity.this, perms)) {
                    EasyPermissions.requestPermissions(MessageListActivity.this,
                            getResources().getString(R.string.rationale_record_voice),
                            RC_RECORD_VOICE, perms);
                }
                return true;
            }

            @Override
            public boolean switchToGalleryMode() {
                scrollToBottom();
                String[] perms = new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE
                };

                if (!EasyPermissions.hasPermissions(MessageListActivity.this, perms)) {
                    EasyPermissions.requestPermissions(MessageListActivity.this,
                            getResources().getString(R.string.rationale_photo),
                            RC_PHOTO, perms);
                }
                // If you call updateData, select photo view will try to update data(Last update over 30 seconds.)
                mChatView.getChatInputView().getSelectPhotoView().updateData();
                return true;
            }

            @Override
            public boolean switchToCameraMode() {
                scrollToBottom();
                String[] perms = new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO
                };
//
//                if (!ActivityCompat.shouldShowRequestPermissionRationale(MessageListActivity.this,Manifest.permission.CAMERA)){
//                    ActivityCompat.requestPermissions(MessageListActivity.this, new String[]{Manifest.permission.CAMERA},0);
//                }

                if (!EasyPermissions.hasPermissions(MessageListActivity.this, perms)) {
                    EasyPermissions.requestPermissions(MessageListActivity.this,
                            getResources().getString(R.string.rationale_camera),
                            RC_CAMERA, perms);


                    return false;
                } else {
                    File rootDir = getFilesDir();
                    String fileDir = rootDir.getAbsolutePath() + "/photo";
                    mChatView.setCameraCaptureFile(fileDir, new SimpleDateFormat("yyyy-MM-dd-hhmmss",
                            Locale.getDefault()).format(new Date()));
                }
                scrollToBottom();
                return true;
            }

            @Override
            public boolean switchToEmojiMode() {
                scrollToBottom();
                return true;
            }

            @Override
            public void switchToMenuItemHideShowMode() {
                scrollToBottom();
            }
        });
    }


    //初始化顶部菜单点击事件
    private void initTopMenuClickListener() {
        message_middle_select_bar1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                MyMessage e = new MyMessage("电话请求已经发出！！！", IMessage.MessageType.EVENT.ordinal());
                mAdapter.addToStart(e, true);


            }
        });

        message_middle_select_bar2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                MyMessage e = new MyMessage("Line请求发出", IMessage.MessageType.EVENT.ordinal());
                mAdapter.addToStart(e, true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(5000);
                            Message message = new Message();
                            message.what = LINE_EXCHANGE;
                            handler.sendMessage(message);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }

                    }
                }) {
                }.start();

            }
        });

        message_middle_select_bar3.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                hideDropMenu();
                if (resumeMenuFragment == null && fragmentShadow == null) {
                    FragmentTransaction mTransaction = getFragmentManager().beginTransaction();
                    fragmentShadow = new ShadowFragment();
                    mTransaction.add(R.id.mainBody, fragmentShadow);

                    resumeMenuFragment = new ResumeMenuFragment();
                    mTransaction.setCustomAnimations(R.anim.bottom_in_a, R.anim.bottom_in_a);
                    mTransaction.add(R.id.mainBody, resumeMenuFragment);

                    mTransaction.commit();
                } else {
                    hideResumeMenu();
                }


            }
        });

        message_middle_select_bar4.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                hideResumeMenu();
                if (dropMenuFragment == null && fragmentShadow == null) {
                    FragmentTransaction mTransaction = getFragmentManager().beginTransaction();
                    fragmentShadow = new ShadowFragment();
                    mTransaction.add(R.id.chat_view, fragmentShadow);

                    dropMenuFragment = new DropMenuFragment();
                    mTransaction.setCustomAnimations(R.anim.top_in_a, R.anim.top_out_a);
                    mTransaction.add(R.id.chat_view, dropMenuFragment);

                    mTransaction.commit();
                } else {
                    hideDropMenu();
                }
            }
        });
    }

    //标记为已读
    private void setAsRead(String hisId) {
        socket.emit("setStatusAsRead", hisId);
    }

    //下一页
    private void loadNextPage(String lastMsgId) {
        String jstr = "{\"uids\":[\"" + MY_ID + "\",\"" + HIS_ID + "\"]}";
        try {
            JSONObject j = new JSONObject(jstr);
            j.put("lastMsgId", lastMsgId);
            j.put("type", "p2p");
            socket.emit("queryHistoryData", j);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //加载历史消息
    private void initHistoryMessageList(JSONArray data) {
        historyMessage = data;
        Message message = new Message();
        historyMessageHandler.sendMessage(message);
    }

    //展示当前接收或发送的消息到页面上
    private void showMessageOnScreen(JSONObject jsono) {
        String senderId = null;
        try {
            senderId = jsono.getJSONObject("sender").get("id").toString();

            JSONObject content = new JSONObject(jsono.get("content").toString());
            String type = jsono.get("type").toString();
            if (senderId != null && senderId.equals(MY_ID)) {
                //我发送的信息 更新状态
                System.out.println("我发送的");
                if (type != null && type.equals("p2p") && content.get("type").toString() != null && content.get("type").toString().equals("text")) {
                    //更新状态
                    MyMessage message = mAdapter.getMessageById(messageId);
                    message.setMessageStatus(IMessage.MessageStatus.SEND_SUCCEED);
                    mAdapter.updateMessage(messageId, message);
                    mAdapter.notifyDataSetChanged();
                }
            } else {
                //我接收的
                System.out.println("我接收的");
                if (type != null && type.equals("p2p")) {
                    MyMessage message=null;
                    String contentMsg=content.get("msg").toString();
                    if (content.get("type").toString() != null && content.get("type").toString().equals("text")) {
                        //文字消息
                        new MyMessage(contentMsg, IMessage.MessageType.RECEIVE_TEXT.ordinal());
                    } else if (content.getString("type") != null && content.getString("type").equals("image")) {
                        //图片消息
                        message = new MyMessage(null, IMessage.MessageType.RECEIVE_IMAGE.ordinal());
                        message.setMediaFilePath(contentMsg);
                    }
                    final MyMessage message_recieve=message;
                    if(message_recieve!=null){
                        MessageListActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                message_recieve.setUserInfo(new DefaultUser("1", "Ironman", "R.drawable.ironman"));
                                message_recieve.setTimeString(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
                                message_recieve.setMessageStatus(IMessage.MessageStatus.RECEIVE_SUCCEED);
                                mAdapter.addToStart(message_recieve, true);
                                mAdapter.notifyDataSetChanged();
                                mChatView.getMessageListView().smoothScrollToPosition(0);
                            }
                        });
                    }
                }
            }
            //没有历史消息时，把接受或者发送的第一条消息作为lastShowedMessageId
            if (lastShowedMessageId == null) {
                lastShowedMessageId = jsono.getString("_id");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //初始化信息
    private void initMessageChannel() {
        Intent intent = getIntent();
        String hisId = intent.getStringExtra("hisId");
        HIS_ID = hisId;
        try {
            sendMessageModel = new JSONObject("{ \"sender\":{\"id\": \"589daa8b-79bd-4cae-bf67-765e6e786a72\",\"name\": \"\" }," +
                    "\"receiver\":{ \"id\": \"" + HIS_ID + "\", \"name\": \"\" }," +
                    "\"content\":{ \"type\": \"text\", \"msg\": \"\" }, " +
                    "\"type\":\"p2p\"}}");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        application = App.Companion.getInstance();
        application.setRecieveMessageListener(new RecieveMessageListener() {
            @Override
            public void getNormalMessage(@NotNull String str) {
                try {
                    JSONObject jsono = new JSONObject(str);
                    showMessageOnScreen(jsono);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("普通消息");
            }

            @Override
            public void getHistoryMessage(@NotNull String str) {
                try {
                    JSONObject jsono = new JSONObject(str);
                    initHistoryMessageList(jsono.getJSONObject("content").getJSONArray("data"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("历史消息");
            }
        });


        socket = application.getSocket();
        channelSend = socket.createChannel("p_" + HIS_ID);
    }

    /**
     * 发送图片
     *
     * @param uploadPic
     * @param imagePath
     * @param imageName
     */
    private void sendImageMessage(UploadPic uploadPic, String imagePath, String imageName) {
        try {
            //图片二进制数据
            final RequestBody image_file = uploadPic.getImageDate(imagePath);
            //token
            final String authorization = "Bearer " + application.getToken();
            //文件名
            final String fileName = imageName;
            final OkHttpClient client = new OkHttpClient();
            final String path = imagePath;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        MyMessage message;
                        message = new MyMessage(null, IMessage.MessageType.SEND_IMAGE.ordinal());
                        mPathList.add(path);
                        mMsgIdList.add(message.getMsgId());
                        System.out.println(path);
                        message.setTimeString(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
                        message.setMediaFilePath(path);
                        message.setUserInfo(new DefaultUser("1", "Ironman", "R.drawable.ironman"));
                        message.setMessageStatus(IMessage.MessageStatus.SEND_GOING);

                        final MyMessage fMsg_sending = message;
                        MessageListActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.addToStart(fMsg_sending, true);
                                scrollToBottom();
                            }
                        });

                        RequestBody requestBody = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)
                                ///                .addPart(
                                //                        Headers.of("Content-Disposition", "form-data; name=\"file\"; filename=\"" + fileName + "\""),
                                //                        RequestBody.create(MEDIA_TYPE_PNG, file))
                                //                .addPart(
                                //                        Headers.of("Content-Disposition", "form-data; name=\"imagetype\""),
                                //                        RequestBody.create(null, imageType))
                                //                .addPart(
                                //                        Headers.of("Content-Disposition", "form-data; name=\"userphone\""),
                                //                        RequestBody.create(null, userPhone))

                                .addFormDataPart("file", fileName, image_file)
                                .addFormDataPart("type", "IMAGE")
                                .addFormDataPart("bucket", "user-feedback")
//                                                .addFormDataPart("authorization", authorization)
                                .build();

                        Request request = new Request.Builder()
                                .url("https://storage.sk.cgland.top/api/v1/storage")
                                .addHeader("Authorization", authorization)
                                .post(requestBody)
                                .build();

                        Response response;
                        response = client.newCall(request).execute();
                        if (response.isSuccessful()) {
                            //请求成功
                            String jsonString = response.body().string();
                            System.out.println("发送图片返回数据");
                            System.out.println(jsonString);


                            JSONObject result = new JSONObject(jsonString);

                            JSONObject sendMessage = sendMessageModel;
                            sendMessage.getJSONObject("content").put("msg", result.getString("url"));
                            sendMessage.getJSONObject("content").put("type", "image");

                            final MyMessage message_f = message;
                            channelSend.publish(sendMessage, new Ack() {
                                public void call(String channelName, Object error, Object data) {
                                    if (error == null) {
                                        //成功
                                        System.out.println("Published message to channel " + channelName + " successfully");
                                        System.out.println(data);

                                        message_f.setTimeString(new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
                                        message_f.setMediaFilePath(path);
                                        message_f.setUserInfo(new DefaultUser("1", "Ironman", "R.drawable.ironman"));
                                        message_f.setMessageStatus(IMessage.MessageStatus.SEND_SUCCEED);

                                        final MyMessage fMsg_success = message_f;
                                        MessageListActivity.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                mAdapter.updateMessage(fMsg_success.getMsgId(), fMsg_success);
                                                mAdapter.notifyDataSetChanged();
                                            }
                                        });

                                    } else {
                                        //失败
                                    }
                                }
                            });

                        } else {
                            System.out.println("发送图片请求失败");
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //滑动到底部
    private void scrollToBottom() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
                mAdapter.getLayoutManager().scrollToPosition(0);
                mChatView.getMessageListView().smoothScrollToPosition(0);
            }
        }, 10);
    }

    //滑动到底部
    private void scrollToBottom(int m) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
                mAdapter.getLayoutManager().scrollToPosition(0);
                mChatView.getMessageListView().smoothScrollToPosition(0);
            }
        }, m);
    }

    //销毁消息通道
    public void DestroyMessageChannel() {

    }

    //加载历史消息
    @SuppressLint("HandlerLeak")
    private Handler historyMessageHandler = new Handler() {
        public void handleMessage(Message mes) {
            List<MyMessage> list = new ArrayList<>();
            try {
                //展示
                for (int i = 0; i < historyMessage.length(); i++) {
                    String senderId;
                    senderId = historyMessage.getJSONObject(i).getJSONObject("sender").getString("id");

                    JSONObject content = historyMessage.getJSONObject(i).getJSONObject("content");

                    String type = historyMessage.getJSONObject(i).getString("type");

                    MyMessage message = null;

                    if (type != null && type.equals("p2p")) {
                        String msg = content.getString("msg");
                        String contetType = content.get("type").toString();
                        if (contetType != null && contetType.equals("text")) {
                            //历史文字消息
                            if (senderId != null && senderId.equals(MY_ID)) {
                                message = new MyMessage(msg, IMessage.MessageType.SEND_TEXT.ordinal());
                                message.setUserInfo(new DefaultUser("1", "IronMan", "R.drawable.ironman"));
                                message.setMessageStatus(IMessage.MessageStatus.SEND_SUCCEED);
                            } else {
                                message = new MyMessage(msg, IMessage.MessageType.RECEIVE_TEXT.ordinal());
                                message.setUserInfo(new DefaultUser("0", "DeadPool", "R.drawable.deadpool"));
                            }
                        } else if (contetType != null && contetType.equals("image")) {
                            //历史图片消息
                            if (senderId != null && senderId.equals(MY_ID)) {
                                message = new MyMessage(null, IMessage.MessageType.SEND_IMAGE.ordinal());
                                message.setUserInfo(new DefaultUser("1", "IronMan", "R.drawable.ironman"));
                                message.setMessageStatus(IMessage.MessageStatus.SEND_SUCCEED);
                                message.setMediaFilePath(msg);

                            } else {
                                message = new MyMessage(null, IMessage.MessageType.RECEIVE_IMAGE.ordinal());
                                message.setUserInfo(new DefaultUser("0", "DeadPool", "R.drawable.deadpool"));
                                message.setMediaFilePath(msg);

                            }
                        }


                        if (i == 0 && topBlankMessageId != null && message != null) {
                            mAdapter.updateMessage(topBlankMessageId, message);
                            mAdapter.notifyDataSetChanged();
                            continue;
                        }

                    }

                    if (message != null) {
                        if (i == historyMessage.length() - 1) {
                            //展示时间
                            try {

                                lastShowedMessageId = historyMessage.getJSONObject(i).getString("_id");


                                String created = historyMessage.getJSONObject(i).getString("created");
                                created = created.replace('T', ' ');
                                created = created.substring(0, created.length() - 1);


                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                                Date createdDate = sdf.parse(created);
                                SimpleDateFormat sdf_show = new SimpleDateFormat("HH:mm");


                                // System.out.println(createdDate.getTime());
                                message.setTimeString(sdf_show.format(createdDate));
                            } catch (ParseException e) {
                                System.out.println("77777777777777777777777777777");

                                e.printStackTrace();
                            }


                        }

                        list.add(message);
                    }
                    //最后一条历史记录后面，添加空项，下载加载历史记录的第一条取代他，以便历史记录加载出来后，在界面上有所体现
                    if (i == historyMessage.length() - 1) {
                        MyMessage RESET2 = new MyMessage("", IMessage.MessageType.EMPTY.ordinal());
                        list.add(RESET2);
                        topBlankMessageId = RESET2.getMsgId();
                    }
                }
            } catch (JSONException e) {
                System.out.println("|||||||||||||||||||||");

                e.printStackTrace();
            }
            mAdapter.addHistoryList(list);
            mChatView.getPtrLayout().refreshComplete();
            if (isInitHistory) {
                scrollToBottom();
                isInitHistory = false;
            }
            mChatView.getMessageListView().setScrollY(1000);
        }
    };

    //聊天标记
    @SuppressLint("ResourceType")
    @Override
    public void dropMenuOnclick(int i) {

        hideDropMenu();


        Toast.makeText(MessageListActivity.this, i + "",
                Toast.LENGTH_SHORT).show();
    }

    //建立选择
    @Override
    public void resumeMenuOnclick(int i) {
        hideResumeMenu();
        Toast.makeText(MessageListActivity.this, i + "",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mChatView.requestFocus();
                mChatView.getChatInputView().getMenuContainer().setVisibility(View.GONE);
                mChatView.getChatInputView().getMyMenuitemContainer().setVisibility(View.GONE);


                mChatView.getChatInputView().closeKeyBoard();


                ChatInputView chatInputView = mChatView.getChatInputView();
                if (chatInputView.getMenuState() == View.VISIBLE) {
                    chatInputView.dismissMenuLayout();
                }
                try {
                    View v = getCurrentFocus();
                    if (mImm != null && v != null) {
                        mImm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                        mWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                        view.clearFocus();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case MotionEvent.ACTION_UP:
                view.performClick();
                break;
        }
        return false;
    }


    //销毁时
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        mSensorManager.unregisterListener(this);

        //销毁消息通道
        DestroyMessageChannel();
        System.out.println("xxxxx00000xxxxx");
    }

}
