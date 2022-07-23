package AA.MappingTest.service.DTO;

import AA.MappingTest.domain.enums.DealType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PointTransferDTO {
    private DealType dealType;

    private Integer dealAmount;



    @Override
    public String toString() {
        return "\nPointTransferForm{" +
                "\n\tdealType=" + dealType +
                ", \n\tdealAmount=" + dealAmount +
                "\n}";
    }
}
