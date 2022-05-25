package com.ca.formation.formationdemo1.controllers.api;

import com.ca.formation.formationdemo1.exception.ResourceNotFoundException;
import com.ca.formation.formationdemo1.models.Personne;
import com.ca.formation.formationdemo1.repositories.PersonneRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Api(value = "Personne Rest API")
@RestController
@RequestMapping(value = "/api/v2/personnes")
public class ApiPersonneController {

    private final PersonneRepository personneRepository;

    public ApiPersonneController(PersonneRepository personneRepository) {
        this.personneRepository = personneRepository;
    }

    /**
     * - GET /api/v1/personnes
     * - POST /api/v1/personnnes
     * - PATCH /api/v1/personnnes/{id}
     * - PUT /api/v1/personnnes/{id}
     * - GET /api/v1/personnes/{id}
     * - DELETE /api/v1/personnes/{id}
     * - GET /api/v1/personnes/search?nom="Jean"
     */

    @ApiOperation(value="API de bonjour", response = String.class)
    @GetMapping("/hello")
    public String hello(){
        return "Bonjour tout le monde";
    }

    @GetMapping("/bye")
    public  String byebye(){
        return "Bye bye";
    }

    /**
     * /api/v1/personnes
     * @return List Personne
     */
    @GetMapping
    public ResponseEntity<List<Personne>> getToutPersonne(){
        List<Personne> personnes = (List) personneRepository.findAll();
        return ResponseEntity.ok().body(personnes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Personne> getPersonne(@PathVariable(value="id") Long id) throws ResourceNotFoundException {
        Personne personne = personneRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Personne pas trouvé") );
       return ResponseEntity.ok().body(personne);
    }

    @PostMapping
    public ResponseEntity<Personne> addPersonne(@RequestBody Personne personne){
        Personne personneResponse = personneRepository.save(personne);
        return ResponseEntity.ok().body(personneResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Personne> updatePersonne(@PathVariable(value="id") Long id, @RequestBody Personne personne ) throws Exception {

        Optional<Personne> optionalPersonne = personneRepository.findById(id);

        if(optionalPersonne.isEmpty()){
            throw new Exception("Personne pas trouvé");
        }

        Personne personneResponse = personneRepository.save(personne);
        return ResponseEntity.ok().body(personneResponse);
    }

    @DeleteMapping("/{id}")
    public String deletePersonne(@PathVariable(value="id") Long id){
        personneRepository.deleteById(id);
        return "OK";
    }

    @GetMapping("/search")
    public ResponseEntity<List<Personne>> getPersonneParNom(@RequestParam(name = "nom") String nom){
        List<Personne> personnes = personneRepository.findByNom(nom);
        return ResponseEntity.ok().body(personnes);
    }

}
