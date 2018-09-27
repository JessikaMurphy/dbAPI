package com.example.polls.payload;

import java.util.List;

import com.example.polls.model.Paint;

public class PaintResponse {

	private Boolean success;
    private List<Paint> userPaints;

    public PaintResponse(Boolean success, List<Paint> userPaints) {
        this.success = success;
        this.userPaints = userPaints;
    }
    public Boolean getSuccess() {
        return success;
    }
    public void setSuccess(Boolean success) {
        this.success = success;
    }
    public List<Paint> getPaintList() {
        return userPaints;
    }
    public void setPaintList(List<Paint> userPaints) {
        this.userPaints = userPaints;
    }
}
