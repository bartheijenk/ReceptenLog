package persistence.entity;

import lombok.*;
import persistence.util.Identifiable;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedQueries({
        @NamedQuery(name = "Recept.findAll", query = "select r from Recept r")
})
public class Recept implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titel;
    @Column(nullable = false)
    private int servings;
    private String bron;

    @Column(nullable = false)
    private String instructies;

    @Builder.Default
    @OneToMany(mappedBy = "ingredient",
            cascade = {CascadeType.PERSIST,
                    CascadeType.MERGE})
    @EqualsAndHashCode.Exclude
    private Set<IngredientInRecept> ingredienten = new HashSet<>();

    @Singular
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "recept_tag",
            joinColumns = @JoinColumn(name = "recept_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;
}
