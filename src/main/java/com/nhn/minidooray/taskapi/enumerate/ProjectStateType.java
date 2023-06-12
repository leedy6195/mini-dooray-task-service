package com.nhn.minidooray.taskapi.enumerate;

public enum ProjectStateType {
    활성, 휴면, 종료;

    public static String codeOf(ProjectStateType projectStateType) {
        switch (projectStateType) {
            case 활성:
                return "01";
            case 휴면:
                return "02";
            case 종료:
                return "03";
            default:
                return "01";
        }
    }
}
