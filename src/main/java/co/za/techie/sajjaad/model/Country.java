package co.za.techie.sajjaad.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Country {
    private String name;
    private String capital;
    private long population;
    private String flagUrl;
}

