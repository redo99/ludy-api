package fr.maif.ludy.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.maif.ludy.web.rest.TestUtil;

public class LudyUserTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LudyUser.class);
        LudyUser ludyUser1 = new LudyUser();
        ludyUser1.setId(1L);
        LudyUser ludyUser2 = new LudyUser();
        ludyUser2.setId(ludyUser1.getId());
        assertThat(ludyUser1).isEqualTo(ludyUser2);
        ludyUser2.setId(2L);
        assertThat(ludyUser1).isNotEqualTo(ludyUser2);
        ludyUser1.setId(null);
        assertThat(ludyUser1).isNotEqualTo(ludyUser2);
    }
}
