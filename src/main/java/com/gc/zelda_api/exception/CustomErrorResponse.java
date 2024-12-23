package com.gc.zelda_api.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomErrorResponse {
    private int status;
    private String error;
    private Timestamp timestamp;

    public CustomErrorResponse(int status, String reason) {
        this.status = status;
        this.error = reason;
        timestamp = new Timestamp(System.currentTimeMillis());
    }
}
