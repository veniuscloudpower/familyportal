package me.burninghandsapp.familyportal.repositories;

import me.burninghandsapp.familyportal.models.BlogPostItems;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;


@Repository
public interface BlogPostItemsRepository extends JpaRepository<BlogPostItems,Integer> {

    @Query(value = "SELECT u From BlogPostItems u where u.category.id =:categoryId order by u.dateCreated DESC")
    List<BlogPostItems> findAllByCategory(@Param("categoryId") Integer categoryId, Pageable pageable );

    @Query(value="SELECT u From BlogPostItems u where u.id =:id")
    BlogPostItems findOneById(@Param("id") Integer id);

    @Query(value = "SELECT count(u) From  BlogPostItems u where u.author.id =:authorUserId")
    Integer findCountByAuthor(@Param("authorUserId") Integer authorUserId);

    @Query(value = "SELECT u From BlogPostItems u where u.author.id =:authorUserId order  by u.dateCreated desc")
    List<BlogPostItems> findTop10RecentArticles(@Param("authorUserId") Integer authorUserId);

    @Query(value = "SELECT u From BlogPostItems u where u.author.id  <>:authorUserId and u.id not in (Select r.blogItem.id from BlogPostRatings r where r.rateBy.id=:authorUserId) order  by u.dateCreated desc")
    List<BlogPostItems> findTop10articleToReview(@Param("authorUserId") Integer authorUserId);

    @Query(value = "SELECT u From BlogPostItems u where u.author.id <>:authorUserId and u.id  in (Select r.blogItem.id from BlogPostRatings r where r.rateBy.id<>:authorUserId) order by  u.dateCreated")
    List<BlogPostItems> findTop10ReviewReceived(@Param("authorUserId") Integer authorUserId);

}
