package persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import persistence.dao.ReceptDao;
import persistence.entity.Recept;

import javax.persistence.EntityManager;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReceptServiceTest {

    @Mock
    private ReceptDao receptDao = mock(ReceptDao.class);

    @Mock
    private EntityManager em = mock(EntityManager.class);

    @InjectMocks
    private ReceptService receptService;

    @BeforeEach
    void setUp() {
        try (MockedStatic<ReceptDao> theMock = Mockito.mockStatic(ReceptDao.class)) {
            theMock.when(() -> ReceptDao.getInstance(em)).thenReturn(receptDao);
            receptService = new ReceptService(em);
        }
    }

    @Test
    void zoekRecepten() {
        //Recept met naam "Spaghetti Bolognese"
        Recept expected = ReceptLijst.getLijst().get(0);
        when(receptDao.findAll()).thenReturn(ReceptLijst.getLijst());


        assertThat(receptService.zoekRecepten("Spaghetti").get(0))
                .isEqualTo(expected);
    }
}