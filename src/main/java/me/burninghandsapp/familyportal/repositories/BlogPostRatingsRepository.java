package me.burninghandsapp.familyportal.repositories;

import me.burninghandsapp.familyportal.models.BlogPostRatings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogPostRatingsRepository extends JpaRepository<BlogPostRatings,Integer> {

    @Query(value = "select count(*) from blog_post_ratings where rate_by_user_id=:rate_by_user_id",nativeQuery = true)
    Integer findCountByRateBy(@Param("rate_by_user_id") Long rate_by_user_id);

    @Query(value = "select count(*) from blog_post_ratings where rate_by_user_id=:rate_by_user_id and blog_item_id=:blog_item_id",nativeQuery = true)
    Integer findCountByRateByAndBlog(@Param("blog_item_id") Integer blog_item_id,@Param("rate_by_user_id") Long rate_by_user_id);

    @Query(value = "select * from blog_post_ratings where blog_item_id=:blog_item_id and rate_by_user_id=:rate_by_user_id",nativeQuery = true)
    BlogPostRatings findByBlog(@Param("blog_item_id") Integer blog_item_id,@Param("rate_by_user_id") Long rate_by_user_id);

    @Query(value = "select Round(AVG(rate)) from blog_post_ratings where blog_item_id=:blog_item_id",nativeQuery = true)
    Integer findAvgByBlog(@Param("blog_item_id") Integer blog_item_id);
}
