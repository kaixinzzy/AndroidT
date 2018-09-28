package com.zzy.android.download;

public class DownloadStatus {

    private String NotificationTitle;// 通知栏标题
    private String NotificationDescription;// 通知栏描述

    private long taskId;// 任务id
    private String downloadPath;// 下载文件网络地址
    private String localFilePath;// 下载后的本地文件完整路径（带文件名）
    private Status status = Status.UNKNOWN;// 下载状态
    private String percent;// 下载进度百分比

    enum Status {
        PAUSED("暂停"),
        PENDING("下载中"),
        SUCCESSFUL("下载成功"),
        FAILED("下载失败"),
        UNKNOWN("未知状态");

        private String desc;//中文描述

        Status(String desc) {
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }

        @Override
        public String toString() {
            return desc;
        }
    }

    public DownloadStatus(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public DownloadStatus(String notificationTitle, String notificationDescription, String downloadPath) {
        NotificationTitle = notificationTitle;
        NotificationDescription = notificationDescription;
        this.downloadPath = downloadPath;
    }

    public String getNotificationTitle() {
        return NotificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        NotificationTitle = notificationTitle;
    }

    public String getNotificationDescription() {
        return NotificationDescription;
    }

    public void setNotificationDescription(String notificationDescription) {
        NotificationDescription = notificationDescription;
    }

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public String getLocalFilePath() {
        return localFilePath;
    }

    public void setLocalFilePath(String localFilePath) {
        this.localFilePath = localFilePath;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }
}
