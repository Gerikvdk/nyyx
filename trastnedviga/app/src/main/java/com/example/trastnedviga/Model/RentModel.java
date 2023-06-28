package com.example.trastnedviga.Model;

import java.io.Serializable;


public class RentModel implements Serializable {

    public RentModel() {
    }

    public String category;
    public String date;
    public String description;
    public String id;
    public String pname;
    public String price;
    public String image;
    public String time;
    public String location;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRname() {
        return pname;
    }

    public void setRname(String pname) {
        this.pname = pname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRefImage() {
        return image;
    }

    public void setRefImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRdate() {
        return date;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}