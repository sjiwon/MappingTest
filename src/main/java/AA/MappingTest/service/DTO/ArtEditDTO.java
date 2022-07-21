package AA.MappingTest.service.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArtEditDTO {
    private String description;

    @Override
    public String toString() {
        return "\nArtEditForm{" +
                "\n\tdescription='" + description + '\'' +
                "\n}";
    }
}
