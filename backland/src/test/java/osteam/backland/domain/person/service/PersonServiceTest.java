package osteam.backland.domain.person.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

//import static jdk.internal.org.objectweb.asm.util.CheckClassAdapter.verify;
//import static jdk.internal.org.objectweb.asm.util.CheckClassAdapter.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PersonServiceTest {

    @Mock
    private PersonOneRepository personOneRepository;

    @Mock
    private PhoneOneRepository phoneOneRepository;

    @Mock
    private PersonManyRepository personManyRepository;

    @Mock
    private PhoneManyRepository phoneManyRepository;

    @Mock
    private PersonOnlyRepository personOnlyRepository;

    @Mock
    private PhoneCreateService phoneCreateService;

    @InjectMocks
    private PersonCreateService personCreateService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testOneToOne() {
        // given
        String name = "John Doe";
        String phone = "123-456-7890";
        PersonOneToOne person = new PersonOneToOne();
        PhoneOneToOne phoneEntity = new PhoneOneToOne();
        phoneEntity.setPhone(phone);

        when(phoneCreateService.phoneOneReturn(phone, person)).thenReturn(phoneEntity);
        when(personOneRepository.save(any(PersonOneToOne.class))).thenReturn(person);
        when(phoneOneRepository.save(any(PhoneOneToOne.class))).thenReturn(phoneEntity);

        // when
        PersonDTO result = personCreateService.oneToOne(name, phone);

        // then
        assertEquals(name, result.getName());
        assertEquals(phone, result.getPhone());
        verify(personOneRepository, times(1)).save(any(PersonOneToOne.class));
        verify(phoneOneRepository, times(1)).save(any(PhoneOneToOne.class));
    }

    @Test
    void testOneToMany() {
        // given
        String name = "Jane Doe";
        String phone = "987-654-3210";
        PersonOneToMany person = new PersonOneToMany();
        PhoneOneToMany phoneEntity = new PhoneOneToMany();
        phoneEntity.setPhone(phone);

        when(phoneCreateService.phoneManyreturn(phone, person)).thenReturn(phoneEntity);
        when(personManyRepository.save(any(PersonOneToMany.class))).thenReturn(person);
        when(phoneManyRepository.save(any(PhoneOneToMany.class))).thenReturn(phoneEntity);

        // when
        PersonDTO result = personCreateService.oneToMany(name, phone);

        // then
        assertEquals(name, result.getName());
        assertEquals(phone, result.getPhone());
        verify(personManyRepository, times(1)).save(any(PersonOneToMany.class));
        verify(phoneManyRepository, times(1)).save(any(PhoneOneToMany.class));
    }

    @Test
    void testOne() {
        // given
        String name = "Jim Doe";
        String phone = "555-555-5555";
        PersonOnly person = new PersonOnly();
        person.setName(name);
        person.setPhone(phone);

        when(personOnlyRepository.save(any(PersonOnly.class))).thenReturn(person);

        // when
        PersonDTO result = personCreateService.one(name, phone);

        // then
        assertEquals(name, result.getName());
        assertEquals(phone, result.getPhone());
        verify(personOnlyRepository, times(1)).save(any(PersonOnly.class));
    }

    @Test
    void testCreateAll() {
        // given
        String name = "Jake Doe";
        String phone = "333-333-3333";

        doNothing().when(personCreateService).oneToOne(name, phone);
        doNothing().when(personCreateService).oneToMany(name, phone);
        doNothing().when(personCreateService).one(name, phone);

        // when
        String result = personCreateService.createAll(name, phone);

        // then
        assertEquals("Success", result);
        verify(personCreateService, times(1)).oneToOne(name, phone);
        verify(personCreateService, times(1)).oneToMany(name, phone);
        verify(personCreateService, times(1)).one(name, phone);
    }


}
