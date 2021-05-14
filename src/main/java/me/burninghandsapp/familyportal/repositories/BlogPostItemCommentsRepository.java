package me.burninghandsapp.familyportal.repositories;

import me.burninghandsapp.familyportal.models.BlogPostItemComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogPostItemCommentsRepository  extends JpaRepository<BlogPostItemComments,Integer> {

    @Query(value = "SELECT  count(*) From blog_post_item_comments where author_user_id =:Author_User_Id",nativeQuery = true)
    Integer findCountByAuthor(@Param("Author_User_Id") Long Author_User_Id);
}
