package edu.eci.arsw.blueprints.controllers;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/blueprints")


public class BlueprintAPIController {

    @Autowired
    BlueprintsServices bps;

    @RequestMapping(method = RequestMethod.GET)

    public ResponseEntity<Collection<Blueprint>> getBlueprints() {


        try {
            //obtener datos que se enviarán a través del API
            return new ResponseEntity<Collection<Blueprint>>(bps.getAllBlueprints(), HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintNotFoundException.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<Collection<Blueprint>>(new HashSet<>(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "{author}")

    public ResponseEntity<Collection<Blueprint>> getAuthors(@PathVariable String author ) {

        try {
            //obtener datos que se enviarán a través del API
            return new ResponseEntity<Collection<Blueprint>>(bps.getBlueprintsByAuthor(author), HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintNotFoundException.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<Collection<Blueprint>>(new HashSet<>(),HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "{author}/{name}")

    public ResponseEntity<Blueprint> getAuthors(@PathVariable String author, @PathVariable String name ) {

        try {
            //obtener datos que se enviarán a través del API
            return new ResponseEntity<Blueprint>(bps.getBlueprint(author, name), HttpStatus.ACCEPTED);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintNotFoundException.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<Blueprint>(new Blueprint() ,HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = "/createblueprints")
    public ResponseEntity<?> postNewBliprint(@RequestBody Blueprint bp){
        try {
            bps.addNewBlueprint(bp);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (BlueprintPersistenceException ex) {
            Logger.getLogger(BlueprintPersistenceException.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error adding new BluePrint",HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = "/updateblueprints")
    public ResponseEntity<?> putBlueprint(@RequestBody Blueprint bp, @PathVariable("author") String author, @PathVariable("name") String name ){
        try {
            bps.updateBlueprint(bp, author, name);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (BlueprintPersistenceException ex) {
            Logger.getLogger(BlueprintPersistenceException.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error updating BluePrint",HttpStatus.FORBIDDEN);
        }
    }



}
