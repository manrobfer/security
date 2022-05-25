package br.com.jwt.repositories;

import br.com.jwt.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByFirstName(String name);
    User findUserByEmail(String email);
}
