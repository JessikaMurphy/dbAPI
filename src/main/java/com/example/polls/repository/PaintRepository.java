package com.example.polls.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.polls.model.Paint;

@Repository
public interface PaintRepository extends JpaRepository<Paint, Long>{

	
	@Query("SELECT p FROM Paint p where p.user.id = :userId")
    List<Paint> findByUserId(@Param("userId") Long userId);
	
	@Transactional @Modifying @Query("DELETE FROM Paint p where p.user.id = :userId and p.id = :id")
    void deleteByUserAndPaintId(@Param("userId") Long userId, @Param("id") Long id);
}
