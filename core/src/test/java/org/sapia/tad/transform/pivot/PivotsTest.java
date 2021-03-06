package org.sapia.tad.transform.pivot;

import org.junit.Before;
import org.junit.Test;
import org.sapia.tad.ColumnSets;
import org.sapia.tad.Dataset;
import org.sapia.tad.Datasets;
import org.sapia.tad.Datatype;
import org.sapia.tad.Vectors;
import org.sapia.tad.util.Data;

public class PivotsTest {
  
  private Dataset multiLevel, singleLevel;
  
  @Before
  public void setUp() {
    multiLevel = Datasets.dataset(
        ColumnSets.columnSet("country", Datatype.STRING, "gdp", Datatype.NUMERIC, "year", Datatype.NUMERIC),
        Data.list(
            Vectors.vector("us", 100, 2000),
            Vectors.vector("us", 101, 2001),
            Vectors.vector("us", 102, 2002),
            Vectors.vector("uk", 200, 2000),
            Vectors.vector("uk", 201, 2001),
            Vectors.vector("uk", 203, 2003),            
            Vectors.vector("fr", 302, 2002)
        )
    );
    
    singleLevel = Datasets.dataset(
        ColumnSets.columnSet("gdp", Datatype.NUMERIC, "year", Datatype.NUMERIC),
        Data.list(
            Vectors.vector(100, 2000),
            Vectors.vector(101, 2001),
            Vectors.vector(102, 2002),
            Vectors.vector(200, 2000),
            Vectors.vector(201, 2001),
            Vectors.vector(203, 2003),            
            Vectors.vector(302, 2002)
        )
    );
  }

  @Test
  public void testPivot() {
    System.out.println(Pivots.pivot(multiLevel, "year", "country", "gdp"));
  }

}
