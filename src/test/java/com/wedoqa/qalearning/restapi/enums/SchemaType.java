package com.wedoqa.qalearning.restapi.enums;

public enum SchemaType {
    USER_SCHEMA("schemas/user-schema.json"),
    USERS_SCHEMA("schemas/users-schema.json"),
    UNDEFINED_SCHEMA(null);

    private final String path;

    SchemaType(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
