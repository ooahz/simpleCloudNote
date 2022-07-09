package com.simplecloudnote.service;

import cn.hutool.core.util.ObjectUtil;
import com.simplecloudnote.dao.CategoryDao;
import com.simplecloudnote.entity.Category;
import com.simplecloudnote.utils.ReturnResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 十玖八柒
* @date 2022
* @GitHub https://github.com/ooahz
* @desc 分类功能
*/
@Service
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    /**
     * 获取分类列表
     * @return
     */
    public List<Category> findList(){
        List<Category> categoryByLevel = categoryDao.findCategoriesByLevel(1);
        List<Category> categories = toGetCategories(categoryByLevel);
        return categories;
    }

    /**
     * 保存分类
     * @param id
     * @param value
     * @return
     */
    public String save(String id,String value){
        int level = 0;
        if(!"0".equals(id)){
            Category categoryById = categoryDao.getCategoryById(id);
            level = categoryById.getLevel();
        }

        Category category = new Category();
        category.setId(String.valueOf(System.currentTimeMillis()));
        category.setParentId(id);
        category.setLabel(value);
        category.setLevel(level + 1);

        categoryDao.insertCategory(category);
        return ReturnResult.result("true","发送成功！");
    }

    /**
     * 递归获取子分类列表
     * @param categoryList
     * @return
     */
    public List<Category> toGetCategories(List<Category> categoryList){
        for (Category category : categoryList) {
            List<Category> categoryByParent = categoryDao.findCategoriesByParent(category.getId());

            if(ObjectUtil.isNotEmpty(categoryByParent)) {
                category.setChildren(categoryByParent);
                toGetCategories(categoryByParent);
            }
        }
        return categoryList;
    }

    /**
     * 删除分类
     * @param id
     * @return
     */
    public String delete(String id){
        categoryDao.deleteCategoryById(id);
        return ReturnResult.result("true","删除成功！");
    }

}
