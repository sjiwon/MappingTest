package AA.MappingTest.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "like_artist")
public class LikeArtist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "user_id"
            ),
            @JoinColumn(
                    name = "artist_id",
                    referencedColumnName = "user_id"
            )
    })
    private Users user;
}
