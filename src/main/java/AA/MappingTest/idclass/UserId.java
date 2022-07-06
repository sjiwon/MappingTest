package AA.MappingTest.idclass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserId implements Serializable {
    private Long userId;

    private Long artistId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserId userId = (UserId) o;
        return Objects.equals(getUserId(), userId.getUserId()) && Objects.equals(getArtistId(), userId.getArtistId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getArtistId());
    }
}
