package com.olamas.socialmedia.aggregator.twitter;

import java.io.Serializable;

public class TwitterFilter implements Serializable{

    private String userName;

    private String filterText;

    private String fromUser;

    private boolean validFilter;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFilterText() {
        return filterText;
    }

    public void setFilterText(String filterText) {
        this.filterText = filterText;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public boolean isValidFilter() {
        return validFilter;
    }

    public void setValidFilter(boolean validFilter) {
        this.validFilter = validFilter;
    }
}
