package osteam.backland.domain.person.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import osteam.backland.domain.person.controller.request.PersonCreateRequest;
import osteam.backland.domain.person.controller.response.PersonResponse;
import osteam.backland.domain.person.service.PersonCreateService;
import osteam.backland.domain.person.service.PersonSearchService;
import osteam.backland.domain.person.service.PersonUpdateService;

import java.util.List;
import java.util.Optional;

/**
 * PersonController
 * 등록 수정 검색 구현
 * <p>
 * 등록 - 입력된 이름과 전화번호를 personOnly, personOneToOne, personOneToMany에 저장
 * 수정 - 이미 등록된 전화번호가 입력될 경우 해당 전화번호의 소유주 이름을 변경
 * 검색 - 전체 출력, 이름으로 검색, 전화번호로 검색 구현, 검색은 전부 OneToMany 테이블로 진행.
 */
@Slf4j
@RestController
@RequestMapping("/person")
@RequiredArgsConstructor
public class PersonController {


    private final PersonCreateService personCreateService;

    private final PersonUpdateService personUpdateService;

    private final PersonSearchService personSearchService;


    /**
     * 등록 기능
     * personRequest를 service에 그대로 넘겨주지 말 것.
     *
     * @param personCreateRequest
     * @return 성공 시 이름 반환
     */
    @PostMapping
    public String person(@RequestBody PersonCreateRequest personCreateRequest) {

        //이미 있으면 수정하고 그냥 만드는 로직도 넣어주면 좋은 것이다.

        //각각의 이름을 입력받아서 넘겨주면 되는 것이다.
        personCreateService.createAll(personCreateRequest.getName(),personCreateRequest.getPhone());

        //여기 채워 넣어야 함
        return personCreateRequest.getName();
    }

    /**
     * 전체 검색 기능
     */
    @GetMapping
    public ResponseEntity<List<PersonResponse>> getPeople() {
        List<PersonResponse> people = personSearchService.getAllPeople();
        //성공한 경우에 ok를 반환하게 되는 것이다.
        //응답데이터로 people을 포함한 채로 200을 보내게 되는 것이다.
        return ResponseEntity.ok(people);
    }

    /**
     * 이름으로 검색
     *
     * @param name
     */
    @GetMapping("/name")
    public ResponseEntity<List<PersonResponse>> getPeopleByName(@RequestParam String name) {
        //RequestParam을 사용해야지 params로 넣어서 확인할 수 있게 되는 것이다.

        List<PersonResponse> people = personSearchService.findPeopleByName(name);

        if (people.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 데이터가 없으면 404 반환
        }

        return new ResponseEntity<>(people, HttpStatus.OK); // 결과가 있으면 200 반환
    }

    /**
     * 번호로 검색
     *
     * @param phone
     */
    @GetMapping("/phone")
    public ResponseEntity<List<PersonResponse>> getPeopleByPhone(@RequestParam String phone) {
        //번호로 찾을 때는 하나의 One이나 only에서만 찾는 형식으로 구현을 해봄
        //한 번에 One only many 다 저장되기 때문에 하나에서만 찾아도 됨
        List<PersonResponse> people = personSearchService.findPeopleByPhone(phone);
        if (people.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(people,HttpStatus.OK);
    }
}
