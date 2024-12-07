package fer.hr.Projekt.repository;

import fer.hr.Projekt.DAO.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String > {
}
