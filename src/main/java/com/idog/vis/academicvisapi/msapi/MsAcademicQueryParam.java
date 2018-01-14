/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi.msapi;

/**
 *
 * @author idoga
 */
public class MsAcademicQueryParam {

    private boolean isComposite = false;
    private ParamType paramType = ParamType.STRING_PARAM;

    private String paramName;
    private String paramValue;

    private LogicalOperator logicalOperator;
    private MsAcademicQueryParam nextParam;

    public MsAcademicQueryParam() {
    }

    public MsAcademicQueryParam(String paramName, String paramValue) {
        this.paramName = paramName;
        this.paramValue = paramValue;
    }

    public MsAcademicQueryParam(String paramName, String paramValue, ParamType paramType) {
        this.paramName = paramName;
        this.paramValue = paramValue;
        this.paramType = paramType;
    }

    public MsAcademicQueryParam(String paramName, String paramValue, ParamType paramType, boolean isComposite) {
        this.paramName = paramName;
        this.paramValue = paramValue;
        this.paramType = paramType;
        this.isComposite = isComposite;
    }

    public MsAcademicQueryParam addNextParam(String paramName, String paramValue, LogicalOperator logicalOperator) {
        this.nextParam = new MsAcademicQueryParam(paramName, paramValue);
        return this.nextParam;
    }

    public MsAcademicQueryParam addNextParam(String paramName, String paramValue, LogicalOperator logicalOperator, ParamType paramType) {
        this.nextParam = new MsAcademicQueryParam(paramName, paramValue, paramType);
        return this.nextParam;
    }
    
    public MsAcademicQueryParam addNextParam(String paramName, String paramValue, LogicalOperator logicalOperator, ParamType paramType, boolean isComposite) {
        this.nextParam = new MsAcademicQueryParam(paramName, paramValue, paramType, isComposite);
        return this.nextParam;
    }

    public String getParamName() {
        return paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public boolean isComposite() {
        return isComposite;
    }
    
    public MsAcademicQueryParam getNextParam() {
        return nextParam;
    }

    public LogicalOperator getLogicalOperator() {
        return logicalOperator;
    }

    public ParamType getParamType() {
        return paramType;
    }

    public enum LogicalOperator {
        AND,
        OR
    }

    public enum ParamType {
        STRING_PARAM,
        NONSTRING_PARAM
    }
}
