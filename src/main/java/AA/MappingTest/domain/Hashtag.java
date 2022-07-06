package AA.MappingTest.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hashtag")
public class Hashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "hashtag")
    private List<ArtHashtag> artHashtagList = new ArrayList<>();

    public Hashtag(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "\nHashtag{" +
                "\n\tid=" + id +
                ", \n\tname='" + name + '\'' +
                "\n}";
    }
}
