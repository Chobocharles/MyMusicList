package com.learninghouse.mymusiclist.jsonObject;

import com.google.gson.annotations.Expose;

public class ImageResults {

    @Expose
    private ResponseData responseData;
    @Expose
    private Object responseDetails;
    @Expose
    private Integer responseStatus;

    public ResponseData getResponseData() {
        return responseData;
    }

    public void setResponseData(ResponseData responseData) {
        this.responseData = responseData;
    }

    public Object getResponseDetails() {
        return responseDetails;
    }

    public void setResponseDetails(Object responseDetails) {
        this.responseDetails = responseDetails;
    }

    public Integer getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(Integer responseStatus) {
        this.responseStatus = responseStatus;
    }

}
