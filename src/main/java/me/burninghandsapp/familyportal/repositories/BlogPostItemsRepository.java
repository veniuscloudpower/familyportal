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

    @Query(value = "SELECT  * From blog_post_items where  category_id =:CategoryId order by DATE_CREATED DESC  LIMIT :Limit OFFSET :OffSet",nativeQuery = true)
    List<BlogPostItems> findAllByCategory(@Param("CategoryId") Integer CategoryId,@Param("Limit") Integer Limit,@Param("OffSet") Integer OffSet );

    @Query(value="SELECT  * From blog_post_items where Id =:Id",nativeQuery = true)
    BlogPostItems findOneById(@Param("Id") Integer Id);
}
