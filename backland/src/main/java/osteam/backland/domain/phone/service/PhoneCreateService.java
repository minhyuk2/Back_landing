package osteam.backland.domain.phone.service;

import org.springframework.stereotype.Service;
import osteam.backland.domain.person.entity.PersonOneToMany;
import osteam.backland.domain.person.entity.PersonOneToOne;
import osteam.backland.domain.phone.entity.PhoneOneToMany;
import osteam.backland.domain.phone.entity.PhoneOneToOne;

@Service
public class PhoneCreateService {

    //필요한 phoneCreateservice메소드 생성


    public PhoneOneToOne phoneOneReturn (String number, PersonOneToOne person) {

        PhoneOneToOne phone = new PhoneOneToOne();
        phone.setPhone(number);
        phone.setPerson(person);

        return phone;

    }

    public PhoneOneToMany phoneManyReturn (String number, PersonOneToMany person) {

        PhoneOneToMany phone = new PhoneOneToMany();
        phone.setPhone(number);
        phone.setPersonOneToMany(person);
        return phone;
    }
}
