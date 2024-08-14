package osteam.backland.domain.person.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import osteam.backland.domain.person.entity.PersonOneToMany;

import java.util.List;
import java.util.Optional;

public interface PersonManyRepository extends JpaRepository<PersonOneToMany,Long> {
    List<PersonOneToMany> findPeopleByName(String name);
    Optional<PersonOneToMany> findOneByName(String name);
}
