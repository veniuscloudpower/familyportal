package me.burninghandsapp.familyportal.repositories;

import me.burninghandsapp.familyportal.models.BlogPostRatings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface BlogPostRatingsRepository extends JpaRepository<BlogPostRatings,Integer> {

    @Query(value = "select count(*) from blog_post_ratings where rate_by_user_id=:rateByUserId",nativeQuery = true)
    Integer findCountByRateBy(@Param("rateByUserId") Long rateByUserId);

    @Query(value = "select count(*) from blog_post_ratings where rate_by_user_id=:rateByUserId and blog_item_id=:blogItemId",nativeQuery = true)
    Integer findCountByRateByAndBlog(@Param("blogItemId") Integer blogItemId,@Param("rateByUserId") Long rateByUserId);

    @Query(value = "select * from blog_post_ratings where blog_item_id=:blogItemId and rate_by_user_id=:rateByUserId",nativeQuery = true)
    BlogPostRatings findByBlog(@Param("blogItemId") Integer blogItemId,@Param("rateByUserId") Long rateByUserId);

    @Query(value = "select Round(AVG(rate)) from blog_post_ratings where blog_item_id=:blogItemId",nativeQuery = true)
    Integer findAvgByBlog(@Param("blogItemId") Integer blogItemId);
}
