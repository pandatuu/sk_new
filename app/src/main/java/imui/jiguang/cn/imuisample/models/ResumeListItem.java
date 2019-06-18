package imui.jiguang.cn.imuisample.models;

import cn.jiguang.imui.commons.models.IUser;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ResumeListItem  {



    private String title;
    private String time;
    private String size;
    private int type;
    private String attachmentType;
    private String url;


    public ResumeListItem(String title, Long time, String size, int type,String attachmentType,String url) {
        this.title = title;

        if(time!=null){
            Date updateTime=new Date(time);
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy.MM.dd");
            this.time = sdf.format(updateTime)+"更新";
        }

        this.size = size;
        this.type = type;
        this.attachmentType=attachmentType;
        this.url=url;
    }

    public String getAttachmentType() {
        return attachmentType;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public String getSize() {
        return size;
    }

    public int getType() {
        return type;
    }
}
