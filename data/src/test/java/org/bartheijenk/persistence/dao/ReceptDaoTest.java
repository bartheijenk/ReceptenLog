package org.bartheijenk.persistence.dao;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReceptDaoTest {
//
//    @InjectMocks
//    private static ReceptDao receptDao;
//
//    @Mock
//    private static EntityManager em;
//
//    @BeforeAll
//    static void setUp() {
//        receptDao = ReceptDao.getInstance(em);
//    }
//
//    private void mockQuery(String name, List<Object[]> results) {
//        Query mockedQuery = mock(Query.class);
//        when(mockedQuery.getResultList()).thenReturn(results);
//        when(em.createQuery(name)).thenReturn(mockedQuery);
//    }
//
//
//    @Test
//    void getReceptenNaamOpId() {
//        List<Object[]> mockList = new ArrayList<>(List.of(
//                new Object[]{0L, "Test"},
//                new Object[]{1L, "Test2"}
//        ));
//        mockQuery("SELECT r.id, r.titel FROM Recept r", mockList);
//
//        Map<Long, String> expected = new HashMap<>();
//        expected.put(0L, "Test");
//        expected.put(1L, "Test2");
//
//        assertThat(receptDao.getReceptenNaamOpId()).isEqualTo(expected);
//    }
//
//    @Test
//    void getReceptenNaamOpIdPerTag() {
//        List<Object[]> mockList = new ArrayList<>(List.of(
//                new Object[]{0L, "Test"},
//                new Object[]{1L, "Test2"}
//        ));
//        mockQuery("SELECT r.id, r.titel FROM Recept r where :tag member of r.tags", mockList);
//
//        Map<Long, String> expected = new HashMap<>();
//        expected.put(0L, "Test");
//        expected.put(1L, "Test2");
//
//        assertThat(receptDao.getReceptenNaamOpIdPerCategorie(Categorie.builder().build())).isEqualTo(expected);

//    }

}