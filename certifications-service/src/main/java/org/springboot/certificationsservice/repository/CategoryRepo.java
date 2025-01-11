package org.springboot.certificationsservice.repository;

import org.springboot.certificationsservice.category.CategoryApp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<CategoryApp,Integer> {
}
