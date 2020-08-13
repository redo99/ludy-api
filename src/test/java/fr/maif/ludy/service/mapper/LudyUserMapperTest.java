package fr.maif.ludy.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LudyUserMapperTest {

    private LudyUserMapper ludyUserMapper;

    @BeforeEach
    public void setUp() {
        ludyUserMapper = new LudyUserMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(ludyUserMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(ludyUserMapper.fromId(null)).isNull();
    }
}
