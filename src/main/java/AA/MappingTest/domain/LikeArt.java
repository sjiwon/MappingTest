package AA.MappingTest.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    //==생성 메소드==//
    public static LikeArt createLikeArt(Users user, Art art) { // 작품찜 할 경우 생성해서 insert
        LikeArt likeArt = new LikeArt();
        likeArt.user = user;
        likeArt.art = art;
        return likeArt;
    }

    //==테스트를 위한 toString()==//
    @Override
    public String toString() {
        return "LikeArt{" +
                "id=" + id +
                ", user=" + user +
                '}';
    }
}
