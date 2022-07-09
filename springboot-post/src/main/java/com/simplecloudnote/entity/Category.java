package com.simplecloudnote.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    private String id;
    private String label;
    private String parentId;
    private Integer level;
    private String value;
    private List<Category> children;
}
