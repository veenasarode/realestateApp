package io.bootify.my_app.repos;


import io.bootify.my_app.domain.Agreement;
import io.bootify.my_app.domain.BrokerProfile;
import io.bootify.my_app.domain.Lease;
import io.bootify.my_app.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrokerProfileRepository extends JpaRepository<BrokerProfile, Integer> {

    List<BrokerProfile> findByUser(User user);
}
