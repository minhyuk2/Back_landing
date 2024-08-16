package osteam.backland.domain.person.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import osteam.backland.domain.person.entity.PersonOneToMany;
import osteam.backland.domain.person.entity.PersonOneToOne;
import osteam.backland.domain.person.entity.PersonOnly;
import osteam.backland.domain.person.entity.dto.PersonDTO;
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
    private final PhoneOneRepository phoneOneRepository;

    private final PersonManyRepository personManyRepository;
    private final PhoneManyRepository  phoneManyRepository;

    private final PersonOnlyRepository personOnlyRepository;

    //여기에는 final 키워드를 달기 어려운 이유가 뭘까?
    private  PhoneCreateService phoneCreateService;




    public PersonCreateService(PersonOneRepository personOneRepository,
                               PhoneOneRepository phoneOneRepository,
                               PersonManyRepository personManyRepository,
                               PhoneManyRepository phoneManyRepository,PersonOnlyRepository personOnlyRepository, PhoneCreateService phoneCreateService) {
        this.personOneRepository = personOneRepository;
        this.phoneOneRepository = phoneOneRepository;
        this.personManyRepository = personManyRepository;
        this.phoneManyRepository = phoneManyRepository;
        this.personOnlyRepository = personOnlyRepository;
        this.phoneCreateService = phoneCreateService;
    }

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
    public PersonDTO oneToOne(String name, String phone) {
        PersonOneToOne person = new PersonOneToOne();
        person.setName(name);


        //여기에는 PhoneOneToOne객체가 없는 상태로 전달이될텐데 서로 갖고 있는 채로 전달해주기 위해서는 어떻게 해야하는 것이지?
        //이렇게 세팅하게 되면 서로 객체를 공우하는 형식이 될 것 같은데
        //이렇게 하면 person객체에 이름만 저장된 채로 전달이 되어서 저장되는게 아닌가? 전화번호 같은 부분들은 저장되지 않아도 상관없나?
        //해결완료


        //기존에 작성했던 코드입니다.
//        PhoneOneToOne phoneEntity = new PhoneOneToOne();
//        phoneEntity.setPhone(phone);
//        phoneEntity.setPerson(person);


        //이렇게 객체를 생성해서 할당하면 안되는 형태인 것인가?
        PhoneOneToOne phoneEntity = phoneCreateService.phoneOneReturn(phone,person);
        person.setPhoneOneToOne(phoneEntity);


        personOneRepository.save(person);
        phoneOneRepository.save(phoneEntity);


        PersonDTO personDTO = new PersonDTO();
        personDTO.setName(person.getName());



        personDTO.setPhone(phoneEntity.getPhone());



        return personDTO;

    }

    /**
     * Phone과 OneToMany 관계인 person 생성
     */

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
