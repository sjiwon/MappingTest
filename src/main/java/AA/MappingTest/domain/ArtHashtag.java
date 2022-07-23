package AA.MappingTest.domain;

import AA.MappingTest.domain.IdClass.ArtHashtagId;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "art_hashtag")
@IdClass(ArtHashtagId.class)
public class ArtHashtag {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "art_id", nullable = false)
    private Art art;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtag_id", nullable = false)
    private Hashtag hashtag;

    @Override
    public String toString() {
        return "ArtHashtag{" +
                "art=" + art +
                ", hashtag=" + hashtag +
                '}';
    }
}
