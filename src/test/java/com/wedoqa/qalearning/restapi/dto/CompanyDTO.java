package com.wedoqa.qalearning.restapi.dto;

public class CompanyDTO {

    private String name;
    private String catchPhrase;
    private String bs;

    public CompanyDTO() {
        super();
    }

    public CompanyDTO(String name, String catchPhrase, String bs) {
        this.name = name;
        this.catchPhrase = catchPhrase;
        this.bs = bs;
    }

    public String getName() {
        return name;
    }

    public CompanyDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getCatchPhrase() {
        return catchPhrase;
    }

    public CompanyDTO setCatchPhrase(String catchPhrase) {
        this.catchPhrase = catchPhrase;
        return this;
    }

    public String getBs() {
        return bs;
    }

    public CompanyDTO setBs(String bs) {
        this.bs = bs;
        return this;
    }
}
