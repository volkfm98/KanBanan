package volkfm.KanBanan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import volkfm.KanBanan.db.Models.Board;
import volkfm.KanBanan.db.Models.Card;
import volkfm.KanBanan.db.Models.Column;
import volkfm.KanBanan.db.Models.User;
import volkfm.KanBanan.db.Repositories.BoardRepository;
import volkfm.KanBanan.db.Repositories.CardRepository;
import volkfm.KanBanan.db.Repositories.ColumnRepository;
import volkfm.KanBanan.db.Repositories.UserRepository;

@SpringBootApplication
public class KanBananApplication {
	Logger logger = LoggerFactory.getLogger(KanBananApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(KanBananApplication.class, args);
	}

	@Bean
	public CommandLineRunner modelTest(
			UserRepository userRepo, BoardRepository boardRepo,
			ColumnRepository colRepo, CardRepository cardRepo) {
		return (args) -> {
			User volkfm = new User("volkfm", "volkfm98@gmail.com", "sassyPassword");
			Board hasky = new Board("Hasky");
			Column done = new Column("Done");
			Card sassyTask = new Card("Sassy task");

			volkfm.addBoard(hasky);
			hasky.addColumn(done);
			done.addCard(sassyTask);

			logger.info(volkfm.getBoards().toString());

			userRepo.save(volkfm);
			boardRepo.save(hasky);
			colRepo.save(done);
			cardRepo.save(sassyTask);
		};
	}
}
