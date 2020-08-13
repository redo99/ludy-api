package fr.maif.ludy.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link fr.maif.ludy.domain.LudyUser} entity.
 */
public class LudyUserDTO implements Serializable {

    private Long id;

    private Long score;

    private Long gems;


    private Long userId;

    private String userLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public Long getGems() {
        return gems;
    }

    public void setGems(Long gems) {
        this.gems = gems;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LudyUserDTO ludyUserDTO = (LudyUserDTO) o;
        if (ludyUserDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ludyUserDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LudyUserDTO{" +
            "id=" + getId() +
            ", score=" + getScore() +
            ", gems=" + getGems() +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            "}";
    }
}
