package Codify.dashboard.repository;

import Codify.dashboard.domain.Users;
import Codify.dashboard.dto.dashboard.response.data.UserDataDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<Users, UUID> {

    UserDataDto findByUserUuid(UUID userUuid);
}
