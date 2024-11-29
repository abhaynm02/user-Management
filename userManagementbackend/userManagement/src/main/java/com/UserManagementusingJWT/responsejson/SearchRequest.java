package com.UserManagementusingJWT.responsejson;

public class SearchRequest {
    String  key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "SearchRequest{" +
                "key='" + key + '\'' +
                '}';
    }
}
