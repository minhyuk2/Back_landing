package osteam.backland.domain.person.controller.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class PersonResponse {

    @NonNull
    private String name;

    @NonNull
    private String phone;
}
