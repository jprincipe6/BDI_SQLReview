package com.sqlreview.api;

public class Author {
    private Integer authorId;
    private String name;
    private String email;
    private String affilication;

    public Author(Integer authorId, String name, String email, String affilication) {
        this.authorId = authorId;
        this.name = name;
        this.email = email;
        this.affilication = affilication;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAffilication() {
        return affilication;
    }

    public void setAffilication(String affilication) {
        this.affilication = affilication;
    }

    @Override
    public String toString() {
        return "Author{" +
                "authorId=" + authorId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", affilication='" + affilication + '\'' +
                '}';
    }
}
