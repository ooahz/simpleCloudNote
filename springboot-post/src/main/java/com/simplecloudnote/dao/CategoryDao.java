package com.simplecloudnote.dao;

import com.simplecloudnote.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryDao {
    void insertCategory(Category category);

    Category getCategoryById(String id);

    List<Category> findCategoriesByLevel(int level);

    List<Category> findCategoriesByParent(String parentId);

    void deleteCategoryById(String id);
}
