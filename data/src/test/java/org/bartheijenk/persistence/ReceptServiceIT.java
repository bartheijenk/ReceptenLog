package org.bartheijenk.persistence;

import org.bartheijenk.persistence.entity.Recept;
import org.bartheijenk.persistence.service.ReceptService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ReceptServiceIT {
    static EntityManager em = TestEntityManagerProvider.entityManager;

    private final ReceptService receptService = new ReceptService(em);

    private static Recept recept;

    @BeforeAll
    static void setUp() {
        recept = ReceptLijst.getLijst().get(0);
    }

    @AfterEach
    void clearTransaction() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }

    @Test
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    void saveRecept() {
        int previousSize = receptService.getAllReceptNamenEnID().size();
        Recept actual = receptService.saveRecept(recept);

        assertThat(actual.getId()).isNotNull();
        assertThat(actual.getIngredienten().stream().findFirst().get().getId()).isNotNull();
        assertThat(receptService.getAllReceptNamenEnID().size()).isGreaterThan(previousSize);

    }
}