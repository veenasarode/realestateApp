package io.bootify.my_app.repos;


import io.bootify.my_app.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE  u.status = 'activate' ORDER BY u.id DESC")
    List<User> getActivateUserOrderedByCreatedAtDesc();
}
