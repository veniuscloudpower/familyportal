package me.burninghandsapp.familyportal.repositories;

import me.burninghandsapp.familyportal.models.BlogPostItems;
import me.burninghandsapp.familyportal.models.Categories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Locale;

@Repository
public interface BlogPostItemsRepository extends JpaRepository<BlogPostItems,Integer> {

    @Query(value = "SELECT * From blog_post_items where  category_id =:CategoryId order by DATE_CREATED DESC  LIMIT :Limit OFFSET :OffSet",nativeQuery = true)
    List<BlogPostItems> findAllByCategory(@Param("CategoryId") Integer CategoryId,@Param("Limit") Integer Limit,@Param("OffSet") Integer OffSet );

    @Query(value="SELECT * From blog_post_items where Id =:Id",nativeQuery = true)
    BlogPostItems findOneById(@Param("Id") Integer Id);

    @Query(value = "SELECT count(*) From blog_post_items where author_user_id =:Author_User_Id",nativeQuery = true)
    Integer findCountByAuthor(@Param("Author_User_Id") Long Author_User_Id);

    @Query(value = "SELECT * From blog_post_items where author_user_id =:Author_User_Id order  by date_created desc limit 10",nativeQuery = true)
    List<BlogPostItems> myRecentArticles(@Param("Author_User_Id") Long Author_User_Id);

    @Query(value = "SELECT * From blog_post_items where author_user_id <>:Author_User_Id and id not in (Select blog_item_id from blog_post_ratings where rate_by_user_id=:Author_User_Id) order  by date_created desc limit 10",nativeQuery = true)
    List<BlogPostItems> articleToReview(@Param("Author_User_Id") Long Author_User_Id);

    @Query(value = "SELECT * From blog_post_items where author_user_id <>:Author_User_Id and id  in (Select blog_item_id from blog_post_ratings where rate_by_user_id<>:Author_User_Id) order  by date_created desc limit 10",nativeQuery = true)
    List<BlogPostItems> myLatestReviewReceived(@Param("Author_User_Id") Long Author_User_Id);

}
