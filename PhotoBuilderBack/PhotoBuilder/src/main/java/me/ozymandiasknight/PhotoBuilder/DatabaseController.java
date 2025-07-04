package me.ozymandiasknight.PhotoBuilder;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DatabaseController extends JpaRepository<User, Long> {
	boolean existsByMail(String username);
	Optional<User> findByMail(String mail);
	Optional<User> findByName(String name);
}
