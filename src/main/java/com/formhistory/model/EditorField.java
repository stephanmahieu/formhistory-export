package com.formhistory.model;

import com.formhistory.util.Utils;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("editorField")
public class EditorField {
    private String id;
    private String name;
    private String type;
    private String formid;
    private String host;
    private String url;
    private String firstsaved;
    private String lastsaved;
    private String content;

//    public EditorField(String id, String name, String type, String formid, String content, String host, String url, long firstsaved, long lastsaved) {
    public EditorField(String id, String name, String type, String formid, byte[] content, String host, String url, long firstsaved, long lastsaved) {
        this.id = Utils.encode(id);
        this.name = Utils.encode(name);
        this.type = type;
        this.formid = Utils.encode(formid);
        this.content = Utils.encode(content);
        this.host = Utils.encode(host);
        this.url = Utils.encode(url);
        this.firstsaved = Utils.toDateString(firstsaved);
        this.lastsaved = Utils.toDateString(lastsaved);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getFormid() {
        return formid;
    }

    public String getContent() {
        return content;
    }

    public String getHost() {
        return host;
    }

    public String getUrl() {
        return url;
    }

    public String getFirstsaved() {
        return firstsaved;
    }

    public String getLastsaved() {
        return lastsaved;
    }

}
