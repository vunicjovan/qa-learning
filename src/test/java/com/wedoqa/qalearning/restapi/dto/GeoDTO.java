package com.wedoqa.qalearning.restapi.dto;

public class GeoDTO {

    private String lat;
    private String lng;

    public GeoDTO() {
        super();
    }

    public GeoDTO(String lat, String lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public GeoDTO setLat(String lat) {
        this.lat = lat;
        return this;
    }

    public String getLng() {
        return lng;
    }

    public GeoDTO setLng(String lng) {
        this.lng = lng;
        return this;
    }
}
