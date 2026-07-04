package hu.springbootrestfuljpa.books.repository;

import hu.springbootrestfuljpa.books.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
