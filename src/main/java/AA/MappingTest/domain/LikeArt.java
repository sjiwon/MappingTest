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

    public LikeArt(Users user, Art art) {
        this.user = user;
        this.art = art;

        user.getLikeArtList().add(this);
    }

    @Override
    public String toString() {
        return "LikeArt{" +
                "id=" + id +
                ", user=" + user +
                '}';
    }
}
