package volkfm.KanBanan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import volkfm.KanBanan.models.authority.Authority;
import volkfm.KanBanan.models.authority.AuthorityRepository;
import volkfm.KanBanan.models.authority.Role;
import volkfm.KanBanan.models.board.Board;
import volkfm.KanBanan.models.card.Card;
import volkfm.KanBanan.models.column.Column;
import volkfm.KanBanan.models.user.User;
import volkfm.KanBanan.models.board.BoardRepository;
import volkfm.KanBanan.models.card.CardRepository;
import volkfm.KanBanan.models.column.ColumnRepository;
import volkfm.KanBanan.models.user.UserFactory;
import volkfm.KanBanan.models.user.UserRepository;

@SpringBootApplication
public class KanBananApplication {
	Logger logger = LoggerFactory.getLogger(KanBananApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(KanBananApplication.class, args);
	}

	@Bean
	public CommandLineRunner modelTest(
			UserRepository userRepo, AuthorityRepository authRepo,
			BoardRepository boardRepo, ColumnRepository colRepo,
			CardRepository cardRepo, PasswordEncoder passwordEncoder,
			UserFactory userFactory) {

		return (args) -> {
			User volkfm = userFactory.createUser("volkfm", "volkfm98@gmail.com", passwordEncoder.encode("sassyPassword"), true);
			Authority auth = new Authority(volkfm.getUsername(), Role.USER);

			Board hasky = new Board("Hasky");
			Column done = new Column("Done");
			Column doing = new Column("Doing");
			Card sassyTask = new Card("Sassy task");

			volkfm.addBoard(hasky);

			hasky.addColumn(done);
			hasky.addColumn(doing);

			done.addCard(sassyTask);

			userRepo.save(volkfm);
			authRepo.save(auth);

			boardRepo.save(hasky);

			colRepo.save(done);
			colRepo.save(doing);

			cardRepo.save(sassyTask);
		};
	}
}
