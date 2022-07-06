package AA.MappingTest.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "art_hashtag")
public class ArtHashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "art_id", nullable = false)
    private Art art;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtag_id", nullable = false)
    private Hashtag hashtag;

    public ArtHashtag(Art art, Hashtag hashtag) {
        this.art = art;
        this.hashtag = hashtag;
    }

    @Override
    public String toString() {
        return "\nArtHashtag{" +
                "\n\tid=" + id +
                ", \n\thashtag=" + hashtag +
                "\n}";
    }
}
