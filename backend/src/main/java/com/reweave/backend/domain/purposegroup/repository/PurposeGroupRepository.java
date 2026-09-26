package com.reweave.backend.domain.purposegroup.repository;

import com.reweave.backend.domain.purposegroup.entity.PurposeGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PurposeGroupRepository extends JpaRepository<PurposeGroup, Long> {

    List<PurposeGroup> findAllByUserId(Long userId);

    Optional<PurposeGroup> findByIdAndUserId(Long id, Long userId);
}
