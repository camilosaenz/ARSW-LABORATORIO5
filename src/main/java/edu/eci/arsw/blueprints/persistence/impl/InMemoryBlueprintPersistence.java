/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.filtro.FRedundancia;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 */

@Service
@Qualifier("InMemoryBlueprintPersistence")
public class InMemoryBlueprintPersistence implements BlueprintsPersistence{

    private final Map<Tuple<String,String>,Blueprint> blueprints=new HashMap<>();

    public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] pts=new Point[]{new Point(140, 140),new Point(115, 115)};
        Blueprint bp=new Blueprint("juan", "PlanoJuan",pts);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        
        Point[] pts2=new Point[]{new Point(1, 1),new Point(4, 4)};
        Blueprint bp2=new Blueprint("juan", "PlanoJuan2",pts2);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp2.getName()), bp2);
        
        Point[] pts3=new Point[]{new Point(1, 1),new Point(4, 4)};
        Blueprint bp3=new Blueprint("alejo", "PlanoAlejo",pts3);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp3.getName()), bp3);
    }    
    
    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(),bp.getName()))){
            throw new BlueprintPersistenceException("The given blueprint already exists: "+bp);
        }
        else{
            blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        }        
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
    	
        Blueprint blueprint = blueprints.get(new Tuple<>(author, bprintname));
        
        if(blueprint == null) {
        	throw new BlueprintNotFoundException("No se encontro!");
        }
        return blueprint;
    }

	@Override
	public Set<Blueprint> getAllBlueprints() {
		Set<Blueprint> blue = new HashSet<Blueprint>();
		for(Blueprint bp : blueprints.values()) {
			blue.add(bp);
		}
		return blue;
	}

	@Override
	public Set<Blueprint> getAllBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
		Set<Blueprint> blue = new HashSet<Blueprint>();
		Collection<Blueprint> col = blueprints.values();
		for(Blueprint bp : col) {
			if(bp.getAuthor().equals(author)) {
				blue.add(bp);
			}
		}
		if(blue.size() == 0) {
			throw new BlueprintNotFoundException("El autor no existe!");
		}
		return blue;
	}
    
    
}
