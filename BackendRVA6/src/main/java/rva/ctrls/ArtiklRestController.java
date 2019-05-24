package rva.ctrls;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import rva.jpa.Artikl;
import rva.reps.ArtiklRepository;

@CrossOrigin
@Api(tags={"Artikl CRUD operacije"})
@RestController
public class ArtiklRestController {

	@Autowired
	private ArtiklRepository artiklRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@ApiOperation(value="VraÄ‡a kolekciju artikala iz baze podataka")
	@GetMapping("/artikl")
	public Collection<Artikl> getArtikli() {
		return artiklRepository.findAll();
	}
	
	@ApiOperation(value = "VraÄ‡a artikl iz baze podataka Ä�iji je id vrednost prosleÄ‘ena kao path varijabla")
	@GetMapping("/artikl/{id}")
	public Artikl getArtikl(@PathVariable Integer id) {
		return artiklRepository.getOne(id);
	}
	
	@ApiOperation(value = "VraÄ‡a kolekciju svih artikala iz baze podataka koji u nazivu sadrÅ¾e string prosleÄ‘en kao path varijabla")
	@GetMapping("/artiklNaziv/{naziv}")
	public Collection<Artikl> getByNaziv(@PathVariable String naziv) {
		return artiklRepository.findByNazivContainingIgnoreCase(naziv);
	}

	@ApiOperation(value = "BriÅ¡e artikl iz baze podataka Ä�iji je id vrednost prosleÄ‘ena kao path varijabla")
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
	@ApiOperation(value = "Upisuje artikl u bazu podataka")
	@PostMapping("/artikl")
	public ResponseEntity<HttpStatus> insertArtikl(@RequestBody Artikl artikl) {
		artiklRepository.save(artikl);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

	// update
	@PutMapping("/artikl")
	@ApiOperation(value = "Modifikuje postojeÄ‡i artikl u bazi podataka")
	public ResponseEntity<HttpStatus> updateArtikl(@RequestBody Artikl artikl) {
		if (artiklRepository.existsById(artikl.getId())) {
			artiklRepository.save(artikl);
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		}
		return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
	}
}
