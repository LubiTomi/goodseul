package data.repository;

import data.entity.ReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository <ReviewEntity, Integer> {
    Page<ReviewEntity> findByGIdx_GoodseulNameContainingAndUIdx_NameContaining(String goodseulName, String name, Pageable pageable);
}

