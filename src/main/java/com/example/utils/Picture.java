package com.example.utils;

public class Picture {
    String image;
    String image_type;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage_type() {
        return image_type;
    }

    public void setImage_type(String image_type) {
        this.image_type = image_type;
    }

    @Override
    public String toString() {
        return "picture{" +
                "image='" + image + '\'' +
                ", image_type='" + image_type + '\'' +
                '}';
    }
}
