package com.example.shorturlgenerator.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "url")
public class Url {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String fullUrl;

    public  Url() {

    }

    public Url(final long id, final String fullUrl) {
        this.id = id;
        this.fullUrl = fullUrl;
    }

    public Url (final String fullUrl) {
        this.fullUrl = fullUrl;
    }

    public String getFullUrl() {
        return this.fullUrl;
    }

    public void setFullUrl(final String link) {
        this.fullUrl = link;
    }


    public void setId(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Url url = (Url) o;

        return id == url.id && fullUrl.equals(url.fullUrl);
    }

    @Override
    public int hashCode() {
        return fullUrl.hashCode();
    }
}
