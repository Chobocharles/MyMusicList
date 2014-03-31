package com.learninghouse.mymusiclist.search;

import com.google.gson.annotations.Expose;

public class Result {

    @Expose
    private String GsearchResultClass;
    @Expose
    private String width;
    @Expose
    private String height;
    @Expose
    private String imageId;
    @Expose
    private String tbWidth;
    @Expose
    private String tbHeight;
    @Expose
    private String unescapedUrl;
    @Expose
    private String url;
    @Expose
    private String visibleUrl;
    @Expose
    private String title;
    @Expose
    private String titleNoFormatting;
    @Expose
    private String originalContextUrl;
    @Expose
    private String content;
    @Expose
    private String contentNoFormatting;
    @Expose
    private String tbUrl;

    public String getGsearchResultClass() {
        return GsearchResultClass;
    }

    public void setGsearchResultClass(String GsearchResultClass) {
        this.GsearchResultClass = GsearchResultClass;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getTbWidth() {
        return tbWidth;
    }

    public void setTbWidth(String tbWidth) {
        this.tbWidth = tbWidth;
    }

    public String getTbHeight() {
        return tbHeight;
    }

    public void setTbHeight(String tbHeight) {
        this.tbHeight = tbHeight;
    }

    public String getUnescapedUrl() {
        return unescapedUrl;
    }

    public void setUnescapedUrl(String unescapedUrl) {
        this.unescapedUrl = unescapedUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVisibleUrl() {
        return visibleUrl;
    }

    public void setVisibleUrl(String visibleUrl) {
        this.visibleUrl = visibleUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleNoFormatting() {
        return titleNoFormatting;
    }

    public void setTitleNoFormatting(String titleNoFormatting) {
        this.titleNoFormatting = titleNoFormatting;
    }

    public String getOriginalContextUrl() {
        return originalContextUrl;
    }

    public void setOriginalContextUrl(String originalContextUrl) {
        this.originalContextUrl = originalContextUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentNoFormatting() {
        return contentNoFormatting;
    }

    public void setContentNoFormatting(String contentNoFormatting) {
        this.contentNoFormatting = contentNoFormatting;
    }

    public String getTbUrl() {
        return tbUrl;
    }

    public void setTbUrl(String tbUrl) {
        this.tbUrl = tbUrl;
    }

}
