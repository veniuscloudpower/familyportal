package me.burninghandsapp.familyportal.repositories;

import me.burninghandsapp.familyportal.models.BlogPostRatings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogPostRatingsRepository extends JpaRepository<BlogPostRatings,Integer> {

    @Query(value = "select count(*) from blog_post_ratings where rate_by_user_id=:rate_by_user_id",nativeQuery = true)
    Integer findCountByRateBy(@Param("rate_by_user_id") Long rate_by_user_id);
}
