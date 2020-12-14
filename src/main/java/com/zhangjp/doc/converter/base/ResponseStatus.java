package com.zhangjp.doc.converter.base;

/**
 * 响应码常量
 */
public enum ResponseStatus {
    /*公用状态码*/
    SUCCESS("000000", "success"),
    ERROR("999999", "error");

    private final String value;

    private final String reasonPhrase;

    ResponseStatus(String value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public String value() {
        return this.value;
    }
    public String msg() {
        return this.reasonPhrase;
    }
    @Override
    public String toString() {
        return this.value + " " + this.name();
    }
}
