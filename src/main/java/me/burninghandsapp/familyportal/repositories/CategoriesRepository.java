package me.burninghandsapp.familyportal.repositories;

import me.burninghandsapp.familyportal.models.Categories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories,Integer> {

}
