package org.sapia.tad.transform.join;

import groovy.sql.DataSet;
import org.sapia.tad.*;
import org.sapia.tad.impl.DefaultColumnSet;
import org.sapia.tad.impl.DefaultDataset;
import org.sapia.tad.transform.join.VectorTable.VectorType;
import org.sapia.tad.value.Value;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides methods for joining datasets.
 * 
 * @author yduchesne
 *
 */
public class Joins {
  
  private Joins() {
  }

  /**
   * @param left the "left" {@link Dataset}.
   * @param right the "right" {@link Dataset}.
   * @param join the {@link Join} describing which columns in the left dataset
   * map to which ones in the right dataset.
   * @return a new {@link DataSet}.
   */
  public static Dataset join(Dataset left, Dataset right, Join join) {
    return join(left, right.index(join.getRight().getColumnNames()), join);
  }
  /**
   * @param left the "left" {@link Dataset}.
   * @param right the "right" {@link Dataset}.
   * @param join the {@link Join} describing which columns in the left dataset
   * map to which ones in the right dataset.
   * @return a new {@link DataSet}.
   */  
  public static Dataset join(Dataset left, IndexedDataset right, Join join) {
    
    List<Column> joinCols = new ArrayList<>(left.getColumnSet().size() + right.getColumnSet().size());

    List<Integer>    colIndices  = new ArrayList<>();
    List<VectorType> vectorTypes = new ArrayList<>();
    
    int index = 0;

    for (Column col : left.getColumnSet().excludes(join.getLeft().getColumnNames())) {
      joinCols.add(new JoinColumn(index++, col));
      colIndices.add(col.getIndex());
      vectorTypes.add(VectorType.LEFT);
    }
    for (Column col : join.getRight()) {
      joinCols.add(new JoinColumn(index++, col));
      colIndices.add(col.getIndex());
      vectorTypes.add(VectorType.RIGHT);
    }
    for (Column col : right.getColumnSet().excludes(join.getRight().getColumnNames())) {
      joinCols.add(new JoinColumn(index++, col));
      colIndices.add(col.getIndex());
      vectorTypes.add(VectorType.RIGHT);
    }
    
    int[] colIndiceArray = new int[colIndices.size()];
    for (int i = 0; i < colIndices.size(); i++) {
      colIndiceArray[i] = colIndices.get(i);
    }
    
    VectorType[] vectorTypeArray = new VectorType[vectorTypes.size()];
    for (int i = 0; i < vectorTypes.size(); i++) {
      vectorTypeArray[i] = vectorTypes.get(i);
    }
    
    VectorTable table = new VectorTable(colIndiceArray, vectorTypeArray);

    List<Vector> joinRows = new ArrayList<>();
    
    for (Vector lrow : left) {
      
      Value[] keyValues = new Value[right.getIndexedColumnSet().size()];
      
      for (int  i = 0; i < join.getLeft().size(); i++) {
        Column l = join.getLeft().get(i);
        keyValues[i] = lrow.get(l.getIndex());
      }
      
      VectorKey key = new VectorKey(right.getIndexedColumnSet(), keyValues);
      RowSet rrows = right.getRowset(key);
      
      switch (join.getType()) {
        case INNER:
          if (rrows.size() == 0) {
            continue;
          }
        case OUTER:
          for (Vector rrow : rrows) {
            JoinVector joinRow = new JoinVector(table, lrow, rrow);
            joinRows.add(joinRow);
          }
      }
    }
    return new DefaultDataset(new DefaultColumnSet(joinCols), joinRows);
  }

}
