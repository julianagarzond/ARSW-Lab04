package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintFilter;


import java.util.ArrayList;
import java.util.Set;


public class SubsamplingFilter implements BlueprintFilter {

    @Override
    public Blueprint filterBlueprint(Blueprint bp) {
        ArrayList<Integer> remove = new ArrayList<>();
        for (int i = 0; i < bp.getPoints().size(); i++){
            if (i % 2 != 0){
                remove.add(i);
            }
        }
        for(int dir: remove){
            bp.getPoints().remove(dir);
        }
        return bp;
    }

    @Override
    public Set<Blueprint> filterBlueprintSet(Set<Blueprint> bp) {
        for (Blueprint blueP: bp){
            blueP = filterBlueprint(blueP);
        }
        return bp;
    }
}
