package AA.MappingTest.enums;

import lombok.Getter;

@Getter
public enum DealType {
    JOIN("가입"), CHARGE("충전"), REFUND("환불"), USE("사용");

    private final String decription;

    DealType(String decription) {
        this.decription = decription;
    }
}
