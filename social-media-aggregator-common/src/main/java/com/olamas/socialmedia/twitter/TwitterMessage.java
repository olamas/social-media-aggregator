package com.olamas.socialmedia.twitter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tweet")
public class TwitterMessage implements Serializable {

    private static final long serialVersionUID = 1113799434508676095L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userFilter;

    private String filter;

    private String idStr;

    private String text;

    private String fromUser;

    public TwitterMessage(){

    }

    public TwitterMessage(final String idStr,final String text){
        this.idStr = idStr;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserFilter() {
        return userFilter;
    }

    public void setUserFilter(String userFilter) {
        this.userFilter = userFilter;
    }

    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr){
        this.idStr = idStr;
    }

    public String getText() {
        return text;
    }

    public void setText(String text){
        this.text = text;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }
}
