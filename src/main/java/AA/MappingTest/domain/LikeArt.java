package AA.MappingTest.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "like_art")
public class LikeArt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "art_id", nullable = false)
    private Art art;

    public static LikeArt createLikeArt(Users user, Art art) {
        LikeArt likeArt = new LikeArt();
        likeArt.user = user;
        likeArt.art = art;
        return likeArt;
    }

    @Override
    public String toString() {
        return "LikeArt{" +
                "id=" + id +
                ", user=" + user +
                '}';
    }
}
