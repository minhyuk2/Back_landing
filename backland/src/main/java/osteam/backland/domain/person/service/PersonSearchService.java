package osteam.backland.domain.person.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import osteam.backland.domain.person.controller.response.PersonResponse;
import osteam.backland.domain.person.entity.PersonOneToMany;
import osteam.backland.domain.person.entity.PersonOneToOne;
import osteam.backland.domain.person.entity.PersonOnly;
import osteam.backland.domain.person.repository.PersonManyRepository;
import osteam.backland.domain.person.repository.PersonOneRepository;
import osteam.backland.domain.person.repository.PersonOnlyRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class PersonSearchService {


    private final PersonManyRepository personManyRepository;
    private final PersonOneRepository personOneRepository;
    private final PersonOnlyRepository personOnlyRepository;



    //찾은 전체 객체들을 어떻게 리턴해야할지를 고민해봐야 함.
    public List<PersonResponse> findPeopleByName(String name) {

        List<PersonOneToOne> personOneToOnes = personOneRepository.findPeopleByName(name);
        List<PersonOnly> personOnlies = personOnlyRepository.findPeopleByName(name);
        List<PersonOneToMany> personOneToManies = personManyRepository.findPeopleByName(name);

        //이렇게 변수를 인라인화해서 넣어주는 형태로도 작성가능하다는 것을 알 수 있다.
        return Stream.of(
                personOnlies.stream().map(personOnly -> new PersonResponse(personOnly.getName(),personOnly.getPhone()))
//                personOneToOnes.stream().map(personOneToOne -> new PersonResponse(personOneToOne.getName(),personOneToOne.getPhoneOneToOne().getPhone())),
//                personOneToManies.stream().map(personOneToMany -> new PersonResponse(personOneToMany.getName(),personOneToMany.getPhoneOneToMany().toString()))
                )
                .flatMap(stream -> stream)
                .distinct()
                .collect(Collectors.toList());
        //해당하는 이름을 리턴해줬습니다.
    }

    public List<PersonResponse> getAllPeople() {
        List<PersonOneToOne> peopleOneToOne = personOneRepository.findAll(); // PersonOneToOne 조회
        List<PersonOneToMany> peopleOneToMany = personManyRepository.findAll(); // PersonOneToMany 조회
        List<PersonOnly> peopleOnly = personOnlyRepository.findAll();

        // 세 리스트를 스트림으로 변환하고 통합하여 PersonResponse 리스트로 변환
        List<PersonResponse> allPeople = Stream.of(
//                        peopleOneToOne.stream().map(personOneToOne -> new PersonResponse(personOneToOne.getName(), personOneToOne.getPhoneOneToOne().getPhone())),
//                        peopleOneToMany.stream().map(personOneToMany -> new PersonResponse(personOneToMany.getName(), personOneToMany.getPhoneOneToMany().toString())),
                        //여기서 OneToMany의 폰이 여러 개인 것은 어떻게 처리해야할까? List를 어떻게 불러와야할지 고민해볼 필요가 있음
                        peopleOnly.stream().map(personOnly -> new PersonResponse(personOnly.getName(),personOnly.getPhone()))
                ).flatMap(stream -> stream)// 각 스트림을 평탄화
                .distinct()
                .collect(Collectors.toList());

        return allPeople;
    }
    public  List<PersonResponse> findPeopleByPhone(String phone) {
        //only는 사람만 있는건가 phone이 없이? 그러면 써도 되는건가 그냥?
        List<PersonOnly> personOnly = personOnlyRepository.findPeopleByPhone(phone);

        List<PersonResponse> findPeople = Stream.of(
                personOnly.stream().map(personOnly1 -> new PersonResponse(personOnly1.getName(),personOnly1.getPhone())))
                        .flatMap(stream->stream)
                        .distinct()
                        .toList();

        return findPeople;

    }

}
