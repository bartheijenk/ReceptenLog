package org.bartheijenk.persistence;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReceptServiceTest {
//
//    @Mock
//    private ReceptDao receptDao = mock(ReceptDao.class);
//
//    @Mock
//    private EntityManager em = mock(EntityManager.class);
//
//    @InjectMocks
//    private ReceptService receptService;
//
//    @BeforeEach
//    void setUp() {
//        try (MockedStatic<ReceptDao> theMock = Mockito.mockStatic(ReceptDao.class)) {
//            theMock.when(() -> ReceptDao.getInstance(em)).thenReturn(receptDao);
//            receptService = new ReceptService(em);
//        }
//    }
//
//    @Test
//    void zoekRecepten() {
//        //Recept met naam "Spaghetti Bolognese"
//        Recept expected = ReceptLijst.getLijst().get(0);
//        when(receptDao.findAll()).thenReturn(ReceptLijst.getLijst());
//
//
//        assertThat(receptService.zoekRecepten("Spaghetti").get(0))
//                .isEqualTo(expected);
//    }
}