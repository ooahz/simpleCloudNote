package com.simplecloudnote.controller;

import com.simplecloudnote.entity.Category;
import com.simplecloudnote.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("a/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("categories")
    public List<Category> toGetCategories(){
        List<Category> categoryList = categoryService.findList();
        return categoryList;
    }

    @PostMapping("save")
    public String toSave(String id,String value){
        String result = categoryService.save(id, value);
        return result;
    }


    @GetMapping("delete/{id}")
    public String toDelete(@PathVariable String id){
        String result = categoryService.delete(id);
        return result;
    }

}
