package org.bartheijenk.recepten.api.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MealplanRequest {

    private Long id;
    private String date;
    private Boolean isAvondeten;
    private int servings;
    private Long recept;
}
