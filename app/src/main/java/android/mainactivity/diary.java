package android.mainactivity;

import org.litepal.crud.DataSupport;

/**
 * Created by 15711 on 2018/10/16.
 */

public class diary extends DataSupport{
    private String content;
    private String time;

    public diary(String content, String time) {
        this.content = content;
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
