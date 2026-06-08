package com.example.products.responseDTO;

import java.time.LocalDateTime;
import java.util.HashMap;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDTO {

    private String path;

    private String message;

    private Integer status;

    private LocalDateTime timeStamp;

    private HashMap<String, String> errors;
}
