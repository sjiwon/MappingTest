package AA.MappingTest.domain.IdClass;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArtHashtagId implements Serializable {
    private Long art;
    private Long hashtag;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtHashtagId that = (ArtHashtagId) o;
        return Objects.equals(getArt(), that.getArt()) && Objects.equals(getHashtag(), that.getHashtag());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getArt(), getHashtag());
    }
}
