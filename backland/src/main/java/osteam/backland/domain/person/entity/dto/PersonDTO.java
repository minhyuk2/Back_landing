package osteam.backland.domain.person.entity.dto;

import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PersonDTO {
    private String name;
    private String phone;

}
