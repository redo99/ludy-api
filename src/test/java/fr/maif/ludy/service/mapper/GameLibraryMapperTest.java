package fr.maif.ludy.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GameLibraryMapperTest {

    private GameLibraryMapper gameLibraryMapper;

    @BeforeEach
    public void setUp() {
        gameLibraryMapper = new GameLibraryMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(gameLibraryMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(gameLibraryMapper.fromId(null)).isNull();
    }
}
