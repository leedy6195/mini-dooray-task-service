package com.nhn.minidooray.taskapi.enumerate;

public enum AuthorityType {
    관리자, 멤버, 손님;

    public static String codeOf(AuthorityType authorityType) {
        switch (authorityType) {
            case 관리자:
                return "01";
            case 멤버:
                return "02";
            case 손님:
            default:
                return "03";
        }
    }
}
