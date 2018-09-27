package com.zzy.android.download;

public class DownloadStatus {

    private String NotificationTitle;// 通知栏标题
    private String NotificationDescription;// 通知栏描述
    private String filePath;// 文件下载路径
    private long taskId;// 任务id

    public DownloadStatus(String filePath) {
        this.filePath = filePath;
    }

    public DownloadStatus(String notificationTitle, String notificationDescription, String filePath) {
        NotificationTitle = notificationTitle;
        NotificationDescription = notificationDescription;
        this.filePath = filePath;
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }
}
