package fr.maif.ludy.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A LudyUser.
 */
@Entity
@Table(name = "ludy_user")
public class LudyUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "score")
    private Long score;

    @Column(name = "gems")
    private Long gems;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getScore() {
        return score;
    }

    public LudyUser score(Long score) {
        this.score = score;
        return this;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public Long getGems() {
        return gems;
    }

    public LudyUser gems(Long gems) {
        this.gems = gems;
        return this;
    }

    public void setGems(Long gems) {
        this.gems = gems;
    }

    public User getUser() {
        return user;
    }

    public LudyUser user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LudyUser)) {
            return false;
        }
        return id != null && id.equals(((LudyUser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "LudyUser{" +
            "id=" + getId() +
            ", score=" + getScore() +
            ", gems=" + getGems() +
            "}";
    }
}
