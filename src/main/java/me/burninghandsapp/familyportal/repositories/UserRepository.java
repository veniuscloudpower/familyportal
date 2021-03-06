package me.burninghandsapp.familyportal.repositories;

import me.burninghandsapp.familyportal.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.userName = :userName")
    public User getUserByUsername(@Param("userName") String userName);
}
