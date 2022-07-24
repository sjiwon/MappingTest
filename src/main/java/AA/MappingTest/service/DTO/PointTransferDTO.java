package AA.MappingTest.service.DTO;

import AA.MappingTest.domain.enums.DealType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointTransferDTO {
    private DealType dealType;
    private Integer dealAmount;

    public PointTransferDTO(DealType dealType, Integer dealAmount) {
        this.dealType = dealType;
        this.dealAmount = dealAmount;
    }

    @Override
    public String toString() {
        return "\nPointTransferForm{" +
                "\n\tdealType=" + dealType +
                ", \n\tdealAmount=" + dealAmount +
                "\n}";
    }
}
