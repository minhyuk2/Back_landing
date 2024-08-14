package osteam.backland.domain.person.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import osteam.backland.domain.person.entity.PersonOneToMany;
import osteam.backland.domain.person.entity.PersonOneToOne;
import osteam.backland.domain.person.entity.PersonOnly;
import osteam.backland.domain.person.repository.PersonManyRepository;
import osteam.backland.domain.person.repository.PersonOneRepository;
import osteam.backland.domain.person.repository.PersonOnlyRepository;
import osteam.backland.domain.phone.entity.PhoneOneToMany;
import osteam.backland.domain.phone.entity.PhoneOneToOne;
import osteam.backland.domain.phone.repository.PhoneManyRepository;
import osteam.backland.domain.phone.repository.PhoneOneRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PersonUpdateService {

    //기존에 있던 것을 찾아서 내용을 수정할 수 있게끔 만들어야 한다.
    private final PersonOneRepository personOneRepository;
    private final PhoneOneRepository phoneOneRepository;
    private final PhoneManyRepository phoneManyRepository;
    private final PersonManyRepository personManyRepository;
    private PersonOnlyRepository personOnlyRepository;

    public PersonUpdateService(PersonOneRepository personOneRepository, PhoneOneRepository phoneOneRepository, PhoneManyRepository phoneManyRepository, PersonManyRepository personManyRepository) {
        this.personOneRepository = personOneRepository;
        this.phoneOneRepository = phoneOneRepository;
        this.phoneManyRepository = phoneManyRepository;
        this.personManyRepository = personManyRepository;
    }

    @Transactional
    public PersonOneToOne updatePersonByName(String name, String newName, String newPhone) {
        // 이름으로 Person을 찾기
        //등록할 때 한 번에 3개를 등록하기 때문에 하나 변경할 때 다 변경해주면 된다.
        Optional<PersonOneToOne> personOpt = personOneRepository.findOneByName(name);
        //하나라도 있으면 찾아서 다 변경
        if (personOpt.isPresent()) {
            Optional<PersonOneToMany> personOpt2 = personManyRepository.findOneByName(name);
            Optional<PersonOnly> personOpt3 = personOnlyRepository.findOneByName(name);

            PersonOneToOne person = personOpt.get();
            PersonOneToMany person2 = personOpt2.get();
            PersonOnly person3 = personOpt3.get();

            //이걸 꼭 다 해줘야하는건가? 객체맵핑을 잘 이용하면 하나만 변경해도 될 수 있는게 아닐까?
            // 새로운 이름으로 변경
            person.setName(newName);
            person2.setName(newName);
            person3.setName(newName);
            person3.setPhone(newPhone);

            PhoneOneToOne phone = person.getPhoneOneToOne();
            //Many일 때의 phone 처리를 하나만 받게 되면 어떻게 할지 -> 다른 함수를 만들어서 분기를 해야할지 고민
//            PhoneOneToMany phone2 = person2.getPhoneOneToMany();
            PhoneOneToMany phones = new PhoneOneToMany();
            phones.setPhone(newPhone);

            if (phone != null) {
                phone.setPhone(newPhone);
                phoneOneRepository.save(phone);  // Phone 엔티티 저장
                phoneManyRepository.save(phones);
            }

            List<PhoneOneToMany> phones2 = person2.getPhoneOneToMany();
            phones2.add(phones);

            person.setPhoneOneToOne(phone);
            person2.setPhoneOneToMany(phones2);

            //oneToMany에는 폰을 추가해서 넣어주는 형태인 것이다.


            personOneRepository.save(person);
            personManyRepository.save(person2);
            personOnlyRepository.save(person3);
            return person;
        } else {
            //아무것도 없는 경우에는 return null
            return null;
        }
    }

    @Transactional
    public PhoneOneToOne updatePhoneByNumber(String oldPhoneNumber, String newPhoneNumber) {
        // 전화번호로 Phone을 찾기
        //얘도 Many도 이걸 만들어줘야하는 것인지 생각해봐야 함.
        Optional<PhoneOneToOne> phoneOpt = phoneOneRepository.findByPhone(oldPhoneNumber);

        if (phoneOpt.isPresent()) {
            PhoneOneToOne phone = phoneOpt.get();
            phone.setPhone(newPhoneNumber);
            phoneOneRepository.save(phone);
            return phone;
        } else {
            //controller에서 null이 리턴된 경우에 대한 예외처리를 진행해주면 된다.
            return null;
        }
    }

}
