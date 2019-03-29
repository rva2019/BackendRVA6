package rva;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
public class HelloRestController {
	
	@GetMapping("/")
	public String helloWorld(){
		return "Hello world!";
	}
	
	@GetMapping("zbir")
	public String zbir() {
		long x = Math.round(Math.random() * 10);
		long y = Math.round(Math.random() * 10);
		return x+" + "+y+" = "+(x+y);
	}
}
