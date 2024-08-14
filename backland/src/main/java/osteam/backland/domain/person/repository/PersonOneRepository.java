package osteam.backland.domain.person.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import osteam.backland.domain.person.entity.PersonOneToOne;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonOneRepository extends JpaRepository<PersonOneToOne,Long> {
    //findby는 jpa가 알아서 해결해주는 부분이다.
    List<PersonOneToOne> findPeopleByName (String name);

    Optional<PersonOneToOne> findOneByName (String name);
}
