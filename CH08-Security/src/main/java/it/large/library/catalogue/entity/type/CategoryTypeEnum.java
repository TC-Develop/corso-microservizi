package it.large.library.catalogue.entity.type;

public enum CategoryTypeEnum {

    NOVEL("NOVEL"),
    THRILLER("THRILLER"),
    FANTASY("FANTASY"),
    ADVENTURE("ADVENTURE"),
    HISTORICAL("HISTORICAL"),
    POETRY("POETRY"),
    BIOGRAPHY("BIOGRAPHY"),
    AUTOBIOGRAPHY("AUTOBIOGRAPHY");

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    CategoryTypeEnum(String value) {
        this.value = value;
    }

}