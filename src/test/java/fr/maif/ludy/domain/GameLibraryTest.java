package fr.maif.ludy.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.maif.ludy.web.rest.TestUtil;

public class GameLibraryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GameLibrary.class);
        GameLibrary gameLibrary1 = new GameLibrary();
        gameLibrary1.setId(1L);
        GameLibrary gameLibrary2 = new GameLibrary();
        gameLibrary2.setId(gameLibrary1.getId());
        assertThat(gameLibrary1).isEqualTo(gameLibrary2);
        gameLibrary2.setId(2L);
        assertThat(gameLibrary1).isNotEqualTo(gameLibrary2);
        gameLibrary1.setId(null);
        assertThat(gameLibrary1).isNotEqualTo(gameLibrary2);
    }
}
