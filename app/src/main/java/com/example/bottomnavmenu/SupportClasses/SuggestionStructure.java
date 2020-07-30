package com.example.bottomnavmenu.SupportClasses;

public class SuggestionStructure {
    private String title;
    private String matter;

    public SuggestionStructure() {
    }

    public SuggestionStructure(String title, String matter) {
        this.title = title;
        this.matter = matter;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMatter() {
        return matter;
    }

    public void setMatter(String matter) {
        this.matter = matter;
    }
}
