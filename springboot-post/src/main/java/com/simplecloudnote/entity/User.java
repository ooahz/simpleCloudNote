package com.simplecloudnote.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    private String username;
    private String userCode;
    private String id;
    private String password;
    private String avatar;
    private LocalDate createDate;
    private LocalDate updateDate;
    private String userStatus;
    private String salt;
    private String userMark;
}
