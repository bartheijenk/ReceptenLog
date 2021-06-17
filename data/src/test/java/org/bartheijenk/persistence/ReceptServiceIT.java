package org.bartheijenk.persistence;

class ReceptServiceIT {
//    static EntityManager em = TestEntityManagerProvider.entityManager;
//
//    private final ReceptService receptService = new ReceptService(em);
//
//    private static Recept recept;
//
//    @BeforeAll
//    static void setUp() {
//        recept = ReceptLijst.getLijst().get(0);
//    }
//
//    @AfterEach
//    void clearTransaction() {
//        if (em.getTransaction().isActive()) {
//            em.getTransaction().rollback();
//        }
//    }
//
//    @Test
//    @SuppressWarnings("OptionalGetWithoutIsPresent")
//    void saveRecept() {
//        int previousSize = receptService.getAllReceptNamenEnID().size();
//        Recept actual = receptService.saveRecept(recept);
//
//        assertThat(actual.getId()).isNotNull();
//        assertThat(actual.getIngredienten().stream().findFirst().get().getId()).isNotNull();
//        assertThat(receptService.getAllReceptNamenEnID().size()).isGreaterThan(previousSize);
//
//    }
}