package com.health.my_heart.ui.content;

import com.health.my_heart.domain.model.ContentType;

public class ContentCard {
    private final String title;
    private final ContentType contentType;
    private final int bgResId;

    public ContentCard(String title, ContentType contentType, int bgResId) {
        this.title = title;
        this.contentType = contentType;
        this.bgResId = bgResId;
    }

    public String getTitle() {
        return title;
    }

    public int getBgResId() {
        return bgResId;
    }

    public ContentType getContentType() {
        return contentType;
    }
}
