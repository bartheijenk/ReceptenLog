package org.bartheijenk.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NamedQueries({
        @NamedQuery(name = "MealplanItem.findAll", query = "select r from MealplanItem r")
})
public class MealplanItem implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate date;
    private Boolean isAvondeten;
    private int servings;

    @ManyToOne
    @JoinColumn(name = "recept_id")
    private Recept recept;
}
