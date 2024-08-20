package osteam.backland.domain.phone.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import osteam.backland.domain.person.entity.PersonOneToMany;
import osteam.backland.domain.person.entity.PersonOneToOne;
import osteam.backland.domain.person.entity.dto.PhoneDTO;
import osteam.backland.domain.phone.entity.PhoneOneToMany;
import osteam.backland.domain.phone.entity.PhoneOneToOne;
import osteam.backland.domain.phone.repository.PhoneOneRepository;

@Service
//@RequiredArgsConstructor
@org.springframework.transaction.annotation.Transactional
public class PhoneCreateService {

    private final PhoneOneRepository phoneOneRepository;

    @Autowired
    public PhoneCreateService(PhoneOneRepository phoneOneRepository) {
        this.phoneOneRepository = phoneOneRepository;
    }

    @org.springframework.transaction.annotation.Transactional
    public PhoneDTO phoneOneReturn (String number, PersonOneToOne person) {

        PhoneOneToOne phone = new PhoneOneToOne(number,person);
//        phone.setPhone(number);
//        phone.setPerson(person);
        phoneOneRepository.save(phone);

        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.setPersonName(person.getName());
        phoneDTO.setPhone(number);

//        person.setPhoneOneToOne(phone);

        return phoneDTO;
    }

    @Transactional
    public PhoneOneToMany phoneManyReturn (String number, PersonOneToMany person) {

        PhoneOneToMany phone = new PhoneOneToMany();
        phone.setPhone(number);
        phone.setPersonOneToMany(person);
        return phone;
    }
}
