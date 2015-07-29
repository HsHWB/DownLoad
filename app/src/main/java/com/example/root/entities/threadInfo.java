package com.example.root.entities;

/**
 * Created by root on 15-7-23.
 */
public class threadInfo {

    private int threadId;
    private String threadUrl;
    private int start;
    private int end;
    private int finished;

    public threadInfo() {
        super();
    }

    public threadInfo(int threadId, String threadUrl, int start, int end, int finished) {

        this.threadId = threadId;
        this.threadUrl = threadUrl;
        this.start = start;
        this.end = end;
        this.finished = finished;
    }

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public String getThreadUrl() {
        return threadUrl;
    }

    public void setThreadUrl(String threadUrl) {
        this.threadUrl = threadUrl;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "threadInfo{" +
                "threadId='" + threadId + '\'' +
                ", threadUrl='" + threadUrl + '\'' +
                ", start=" + start +
                ", end=" + end +
                ", finished=" + finished +
                '}';
    }
}
