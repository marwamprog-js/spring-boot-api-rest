package br.com.cursospringboot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.cursospringboot.model.Usuario;
import br.com.cursospringboot.repository.UsuarioRepository;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {
	
	
	@Autowired //IC - CD - CDI = injeção de dependencia
	private UsuarioRepository usuarioRepository;
	
    /**
     *
     * @param name the name to greet
     * @return greeting text
     */
    @RequestMapping(value = "/mostrarnome/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String greetingText(@PathVariable String name) {
        return "Curso Spring Boot API: " + name + "!";
    }
    
    
    @RequestMapping(value = "/olamundo/{nome}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String retornaFrase(@PathVariable String nome) {
    	
    	Usuario usuario = new Usuario();
    	usuario.setNome(nome);
    	usuarioRepository.save(usuario);
    	
    	return "Olá mundo " + nome;
    }
    
    
    @GetMapping(value = "/listartodos")
    @ResponseBody //Retorna os dados para o corpo da resposta (JSON)
    public ResponseEntity<List<Usuario>> listaUsuario(){
    	
    	List<Usuario> usuarios = usuarioRepository.findAll();
    	
    	return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
    	
    }
    
    
    
    @PostMapping(value = "salvar")
    @ResponseBody //Response - descrição da resposta
    public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario){ //Request body recebe os dados via POST
    	
    	Usuario user = usuarioRepository.save(usuario);
    	
    	return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
    }
    
    
    @DeleteMapping(value = "delete")
    @ResponseBody //Response - descrição da resposta
    public ResponseEntity<String> delete(@RequestParam Long id){ //Request body recebe os dados via POST
    	
    	usuarioRepository.deleteById(id);
    	
    	return new ResponseEntity<String>("Usuario deletado com sucesso", HttpStatus.OK);
    }
    
    
    @GetMapping(value = "buscaruserid")
    @ResponseBody //Response - descrição da resposta
    public ResponseEntity<Usuario> buscarUserId(@RequestParam(name = "id") Long id){ //Request body recebe os dados via POST
    	
    	Usuario usuario = usuarioRepository.findById(id).get();
    	
    	return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    }  
    
    
    @PutMapping(value = "atualizar")
    @ResponseBody //Response - descrição da resposta
    public ResponseEntity<?> atualizar(@RequestBody Usuario usuario){ //Request body recebe os dados via POST
    	
    	if(usuario.getId() == null || usuario.getId() == 0) {
    		return new ResponseEntity<String>("Id não foi informado para atualização", HttpStatus.OK);
    	}
    	
    	Usuario user = usuarioRepository.saveAndFlush(usuario);
    	
    	return new ResponseEntity<Usuario>(user, HttpStatus.OK);
    }
    
    
    
    @GetMapping(value = "buscarpornome")
    @ResponseBody //Response - descrição da resposta
    public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam(name = "nome") String nome){ //Request body recebe os dados via POST
    	    	    	
    	List<Usuario> usuarios = usuarioRepository.buscarPorNome(nome.trim().toUpperCase()); //Trim -> retira espaço
    	
    	return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
    }  
    
    
    
}
