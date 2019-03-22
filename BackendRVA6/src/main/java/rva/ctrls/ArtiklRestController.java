package rva.ctrls;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import rva.jpa.Artikl;
import rva.reps.ArtiklRepository;

@RestController
public class ArtiklRestController {

	@Autowired
	private ArtiklRepository artiklRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@GetMapping("/artikl")
	public Collection<Artikl> getArtikli() {
		return artiklRepository.findAll();
	}

	@GetMapping("/artikl/{id}")
	public Artikl getArtikl(@PathVariable Integer id) {
		return artiklRepository.getOne(id);
	}

	@GetMapping("/artiklNaziv/{naziv}")
	public Collection<Artikl> getByNaziv(@PathVariable String naziv) {
		return artiklRepository.findByNazivContainingIgnoreCase(naziv);
	}

	
	@DeleteMapping("/artikl/{id}")
	public ResponseEntity<HttpStatus> deleteArtikl(@PathVariable Integer id) {
		if (artiklRepository.existsById(id)) {
			artiklRepository.deleteById(id);
			if(id == -100)
				jdbcTemplate.execute("INSERT INTO \"artikl\"(\"id\", \"naziv\", \"proizvodjac\")"
						+ "VALUES(-100, 'Naziv TEST', 'Proizvodjac TEST')");

			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		}
		return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
	}

	// insert
	@PostMapping("/artikl")
	public ResponseEntity<HttpStatus> insertArtikl(@RequestBody Artikl artikl) {
		artiklRepository.save(artikl);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

	// update
	@PutMapping("/artikl")
	public ResponseEntity<HttpStatus> updateArtikl(@RequestBody Artikl artikl) {
		if (artiklRepository.existsById(artikl.getId())) {
			artiklRepository.save(artikl);
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		}
		return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
	}
}
