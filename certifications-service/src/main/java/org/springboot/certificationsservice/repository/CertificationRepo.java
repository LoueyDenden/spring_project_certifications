package org.springboot.certificationsservice.repository;

import org.springboot.certificationsservice.certifications.CertificationsApp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CertificationRepo extends JpaRepository<CertificationsApp,Integer> {
    List<CertificationsApp> findAllByIdInOrderById(List<Integer> ids);

    @Query("SELECT c " +
            "FROM CertificationsApp c " +
            "WHERE (:name IS NULL OR :name = '' OR c.name LIKE %:name%) " +
            "ORDER BY c.id DESC")
    Page<CertificationsApp> findByNameContaining(@Param("name") String name,
                                                 Pageable pageable);
}
