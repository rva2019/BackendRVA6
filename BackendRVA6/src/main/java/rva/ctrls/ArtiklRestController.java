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
import org.springframework.web.bind.annotation.RestController;

import rva.jpa.Artikl;
import rva.reps.ArtiklRepository;

@RestController
public class ArtiklRestController {
	
	@Autowired
	private ArtiklRepository artiklRepository;
	
	@GetMapping("/artikl")
	public Collection<Artikl> getArtikli(){
		return artiklRepository.findAll();
	}

	@GetMapping("/artikl/{id}")
	public Artikl getArtikl(@PathVariable Integer id){
		return artiklRepository.getOne(id);
	}
	
	@GetMapping("/artiklNaziv/{naziv}")
	public Collection<Artikl> getByNaziv(@PathVariable String naziv){
		return artiklRepository.findByNazivContainingIgnoreCase(naziv);
	}
	
	@DeleteMapping("/artikl/{id}")
	public ResponseEntity<HttpStatus> deleteArtikl(@PathVariable Integer id){
		if(artiklRepository.existsById(id)){
			artiklRepository.deleteById(id);
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		}
		return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
	}
	
	// insert
	@PostMapping("/artikl/{artikl}")
	public ResponseEntity<HttpStatus> insertArtikl(@PathVariable Artikl artikl){
		artiklRepository.save(artikl);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	// edit
	@PutMapping("/artikl/{artikl}")
	public ResponseEntity<HttpStatus> updateArtikl(@PathVariable Artikl artikl){
		if(artiklRepository.existsById(artikl.getId())){
			artiklRepository.save(artikl);
			return new ResponseEntity<HttpStatus>(HttpStatus.OK);
		}
		return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
	}
}
