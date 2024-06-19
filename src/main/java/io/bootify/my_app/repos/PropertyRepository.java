package io.bootify.my_app.repos;

import io.bootify.my_app.domain.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Integer> {

    List<Property> findByUserUserUserId(Integer userId);

    List<Property> findByProprtyWonerProprtyWonerId(Integer propertyOwnerId);
}
