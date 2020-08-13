package fr.maif.ludy.service.mapper;


import fr.maif.ludy.domain.*;
import fr.maif.ludy.service.dto.GameLibraryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link GameLibrary} and its DTO {@link GameLibraryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GameLibraryMapper extends EntityMapper<GameLibraryDTO, GameLibrary> {



    default GameLibrary fromId(Long id) {
        if (id == null) {
            return null;
        }
        GameLibrary gameLibrary = new GameLibrary();
        gameLibrary.setId(id);
        return gameLibrary;
    }
}
