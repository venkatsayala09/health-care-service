package com.health.care.exception;

import lombok.*;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "error")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ErrorResponse
{
    //General error message about nature of error
    private String message;

    //Specific errors in API request processing
    private List<String> details;

}
