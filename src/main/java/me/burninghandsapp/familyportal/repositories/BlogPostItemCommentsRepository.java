package me.burninghandsapp.familyportal.repositories;

import me.burninghandsapp.familyportal.models.BlogPostItemComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogPostItemCommentsRepository  extends JpaRepository<BlogPostItemComments,Integer> {

    @Query(value = "SELECT  count(u) From BlogPostItemComments u where u.author.id =:authorUserId")
    Integer findCountByAuthor(@Param("authorUserId") Integer authorUserId);
}
