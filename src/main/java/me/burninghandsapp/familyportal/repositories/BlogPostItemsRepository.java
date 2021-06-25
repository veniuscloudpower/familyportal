package me.burninghandsapp.familyportal.repositories;

import me.burninghandsapp.familyportal.models.BlogPostItems;;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Locale;

@Repository
public interface BlogPostItemsRepository extends JpaRepository<BlogPostItems,Integer> {

    @Query(value = "SELECT * From blog_post_items where  category_id =:categoryId order by DATE_CREATED DESC  LIMIT :limit OFFSET :offSet",nativeQuery = true)
    List<BlogPostItems> findAllByCategory(@Param("categoryId") Integer categoryId,@Param("limit") Integer limit,@Param("offSet") Integer offSet );

    @Query(value="SELECT * From blog_post_items where Id =:id",nativeQuery = true)
    BlogPostItems findOneById(@Param("id") Integer id);

    @Query(value = "SELECT count(*) From blog_post_items where author_user_id =:authorUserId",nativeQuery = true)
    Integer findCountByAuthor(@Param("authorUserId") Long authorUserId);

    @Query(value = "SELECT * From blog_post_items where author_user_id =:authorUserId order  by date_created desc limit 10",nativeQuery = true)
    List<BlogPostItems> myRecentArticles(@Param("authorUserId") Long authorUserId);

    @Query(value = "SELECT * From blog_post_items where author_user_id <>:authorUserId and id not in (Select blog_item_id from blog_post_ratings where rate_by_user_id=:authorUserId) order  by date_created desc limit 10",nativeQuery = true)
    List<BlogPostItems> articleToReview(@Param("authorUserId") Long authorUserId);

    @Query(value = "SELECT * From blog_post_items where author_user_id <>:authorUserId and id  in (Select blog_item_id from blog_post_ratings where rate_by_user_id<>:authorUserId) order  by date_created desc limit 10",nativeQuery = true)
    List<BlogPostItems> myLatestReviewReceived(@Param("authorUserId") Long authorUserId);

}
