package AA.MappingTest.domain;

import AA.MappingTest.idclass.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "like_artist")
@IdClass(UserId.class)
public class LikeArtist {
    @Id
    @Column(name = "user_id", updatable = false)
    private Long userId;

    @Id
    @Column(name = "artist_id", updatable = false)
    private Long artistId;
}
