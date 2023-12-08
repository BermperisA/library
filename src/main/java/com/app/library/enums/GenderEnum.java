package com.app.library.enums;

public enum GenderEnum {
    MALE("m"),
    FEMALE("f");

    final String value;

    GenderEnum(String value) {
        this.value = value;
    }

    public static GenderEnum fromString(final String gender) {
        if (gender.isBlank()) {
            throw new IllegalArgumentException("GenderEnum cannot be retrieved from null value");
        }
        return switch (gender) {
            case "m" -> MALE;
            case "f" -> FEMALE;
            default -> throw new IllegalArgumentException("Illegal '" + gender + "' value");
        };

    }
}