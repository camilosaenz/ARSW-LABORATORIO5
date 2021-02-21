/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.services.BlueprintsServices;

/**
 *
 * @author hcadavid
 */

@RestController
@RequestMapping(value = "/blueprints")
public class BlueprintAPIController {
    
	@Autowired
	BlueprintsServices blueprintsServices = null;
	
	// Controlador 1 (get - blueprint) - Este controlador es para retornar todos los blueprints

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> manejadorGetRecursoBlueprints(){
	    try {
	    	Set<Blueprint> data = blueprintsServices.getAllBlueprints();
	        //obtener datos que se enviarán a través del API
	        return new ResponseEntity<>(data,HttpStatus.ACCEPTED);
	    } catch (Exception ex) {
	        Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
	        return new ResponseEntity<>("Error al ingresar a la pagina: ",HttpStatus.NOT_FOUND);
	    }  
    
	}
	
	// Controlador  (get - blueprint(author)) - Este controlador es para retornar todos los blueprints de un author en especifico
	
	@RequestMapping(method = RequestMethod.GET, path = "/{author}")
	public ResponseEntity<?> manejadorGetRecursoBlueprintsbyAuthor(@PathVariable("author") String author){
	    try {
	    	Set<Blueprint> data = blueprintsServices.getBlueprintsByAuthor(author);
	        //obtener datos que se enviarán a través del API
	        return new ResponseEntity<>(data,HttpStatus.ACCEPTED);
	    } catch (Exception ex) {
	        Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
	        return new ResponseEntity<>("Error al ingresar a la pagina: ",HttpStatus.NOT_FOUND);
	    }  
    
	}
	
	// Controlador 3 (get - blueprint(author, bprint)) - Este controlador es para retornar el palno indicado del autor indicado
	
	@RequestMapping(method = RequestMethod.GET, path = "/{author}/{bprint}")
	public ResponseEntity<?> manejadorGetRecursoBlueprintsByAuthorBybprint(@PathVariable("author") String author, @PathVariable("bprint") String bprint){
	    try {
	    	Blueprint data = blueprintsServices.getBlueprint(author, bprint);
	        //obtener datos que se enviarán a través del API
	        return new ResponseEntity<>(data,HttpStatus.ACCEPTED);
	    } catch (Exception ex) {
	        Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
	        return new ResponseEntity<>("Error al ingresar a la pagina: ",HttpStatus.NOT_FOUND);
	    }  
    
	}
	
	// Controlador 4 (Post)
	
	@RequestMapping(method = RequestMethod.POST)	
	public ResponseEntity<?> addBlueprint(@RequestBody Blueprint blue){
	    try {
	    	blueprintsServices.addNewBlueprint(blue);
	        //registrar dato
	        return new ResponseEntity<>(HttpStatus.CREATED);
	    } catch (Exception ex) {
	        Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
	        return new ResponseEntity<>("Error al ingresar a la pagina:",HttpStatus.FORBIDDEN);            
	    }        

	}
	
	// Controlador 5 (Put)
	
    @RequestMapping(method = RequestMethod.PUT, path = "{author}/{bprint}")
    public ResponseEntity<Blueprint> PutBluePrint(@RequestBody Blueprint blue, @PathVariable("author") String author, @PathVariable("bprint") String bprint){
        try {
        	blueprintsServices.setBlueprint(blue, author, bprint);
        	return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
       
    }
	
}

