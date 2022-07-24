package AA.MappingTest.service.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArtEditDTO {
    private String description;

    public ArtEditDTO(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "\nArtEditForm{" +
                "\n\tdescription='" + description + '\'' +
                "\n}";
    }
}
