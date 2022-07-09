package com.simplecloudnote.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private String id;
    private String title;
    private String digest;
    private String categoryId;
    private String path;
    private String mark;
    private LocalDate createDate;
    private LocalDate updateDate;
    private PostContent postContent;
    private String state;
    private Category category;
}
