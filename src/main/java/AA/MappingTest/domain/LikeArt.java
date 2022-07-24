package AA.MappingTest.domain;

import AA.MappingTest.domain.IdClass.LikeArtId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "like_art")
@IdClass(LikeArtId.class) // 복합키 클래스
public class LikeArt {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "art_id", nullable = false)
    private Art art;

    //==테스트를 위한 toString()==//
    @Override
    public String toString() {
        return "LikeArt{" +
                "user=" + user +
                ", art=" + art +
                '}';
    }
}
