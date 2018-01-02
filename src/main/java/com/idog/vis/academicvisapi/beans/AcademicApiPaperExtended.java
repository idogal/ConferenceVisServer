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
public class AcademicApiPaperExtended {

    @JsonProperty("DN")
    public String displayName;

    @JsonProperty("VFN")
    public String venueFullName;

    @JsonProperty("VSN")
    public String venueShortName;

    @JsonProperty("V")
    public String journalVolume;

    @JsonProperty("I")
    public String journalIssue;
}
