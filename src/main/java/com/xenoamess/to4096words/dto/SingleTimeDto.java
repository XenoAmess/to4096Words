package com.xenoamess.to4096words.dto;

import com.xenoamess.x8l.databind.X8lDataBeanFieldMark;

public class SingleTimeDto {
    public SingleTimeDto() {

    }

    /**
     * index
     */
    @X8lDataBeanFieldMark(paths = "CONTENT_NODE[0]>ATTRIBUTE(i)")
    private Integer i;

    /**
     * sum
     */
    @X8lDataBeanFieldMark(paths = "CONTENT_NODE[0]>ATTRIBUTE(s)")
    private Integer s;

    /**
     * hash
     */
    @X8lDataBeanFieldMark(paths = "CONTENT_NODE[0]>ATTRIBUTE(h)")
    private String h;

    /**
     * value
     */
    @X8lDataBeanFieldMark(paths = "CONTENT_NODE[0]>TEXT_CONTENT")
    private String value;

    //-----getters and setters

    public Integer getI() {
        return i;
    }

    public void setI(Integer i) {
        this.i = i;
    }

    public Integer getS() {
        return s;
    }

    public void setS(Integer s) {
        this.s = s;
    }

    public String getH() {
        return h;
    }

    public void setH(String h) {
        this.h = h;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
