/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idog.vis.academicvisapi.msapi;

import com.idog.vis.academicvisapi.msapi.MsAcademicQueryParam.ParamType;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author idoga
 */
public class MsAcademinApiExpr {

    private List<MsAcademicQueryParam> params;

    private MsAcademinApiExpr() {
    }
    
    public String generate() {
        StringBuilder expr = new StringBuilder();
        
        boolean combineWithPrevious = false;
        for (MsAcademicQueryParam param : params) {
            MsAcademicQueryParam nextParam = param.getNextParam();
            if (nextParam != null) {
                String operatorString = nextParam.getLogicalOperator().AND.toString();
                expr.append(operatorString).append(" (");
            }
            
            if (param.isComposite()) {
                expr.append("Composite(");
            }
            String paramValue = (param.getParamType() == ParamType.STRING_PARAM) ? "'" + param.getParamValue() + "'" : param.getParamValue();
            expr.append(param.getParamName()).append("=").append(paramValue).append(")");
            
            if (combineWithPrevious == true) {
                expr.append(")");
            }
            
            combineWithPrevious = nextParam != null;
        }
        
        String lastChar = expr.substring(expr.length() - 1);
        if (!lastChar.equals(")")) {
            expr.append(")");
        }
        
        return expr.toString();
    }

    public static class MsAcademinApiExprBuilder {

        private List<MsAcademicQueryParam> params = new ArrayList<>();

        public MsAcademicQueryParam addParam(String paramName, String paramValue) {
            MsAcademicQueryParam queryParam = new MsAcademicQueryParam(paramName, paramValue);
            this.params.add(queryParam);

            return queryParam;
        }
        
        public MsAcademicQueryParam addParam(String paramName, String paramValue, MsAcademicQueryParam.ParamType paramType) {
            MsAcademicQueryParam queryParam = new MsAcademicQueryParam(paramName, paramValue, paramType);
            this.params.add(queryParam);

            return queryParam;
        }        
        
        public MsAcademicQueryParam addParam(String paramName, String paramValue, MsAcademicQueryParam.ParamType paramType, boolean isComposite) {
            MsAcademicQueryParam queryParam = new MsAcademicQueryParam(paramName, paramValue, paramType, isComposite);
            this.params.add(queryParam);

            return queryParam;
        }                

        public MsAcademinApiExpr build() {
            MsAcademinApiExpr apiExpr = new MsAcademinApiExpr();
            apiExpr.params = this.params;
            
            return apiExpr;
        }
    }
}
