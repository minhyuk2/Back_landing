package osteam.backland.domain.phone.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import osteam.backland.domain.phone.entity.PhoneOneToMany;

public interface PhoneManyRepository extends JpaRepository<PhoneOneToMany,Long> {
}
