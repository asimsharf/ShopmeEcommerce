package com.shopme.admin.category.services;

import com.shopme.admin.category.exceptions.CategoryNotFoundException;
import com.shopme.admin.category.repositories.CategoryRepository;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@Transactional
public class CategoryService {
    public static final int CATEGORY_PER_PAGE = 4;

    @Autowired
    private final CategoryRepository categoryRepo;

    public CategoryService(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    public Page<Category> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, CATEGORY_PER_PAGE, sort);

        if (keyword != null) {
            return categoryRepo.findAll(keyword, pageable);
        }

        return categoryRepo.findAll(pageable);
    }

    public List<Category> listAll() {
        List<Category> rootCategories = categoryRepo.findRootCategories();
        return listHierarchicalCategories(rootCategories);
    }

    public List<Category> listHierarchicalCategories(List<Category> rootCategories) {
        List<Category> hierarchicalCategories = new ArrayList<>();

        for (Category rootCategory : rootCategories) {
            hierarchicalCategories.add(Category.copyFull(rootCategory));
            Set<Category> children = rootCategory.getChildren();
            for (Category subCategory : children) {
                String name = "--" + subCategory.getName();
                hierarchicalCategories.add(Category.copyFull(subCategory, name));
                listSubHierarchicalCategories(hierarchicalCategories, subCategory, 1);
            }
        }

        return hierarchicalCategories;
    }

    private void listSubHierarchicalCategories(List<Category> hierarchicalCategories, Category subCategory, int i) {
        Set<Category> children = subCategory.getChildren();
        if (children.size() > 0) {
            i++;
            for (Category subCategory2 : children) {
                String name = "--".repeat(Math.max(0, i)) + subCategory2.getName();
                hierarchicalCategories.add(Category.copyFull(subCategory2, name));
                listSubHierarchicalCategories(hierarchicalCategories, subCategory2, i);
            }
        }
    }

    public Category save(Category category) {
        return categoryRepo.save(category);
    }

    public List<Category> listCategoriesUsedInForm() {
        List<Category> categoriesUsedInForm = new ArrayList<>();
        Iterable<Category> categoriesInDB = categoryRepo.findAll(Sort.by("name").ascending());

        for (Category category : categoriesInDB) {
            if (category.getParent() == null) {
                categoriesUsedInForm.add(Category.copyIdAndName(category.getId(), category.getName()));

                Set<Category> children = category.getChildren();

                for (Category subCategory : children) {
                    String name = "--" + subCategory.getName();
                    categoriesUsedInForm.add(Category.copyIdAndName(subCategory.getId(), name));
                    listSubCategoriesUsedInForm(categoriesUsedInForm, subCategory, 1);
                }
            }
        }

        return categoriesUsedInForm;
    }

    private void listSubCategoriesUsedInForm(List<Category> categoriesUsedInForm, Category parent, int subLevel) {
        int newSubLevel = subLevel + 1;
        Set<Category> children = parent.getChildren();

        for (Category subCategory : children) {
            String name = "--".repeat(Math.max(0, newSubLevel)) + subCategory.getName();
            categoriesUsedInForm.add(Category.copyIdAndName(subCategory.getId(), name));
            listSubCategoriesUsedInForm(categoriesUsedInForm, subCategory, newSubLevel);
        }
    }

    public void updateCategoryEnabledStatus(Integer id, boolean enabled) {
        categoryRepo.updateEnabledStatus(id, enabled);
    }

    public void delete(Integer id) throws CategoryNotFoundException {
        Long countById = categoryRepo.countById(id);
        if (countById == null || countById == 0) {
            throw new CategoryNotFoundException("Could not find any category with ID " + id);
        }
        categoryRepo.deleteById(id);
    }

    public Category get(Integer id) {
        return categoryRepo.findById(id).get();
    }

    public Category isCategoryUnique(Integer id, String name, String alias) {
        Category categoryByName = categoryRepo.findByName(name);
        Category categoryByAlias = categoryRepo.findByAlias(alias);
        if (categoryByName == null && categoryByAlias == null)
            return null;

        boolean isCreatingNew = (id == null);
        if (isCreatingNew) {
            return Objects.requireNonNullElse(categoryByName, categoryByAlias);
        } else {
            if (categoryByName != null && categoryByAlias != null) {
                if (!Objects.equals(categoryByName.getId(), id) && !Objects.equals(categoryByAlias.getId(), id)) {
                    return categoryByName;
                }
            } else if (categoryByName != null) {
                if (!Objects.equals(categoryByName.getId(), id)) {
                    return categoryByName;
                }
            } else {
                if (!Objects.equals(categoryByAlias.getId(), id)) {
                    return categoryByAlias;
                }
            }
        }

        return null;
    }
}
