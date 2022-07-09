package com.simplecloudnote.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Log {
    private String id;
    private String info;
    private String userCode;
    private String url;
    private LocalDate date;
}
