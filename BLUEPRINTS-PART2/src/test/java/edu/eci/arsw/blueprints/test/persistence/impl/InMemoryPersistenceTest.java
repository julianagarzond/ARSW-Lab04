/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;
import edu.eci.arsw.blueprints.persistence.impl.RedundancyFilter;
import edu.eci.arsw.blueprints.persistence.impl.SubsamplingFilter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hcadavid
 */
public class InMemoryPersistenceTest {
    
    @Test
    public void saveNewAndLoadTest() throws BlueprintPersistenceException, BlueprintNotFoundException{
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();
        ibpp.setFilter(new RedundancyFilter());
        Point[] pts0 = new Point[]{new Point(40, 40),new Point(15, 15)};
        Blueprint bp0 = new Blueprint("mack", "mypaint",pts0);
        
        ibpp.saveBlueprint(bp0);
        
        Point[] pts = new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp = new Blueprint("john", "thepaint",pts);
        
        ibpp.saveBlueprint(bp);
        
        assertNotNull("Loading a previously stored blueprint returned null.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()));
        
        assertEquals("Loading a previously stored blueprint returned a different blueprint.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()), bp);
        
    }


    @Test
    public void saveExistingBpTest() {
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();
        ibpp.setFilter(new RedundancyFilter());
        Point[] pts = new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp = new Blueprint("john", "thepaint",pts);
        
        try {
            ibpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }
        
        Point[] pts2 = new Point[]{new Point(10, 10),new Point(20, 20)};
        Blueprint bp2 = new Blueprint("john", "thepaint",pts2);

        try{
            ibpp.saveBlueprint(bp2);
            fail("An exception was expected after saving a second blueprint with the same name and autor");
        }
        catch (BlueprintPersistenceException ex){

        }
    }

    @Test
    public void getBlueprintTest(){
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();
        ibpp.setFilter(new RedundancyFilter());
        Point[] pts = new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp = new Blueprint("john", "theRipper",pts);
        try {
            ibpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException e) {
            fail("InMemoryBluePrintsPersistence save error.");
        }
        Blueprint resultBp=null;
        try {
            resultBp = ibpp.getBlueprint("john","theRipper");
        } catch (BlueprintNotFoundException e) {
            fail("InMemoryBluePrintsPersistence get error.");
        }
        assertEquals(resultBp,bp);
    }

    @Test
    public void getBlueprintByAuthorTest(){
        InMemoryBlueprintPersistence ibpp = new InMemoryBlueprintPersistence();
        ibpp.setFilter(new RedundancyFilter());
        Point[] pts1 = new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp1 = new Blueprint("john", "thepaint",pts1);
        Point[] pts2 = new Point[]{new Point(0, 45),new Point(45, 10)};
        Blueprint bp2 = new Blueprint("john", "theRipper",pts2);
        Point[] pts3 = new Point[]{new Point(23, 43),new Point(56, 10)};
        Blueprint bp3 = new Blueprint("juan", "saltaMuros",pts3);
        Set<Blueprint> authorBlueprints = new HashSet<>();
        authorBlueprints.add(bp1);
        authorBlueprints.add(bp2);

        try {
            ibpp.saveBlueprint(bp1);
            ibpp.saveBlueprint(bp2);
            ibpp.saveBlueprint(bp3);
        } catch (BlueprintPersistenceException ex) {
            fail("InMemoryBluePrintsPersistence save error.");
        }
        assertEquals(ibpp.getBlueprintByAuthor("john"),authorBlueprints);
    }

    @Test
    public void redundancyFilterTest() throws BlueprintPersistenceException, BlueprintNotFoundException {

        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
        RedundancyFilter fl=new RedundancyFilter();
        ibpp.setFilter(fl);
        Point[] pts1=new Point[]{new Point(0, 0),new Point(10, 10),new Point(10, 10),new Point(10, 10),new Point(1, 10)};
        Point[] pts2=new Point[]{new Point(0, 0),new Point(10, 10),new Point(1, 10)};
        Blueprint bp1=new Blueprint("john", "thepaint",pts1);
        Blueprint bp2=new Blueprint("john", "thepaint",pts2);
        List<Point> pts3=fl.filterBlueprint(bp1).getPoints();


        for(int i=0;i<pts3.size();i++){
            assertTrue(pts3.get(i).getX()== bp2.getPoints().get(i).getX());
            assertTrue(pts3.get(i).getY()== bp2.getPoints().get(i).getY());
        }

    }

    @Test
    public void subsamplingFilterTest() throws BlueprintPersistenceException, BlueprintNotFoundException {

        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
        SubsamplingFilter fl=new SubsamplingFilter();
        ibpp.setFilter(fl);
        Point[] pts1=new Point[]{new Point(0, 0),new Point(10, 10),new Point(11, 10),new Point(10, 10),new Point(1, 10)};
        Point[] pts2=new Point[]{new Point(0, 0),new Point(11, 10),new Point(1, 10)};
        Blueprint bp1=new Blueprint("john", "thepaint",pts1);
        Blueprint bp2=new Blueprint("john", "thepaint",pts2);
        List<Point> pts3=fl.filterBlueprint(bp1).getPoints();


        for(int i=0;i<pts3.size();i++){
            assertTrue(pts3.get(i).getX()== bp2.getPoints().get(i).getX());
            assertTrue(pts3.get(i).getY()== bp2.getPoints().get(i).getY());
        }

    }
}
