package com.example.veggietracker.model;

public enum Category {
    vegetables("vegetables"), fruits("fruits");

    private String category;

    Category(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
