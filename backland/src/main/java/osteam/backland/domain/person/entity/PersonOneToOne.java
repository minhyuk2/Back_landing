package osteam.backland.domain.person.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import osteam.backland.domain.phone.entity.PhoneOneToOne;
import osteam.backland.global.entity.PrimaryKeyEntity;


@Entity
@Setter
@Getter
@NoArgsConstructor
public class PersonOneToOne extends PrimaryKeyEntity {

    private String name;


    @OneToOne(
            mappedBy = "person",
            orphanRemoval = true,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private PhoneOneToOne phoneOneToOne;
}

