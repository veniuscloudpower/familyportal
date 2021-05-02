package me.burninghandsapp.familyportal.repositories;

import me.burninghandsapp.familyportal.models.BlogPostItemComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogPostItemCommentsRepository  extends JpaRepository<BlogPostItemComments,Integer> {
}
