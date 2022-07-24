package AA.MappingTest.domain.IdClass;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeArtId implements Serializable {
    private Long user;
    private Long art;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LikeArtId likeArtId = (LikeArtId) o;
        return Objects.equals(getUser(), likeArtId.getUser()) && Objects.equals(getArt(), likeArtId.getArt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getArt());
    }
}
