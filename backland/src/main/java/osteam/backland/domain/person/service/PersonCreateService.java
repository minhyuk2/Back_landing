package osteam.backland.domain.person.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import osteam.backland.domain.person.entity.PersonOneToMany;
import osteam.backland.domain.person.entity.PersonOneToOne;
import osteam.backland.domain.person.entity.PersonOnly;
import osteam.backland.domain.person.entity.dto.PersonDTO;
import osteam.backland.domain.person.entity.dto.PhoneDTO;
import osteam.backland.domain.person.repository.PersonManyRepository;
import osteam.backland.domain.person.repository.PersonOneRepository;
import osteam.backland.domain.person.repository.PersonOnlyRepository;
import osteam.backland.domain.phone.entity.PhoneOneToMany;
import osteam.backland.domain.phone.entity.PhoneOneToOne;
import osteam.backland.domain.phone.repository.PhoneManyRepository;
import osteam.backland.domain.phone.repository.PhoneOneRepository;
import osteam.backland.domain.phone.service.PhoneCreateService;

@Service
@Slf4j
public class PersonCreateService {

    private final PersonOneRepository personOneRepository;

    private final PersonManyRepository personManyRepository;
    private final PhoneManyRepository  phoneManyRepository;

    private final PersonOnlyRepository personOnlyRepository;

    private  final PhoneCreateService phoneCreateService;

    @Autowired
    public PersonCreateService(PersonOneRepository personOneRepository,

                               PersonManyRepository personManyRepository,
                               PhoneManyRepository phoneManyRepository,PersonOnlyRepository personOnlyRepository, PhoneCreateService phoneCreateService) {
        this.personOneRepository = personOneRepository;
        this.personManyRepository = personManyRepository;
        this.phoneManyRepository = phoneManyRepository;
        this.personOnlyRepository = personOnlyRepository;
        this.phoneCreateService = phoneCreateService;
    }


    @org.springframework.transaction.annotation.Transactional
    public String createAll(String name, String phone) {
        try {
            log.info("Creating person ...");
            oneToOne(name, phone);
            oneToMany(name, phone);
            one(name, phone);
        } catch (Exception e) {
            log.error("Error occurred during creation: {}", e.getMessage());
            return "Fail";
        }
        return "Success";
    }



    /**
     * Phone과 OneToOne 관계인 person 생성
     */
    @org.springframework.transaction.annotation.Transactional
    public PersonDTO oneToOne(String name, String phone) {

        PersonOneToOne person = new PersonOneToOne();
        person.setName(name);

        //객체를 여기서 생성해서 넣어주는 경우
//        PhoneOneToOne phoneEntity = new PhoneOneToOne();
//        phoneEntity.setPhone(phone);
//        phoneEntity.setPerson(person);

        //왜 이렇게 함수를 통해 객체를 만들어서 리턴하면 null값이 전달되는 것이지

        PhoneDTO phoneDto = phoneCreateService.phoneOneReturn(phone, person);
//        log.info(phoneEntity.toString());

        personOneRepository.save(person);

        PersonDTO personDTO = new PersonDTO();

        personDTO.setName(name);
        personDTO.setPhone(phoneDto.getPhone());

        return personDTO;
    }

    /**
     * Phone과 OneToMany 관계인 person 생성
     */
    @Transactional
    public PersonDTO oneToMany(String name, String phone) {
        PersonOneToMany person = new PersonOneToMany();
        person.setName(name);
        //여기서 입력을 받을 때 phone 하나만 입력받게 되는건데 이 부분을 좀 수정하는게 필요하지 않을까


        PhoneOneToMany phoneEntity = phoneCreateService.phoneManyReturn(phone,person);
//        PhoneOneToMany phoneEntity = new PhoneOneToMany();
//        phoneEntity.setPhone(phone);
//        phoneEntity.setPersonOneToMany(person);
        //하나의 폰에 여러 개의 폰이 등록될 수 있으니까 애초에 입력을 받을 때 어떻게 받아야하는거지?

//        person.setPhoneOneToMany(phoneEntity);
        //phone이 여러 개가 들어갈 수 있게끔 처음부터 입력을 여러 개로 받아야할 것 같긴 함
        personManyRepository.save(person);
        phoneManyRepository.save(phoneEntity);

        PersonDTO personDTO = new PersonDTO();
        personDTO.setName(person.getName());
        personDTO.setPhone(phoneEntity.getPhone());
        return personDTO;
    }

    /**
     * person 하나로만 구성되어 있는 생성
     */
    @Transactional
    public PersonDTO one(String name, String phone) {
        PersonOnly person = new PersonOnly();
        person.setName(name);
        person.setPhone(phone);


        personOnlyRepository.save(person);
        PersonDTO personDTO = new PersonDTO();
        personDTO.setName(person.getName());
        personDTO.setPhone(phone);

        return personDTO;
    }
}
