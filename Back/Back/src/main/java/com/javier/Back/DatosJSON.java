package com.javier.Back;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DatosJSON {

    private UUID _id;
    private String mscode;

    private String year;

    private String estCode;

    private float estimate;

    private float se;

    private float lowerCIB;

    private float upperCIB;

    private String flag;



}
