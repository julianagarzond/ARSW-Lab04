package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintFilter;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RedundancyFilter implements BlueprintFilter {

    @Override
    public Blueprint filterBlueprint(Blueprint bp) {
        List<Point> points = bp.getPoints();
        ArrayList<Integer> remove = new ArrayList<>();
        for(int i = 0; i < points.size() - 1; i++){
            if (points.get(i).equals(points.get(i + 1))){
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
        for(Blueprint blueP: bp){
            blueP = filterBlueprint(blueP);
        }
        return bp;
    }
}
