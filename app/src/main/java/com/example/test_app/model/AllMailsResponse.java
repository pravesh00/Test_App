package com.example.test_app.model;

import java.util.ArrayList;

public class AllMailsResponse {
    ArrayList<Email> mails;

    public AllMailsResponse(ArrayList<Email> mails) {
        this.mails = mails;
    }

    public ArrayList<Email> getMails() {
        return mails;
    }

    public void setMails(ArrayList<Email> mails) {
        this.mails = mails;
    }
}
