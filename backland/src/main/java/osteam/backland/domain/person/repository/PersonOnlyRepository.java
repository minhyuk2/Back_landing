package osteam.backland.domain.person.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import osteam.backland.domain.person.entity.PersonOneToMany;
import osteam.backland.domain.person.entity.PersonOnly;

import java.util.List;
import java.util.Optional;

public interface PersonOnlyRepository extends JpaRepository<PersonOnly,Long> {
    List<PersonOnly> findPeopleByName(String name);
    Optional<PersonOnly> findOneByName(String name);
    List<PersonOnly> findPeopleByPhone(String phone);
}
