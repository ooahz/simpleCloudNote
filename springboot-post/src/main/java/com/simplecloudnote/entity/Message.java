package com.simplecloudnote.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String id;
    private String userCode;
    private String message;
    private LocalDate createDate;
    private User user;
}
