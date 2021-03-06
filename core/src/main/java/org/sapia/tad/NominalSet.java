package org.sapia.tad;

import org.sapia.tad.util.Checks;
import org.sapia.tad.value.Value;

import java.util.*;

/**
 * Holds a set of {@link Nominal} instances.
 * 
 * @author yduchesne
 *
 */
public class NominalSet {
  
  private Map<String, Nominal> byName = new HashMap<>();
  private Map<Integer, Nominal> byValue = new HashMap<>();
  
  public NominalSet(List<Nominal> nominals) {
    for (Nominal n : nominals) {
      byName.put(n.getName(), n);
      byValue.put(n.getValue(), n);
    }
  }
  
  /**
   * @param value a nominal value.
   * @return the {@link Nominal} instance corresponding to the given value.
   * @throws IllegalArgumentException if no nominal instance is found for the 
   * provided value.
   */
  public Nominal getByValue(int value) throws IllegalArgumentException {
    return Checks.notNull(byValue.get(value), "No nominal found for value: %s", value);
  }

  /**
   * @param name a name corresponding to a nominal value.
   * @return the {@link Nominal} instance corresponding to the given name.
   * @throws IllegalArgumentException if no nominal instance is found for the 
   * provided name.
   */
  public Nominal getByName(String name) throws IllegalArgumentException {
    return Checks.notNull(byName.get(name), "No nominal found for name: %s. Got %s", name, byName.keySet());
  }
  
  /**
   * @return this instance's {@link Nominal} array.
   */
  public Collection<Nominal> getValues() {
    return Collections.unmodifiableCollection(byName.values());
  }
  
  /**
   * @return <code>true</code> if this instance is empty.
   */
  public boolean isEmpty() {
    return byName.isEmpty();
  }
  
  /**
   * @return this instance's size - in terms of the number of {@link Nominal} 
   * instances it holds.
   */
  public int size() {
    return byName.size();
  }
  
  /**
   * @param nominals some {@link Nominal} instances.
   * @return a new instance of this class, encapsulating the given {@link Nominal}s.
   */
  public static NominalSet newInstance(Nominal...nominals) {
    return new NominalSet(Arrays.asList(nominals));
  }
  
  /**
   * @param nominals a {@link Collection} of nominal array.
   * @return the {@link NominalSet} holding the {@link Nominal} instances corresponding to the
   * given array.
   */
  public static NominalSet newInstance(Collection<Value> nominals) {
    List<Nominal> nominalList = new ArrayList<>(nominals.size());
    for (Value n : nominals) {
      nominalList.add(new Nominal(n.toString(), nominalList.size()));
    }
    return new NominalSet(nominalList);
  }

  /**
   * @param nominals some nominal array (for which each will be assigned a value corresponding
   * to its index in the given array.
   * @return a new instance of this class, encapsulating {@link Nominal}s corresponding to the
   * array given as input.
   */
  public static NominalSet newInstance(Value...nominals) {
    List<Nominal> nominalList = new ArrayList<>(nominals.length);
    for (Value n : nominals) {
      nominalList.add(new Nominal(n.toString(), nominalList.size()));
    }
    return new NominalSet(nominalList);
  }
}
