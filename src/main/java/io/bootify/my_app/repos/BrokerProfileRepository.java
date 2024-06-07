package io.bootify.my_app.repos;


import io.bootify.my_app.domain.BrokerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrokerProfileRepository extends JpaRepository<BrokerProfile, Integer> {
}
