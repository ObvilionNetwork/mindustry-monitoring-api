package ru.obvilion.mindHelper.model.map;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document
public class MapModel {
    private int id;
    private int authorId;

    private String name;
    private String author;
    private String description;
    private String build;
    private int width;
    private int height;

    private MapType type;
    private Instant date;
    private Instant lastEditDate;

    private int stars;
    private int size;

    public MapModel(int authorId, String name, String author, String description, String build, int width, int height,
                    Instant lastEditDate, MapType type, int size) {
        this.authorId = authorId;

        this.name = name;
        this.author = author;
        this.description = description;
        this.build = build;
        this.type = type;

        this.width = width;
        this.height = height;

        this.stars = 0;
        this.size = size;
        this.date = Instant.now();
        this.lastEditDate = lastEditDate;
    }


    /* Setters */
    public void setName(String name) {
        this.name = name;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(MapType type) {
        this.type = type;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public void setLastEditDate(Instant lastEditDate) {
        this.lastEditDate = lastEditDate;
    }


    /* Getters */
    public String getName() {
        return name;
    }

    public int getStars() {
        return stars;
    }

    public int getAuthorId() {
        return authorId;
    }

    public int getId() {
        return id;
    }

    public MapType getType() {
        return type;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public int getSize() {
        return size;
    }

    public String getBuild() {
        return build;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Instant getLastEditDate() {
        return lastEditDate;
    }

    public Instant getDate() {
        return date;
    }
}

