package com.olamas.socialmedia.stream.api.twitter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tweet")
public class TwitterMessage implements Serializable{

    private static final long serialVersionUID = 1113799434508676095L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private final String idStr;

    private final String text;

    private String fromUser;

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

    public String getIdStr() {
        return idStr;
    }

    public String getText() {
        return text;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }
}
