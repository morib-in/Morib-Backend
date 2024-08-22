package org.morib.server.domain.category.infra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select e from Category e where " +
            "e.userId = :userId " +
            "and e.startDate between :startDate and :endDate " +
            "or e.endDate between :startDate and :endDate " +
            "or (e.startDate <= :startDate and e.endDate >= :endDate)")
    List<Category> findByUserIdInRange(Long userId, LocalDate startDate, LocalDate endDate);

}
