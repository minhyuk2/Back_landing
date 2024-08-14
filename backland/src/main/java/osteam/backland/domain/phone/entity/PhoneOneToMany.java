package osteam.backland.domain.phone.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import osteam.backland.domain.person.entity.PersonOneToMany;
import osteam.backland.global.entity.PrimaryKeyEntity;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PhoneOneToMany extends PrimaryKeyEntity {

    private String phone;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(
            name = "fk_person_id",
            referencedColumnName = "id"
    )
    private PersonOneToMany personOneToMany;
}
