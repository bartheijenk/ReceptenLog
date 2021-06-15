package org.bartheijenk.recepten.api.payload;

import org.bartheijenk.persistence.entity.Recept;

import java.util.Date;

public class MealplanResponse {
    private Long id;
    private Date date;
    private Boolean isAvondeten;
    private int servings;
    private Recept recept;
}
