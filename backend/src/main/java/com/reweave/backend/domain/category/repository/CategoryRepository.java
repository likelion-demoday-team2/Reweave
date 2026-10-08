package com.reweave.backend.domain.category.repository;

import com.reweave.backend.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    // 내 카테고리인지까지 한 번에 확인
    Optional<Category> findByIdAndUserId(Long id, Long userId);
}