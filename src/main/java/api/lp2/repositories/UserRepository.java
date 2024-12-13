package api.lp2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import api.lp2.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    

}
