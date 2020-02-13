package edu.eci.arsw.blueprints.persistence;

import edu.eci.arsw.blueprints.model.Blueprint;

import java.util.Set;

public interface BlueprintFilter {

    public Blueprint filterBlueprint(Blueprint bp);

    public Set<Blueprint> filterBlueprintSet(Set<Blueprint> bp);
}