package AA.MappingTest.enums;

import lombok.Getter;

@Getter
public enum PurchaseCategory {
    GENERAL("일반"), AUCTION("경매");

    private final String description;

    PurchaseCategory(String description) {
        this.description = description;
    }
}
