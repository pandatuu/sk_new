package imui.jiguang.cn.imuisample.models;

import cn.jiguang.imui.commons.models.IUser;


public class ResumeListItem  {


    public  static final int WORD=1;
    public  static final int PDF=2;
    public  static final int JPG=3;

    private String title;
    private String time;
    private String size;
    private int type;

    public ResumeListItem(String title, String time, String size, int type) {
        this.title = title;
        this.time = time;
        this.size = size;
        this.type = type;
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
