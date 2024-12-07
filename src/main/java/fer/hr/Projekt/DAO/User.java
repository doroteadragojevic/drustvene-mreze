package fer.hr.Projekt.DAO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Collections;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    @Id
    String email;
    String name;
    Set<String> favoriteMovies = Collections.emptySet();
}
