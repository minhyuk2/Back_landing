package osteam.backland.domain.person.entity.dto;


import lombok.*;
import osteam.backland.domain.person.entity.PersonOneToOne;
import osteam.backland.domain.phone.entity.PhoneOneToOne;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PhoneDTO {
    private String phone;
    private String personName;
}
