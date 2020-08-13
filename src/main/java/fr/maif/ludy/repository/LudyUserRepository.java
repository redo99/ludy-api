package fr.maif.ludy.repository;

import fr.maif.ludy.domain.LudyUser;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the LudyUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LudyUserRepository extends JpaRepository<LudyUser, Long> {

}
