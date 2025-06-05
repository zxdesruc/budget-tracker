package com.zxdesruc.budgettracker.service;

public interface EmailSyncService {

    void syncEmails();

    String getStatusByUid(String uid);

    void clearLogs();
}
