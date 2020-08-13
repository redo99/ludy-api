package fr.maif.ludy.service.mapper;


import fr.maif.ludy.domain.*;
import fr.maif.ludy.service.dto.LudyUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link LudyUser} and its DTO {@link LudyUserDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface LudyUserMapper extends EntityMapper<LudyUserDTO, LudyUser> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    LudyUserDTO toDto(LudyUser ludyUser);

    @Mapping(source = "userId", target = "user")
    LudyUser toEntity(LudyUserDTO ludyUserDTO);

    default LudyUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        LudyUser ludyUser = new LudyUser();
        ludyUser.setId(id);
        return ludyUser;
    }
}
