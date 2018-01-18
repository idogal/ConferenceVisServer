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
    @JsonProperty("AuId") private Long authorId;
    @JsonProperty("AfN") private String affiliationName;
    @JsonProperty("AfId") private Long affiliationId;
    @JsonProperty("S") private Integer paperOrder;

    public AcademicApiAuthor(String authorName, Long authorId, String affiliationName, Long affiliationId, Integer paperOrder) {
        this.authorName = authorName;
        this.authorId = authorId;
        this.affiliationName = affiliationName;
        this.affiliationId = affiliationId;
        this.paperOrder = paperOrder;
    }    
    
    public Long getAffiliationId() {
        return affiliationId;
    }

    public String getAffiliationName() {
        return affiliationName;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public Integer getPaperOrder() {
        return paperOrder;
    }

    public void setAffiliationId(Long affiliationId) {
        this.affiliationId = affiliationId;
    }

    public void setAffiliationName(String affiliationName) {
        this.affiliationName = affiliationName;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setPaperOrder(Integer paperOrder) {
        this.paperOrder = paperOrder;
    }
}
