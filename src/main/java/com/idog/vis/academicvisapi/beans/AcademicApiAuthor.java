/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author idoga
 */
public class AcademicApiAuthor {

    @JsonProperty("AuN") private String authorName;
    @JsonProperty("AuId") private long authorId;
    @JsonProperty("AfN") private String affiliationName;
    @JsonProperty("AfId") private long affiliationId;
    @JsonProperty("S") private int paperOrder;

    public AcademicApiAuthor(String authorName, long authorId, String affiliationName, long affiliationId, int paperOrder) {
        this.authorName = authorName;
        this.authorId = authorId;
        this.affiliationName = affiliationName;
        this.affiliationId = affiliationId;
        this.paperOrder = paperOrder;
    }    
    
    public long getAffiliationId() {
        return affiliationId;
    }

    public String getAffiliationName() {
        return affiliationName;
    }

    public long getAuthorId() {
        return authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public int getPaperOrder() {
        return paperOrder;
    }

    public void setAffiliationId(long affiliationId) {
        this.affiliationId = affiliationId;
    }

    public void setAffiliationName(String affiliationName) {
        this.affiliationName = affiliationName;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setPaperOrder(int paperOrder) {
        this.paperOrder = paperOrder;
    }
}
