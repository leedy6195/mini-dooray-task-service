package com.nhn.minidooray.taskapi.domain.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@Builder
public class ResultResponse<T> {

    private Header header;
    private List<T> result;

    public long getTotalCount() {
        return result == null ? 0 : result.size();
    }

    @Getter
    @Setter
    @Builder
    public static class Header {
        private boolean isSuccessful;
        private int resultCode;
        private String resultMessage;
    }

    public static <T> ResultResponse<T> created(List<T> result) {
        return buildResultResponse(true, HttpStatus.CREATED.value(), "created successfully", result);
    }

    public static <T> ResultResponse<T> updated(List<T> result) {
        return buildResultResponse(true, HttpStatus.OK.value(), "updated successfully", result);
    }

    public static <T> ResultResponse<T> deleted(List<T> result) {
        return buildResultResponse(true, HttpStatus.NO_CONTENT.value(), "deleted successfully", result);
    }

    public static <T> ResultResponse<T> fetched(List<T> result) {
        return buildResultResponse(true, HttpStatus.OK.value(), "retrieved successfully", result);
    }
    private static <T> ResultResponse<T> buildResultResponse(boolean isSuccessful, int resultCode, String resultMessage, List<T> result) {
        return ResultResponse.<T>builder()
                .header(Header.builder()
                        .isSuccessful(isSuccessful)
                        .resultCode(resultCode)
                        .resultMessage(resultMessage)
                        .build())
                .result(result)
                .build();
    }

}