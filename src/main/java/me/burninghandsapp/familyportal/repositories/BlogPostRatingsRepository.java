package me.burninghandsapp.familyportal.repositories;

import me.burninghandsapp.familyportal.models.BlogPostRatings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface BlogPostRatingsRepository extends JpaRepository<BlogPostRatings,Integer> {

    @Query(value = "select count(u) from BlogPostRatings u where u.id=:rateByUserId")
    Integer findCountByRateBy(@Param("rateByUserId") Integer rateByUserId);

    @Query(value = "select count(u) from BlogPostRatings u where u.rateBy.id=:rateByUserId and u.blogItem.id=:blogItemId")
    Integer findCountByRateByAndBlog(@Param("blogItemId") Integer blogItemId,@Param("rateByUserId") Integer rateByUserId);

    @Query(value = "select u from BlogPostRatings u where u.blogItem.id=:blogItemId and u.rateBy.id=:rateByUserId")
    BlogPostRatings findByBlog(@Param("blogItemId") Integer blogItemId,@Param("rateByUserId") Integer rateByUserId);

    @Query(value = "select AVG(u.rate) from BlogPostRatings u where u.blogItem.id=:blogItemId")
    Integer findAvgByBlog(@Param("blogItemId") Integer blogItemId);
}
