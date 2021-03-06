package org.sapia.tad.transform.view;

import org.sapia.tad.Column;
import org.sapia.tad.Dataset;
import org.sapia.tad.Datatype;
import org.sapia.tad.NominalSet;
import org.sapia.tad.format.Format;
import org.sapia.tad.parser.Parser;

/**
 * An instance of this class is meant to wrap the column of a {@link Dataset}
 * that is itself wrapped into a {@link ViewDataset}. 
 * 
 * @author yduchesne
 *
 */
class ViewColumn implements Column {
  
  private int    index;
  private String name;
  private Column delegate;
  
  ViewColumn(int index, Column delegate) {
    this(index, delegate.getName(), delegate);
  }
  
  ViewColumn(int index, String name, Column delegate) {
    this.index    = index;
    this.name     = name;
    this.delegate = delegate;
  }
  
  @Override
  public NominalSet getNominalValues() {
    return delegate.getNominalValues();
  }

  @Override
  public int getIndex() {
    return index;
  }
  
  @Override
  public String getName() {
    return name;
  }
  
  @Override
  public Datatype getType() {
    return delegate.getType();
  }

  @Override
  public Format getFormat() {
    return delegate.getFormat();
  }
  
  @Override
  public void setFormat(Format formatter) {
    delegate.setFormat(formatter);
  }
  
  @Override
  public Parser getParser() {
    return delegate.getParser();
  }
  
  @Override
  public void setParser(Parser parser) {
    delegate.setParser(parser);
  }
  
  @Override
  public Column copy(int newIndex) {
    return delegate.copy(newIndex);
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Column) {
      return delegate.equals((Column) obj);
    }
    return false;
  }
  
  @Override
  public String toString() {
    return delegate.toString();
  }
}
