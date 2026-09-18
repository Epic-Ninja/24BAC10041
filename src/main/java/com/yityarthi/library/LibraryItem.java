package com.yityarthi.library;

public abstract class LibraryItem {
    private final String id;
    private String title;

    protected LibraryItem(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public abstract String getItemType();
}
