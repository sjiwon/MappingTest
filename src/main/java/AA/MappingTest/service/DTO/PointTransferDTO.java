package AA.MappingTest.service.DTO;

import AA.MappingTest.enums.DealType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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
