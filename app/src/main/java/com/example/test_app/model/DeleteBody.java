package com.example.test_app.model;

import java.util.ArrayList;

public class DeleteBody {
    ArrayList<String> ids;

    public ArrayList<String> getIds() {
        return ids;
    }

    public void setIds(ArrayList<String> ids) {
        this.ids = ids;
    }

    public DeleteBody(ArrayList<String> ids) {
        this.ids = ids;
    }

    public DeleteBody() {
    }
}
