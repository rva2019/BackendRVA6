package rva.ctrls;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import rva.jpa.Dobavljac;
import rva.reps.DobavljacRepository;

@RestController
public class DobavljacRestController {

	@Autowired
	private DobavljacRepository dobavljacRepository;

	@GetMapping("dobavljac")
	public Collection<Dobavljac> getDobavljaci() {
		return dobavljacRepository.findAll();
	}

	@GetMapping("dobavljacId/{id}")
	public Dobavljac getDobavljac(@PathVariable("id") Integer id) {
		return dobavljacRepository.getOne(id);
	}

	@GetMapping("dobavljacNaziv/{naziv}")
	public Collection<Dobavljac> getDobavljacByNaziv(@PathVariable("naziv") String naziv) {
		return dobavljacRepository.findByNazivContainingIgnoreCase(naziv);
	}

	@DeleteMapping("dobavljac/{id}")
	public ResponseEntity<Dobavljac> deleteDobavljac(@PathVariable("id") Integer id) {
		if (!dobavljacRepository.existsById(id))
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		dobavljacRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// insert
	@PostMapping("dobavljac")
	public ResponseEntity<Dobavljac> insertDobavljac(@RequestBody Dobavljac dobavljac) {
		dobavljacRepository.save(dobavljac);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// update
	@PutMapping("dobavljac")
	public ResponseEntity<Dobavljac> updateDobavljac(@RequestBody Dobavljac dobavljac) {
		if (!dobavljacRepository.existsById(dobavljac.getId()))
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		dobavljacRepository.save(dobavljac);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}