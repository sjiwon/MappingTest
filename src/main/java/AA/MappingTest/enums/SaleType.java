package AA.MappingTest.enums;

import lombok.Getter;

@Getter
public enum SaleType {
    GENERAL("일반"), AUCTION("경매");

    private final String description;

    SaleType(String description) {
        this.description = description;
    }
}
