package fr.maif.ludy.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.maif.ludy.web.rest.TestUtil;

public class GameLibraryDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GameLibraryDTO.class);
        GameLibraryDTO gameLibraryDTO1 = new GameLibraryDTO();
        gameLibraryDTO1.setId(1L);
        GameLibraryDTO gameLibraryDTO2 = new GameLibraryDTO();
        assertThat(gameLibraryDTO1).isNotEqualTo(gameLibraryDTO2);
        gameLibraryDTO2.setId(gameLibraryDTO1.getId());
        assertThat(gameLibraryDTO1).isEqualTo(gameLibraryDTO2);
        gameLibraryDTO2.setId(2L);
        assertThat(gameLibraryDTO1).isNotEqualTo(gameLibraryDTO2);
        gameLibraryDTO1.setId(null);
        assertThat(gameLibraryDTO1).isNotEqualTo(gameLibraryDTO2);
    }
}
