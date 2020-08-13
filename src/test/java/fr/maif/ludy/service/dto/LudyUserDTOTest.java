package fr.maif.ludy.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.maif.ludy.web.rest.TestUtil;

public class LudyUserDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LudyUserDTO.class);
        LudyUserDTO ludyUserDTO1 = new LudyUserDTO();
        ludyUserDTO1.setId(1L);
        LudyUserDTO ludyUserDTO2 = new LudyUserDTO();
        assertThat(ludyUserDTO1).isNotEqualTo(ludyUserDTO2);
        ludyUserDTO2.setId(ludyUserDTO1.getId());
        assertThat(ludyUserDTO1).isEqualTo(ludyUserDTO2);
        ludyUserDTO2.setId(2L);
        assertThat(ludyUserDTO1).isNotEqualTo(ludyUserDTO2);
        ludyUserDTO1.setId(null);
        assertThat(ludyUserDTO1).isNotEqualTo(ludyUserDTO2);
    }
}
