package com.cmpe295.mapsio.domain;

/**
 * @author arunabh.shrivastava
 */
public class Error {

    private String error;

    public Error() {
    }

    public Error(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
