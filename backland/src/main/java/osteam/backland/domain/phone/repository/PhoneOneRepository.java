package osteam.backland.domain.phone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import osteam.backland.domain.phone.entity.PhoneOneToOne;

import java.util.Optional;

@Repository
public interface PhoneOneRepository extends JpaRepository<PhoneOneToOne,Long> {
    Optional<PhoneOneToOne> findByPhone(String phone);
}
