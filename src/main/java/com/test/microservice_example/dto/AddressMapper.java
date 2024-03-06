// package com.test.microservice_example.dto;

// import com.test.microservice_example.dto.AddressUpdateDTO;
// import com.test.microservice_example.model.Address;

// import java.util.Optional;
// import java.util.function.Consumer;

// public class AddressMapper {

//     public static void mapAddressDtoToAddress(AddressUpdateDTO dto, Address address) {
//         Consumer<String> setter = str -> Optional.ofNullable(str).ifPresentOrElse(
//                 value -> setter.accept(value),
//                 () -> {} // No operation if the value is null
//         );

//         setter.accept(dto.getTitle());
//         setter.accept(dto.getFirstName());
//         setter.accept(dto.getLastName());
//         setter.accept(dto.getStreet());
//         setter.accept(dto.getHouseNumber());
//         setter.accept(dto.getPostalCode());
//         setter.accept(dto.getPostOfficeName());
//         setter.accept(dto.getCity());
//         setter.accept(dto.getCountry());
//     }
// }
