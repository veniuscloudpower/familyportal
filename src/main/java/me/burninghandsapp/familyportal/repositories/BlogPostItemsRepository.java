package me.burninghandsapp.familyportal.repositories;

import me.burninghandsapp.familyportal.models.BlogPostItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogPostItemsRepository extends JpaRepository<BlogPostItems,Integer> {

}
