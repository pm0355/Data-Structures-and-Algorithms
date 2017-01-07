/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hash;

import hash.MyMap.Entry;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;

public class MyHashMap<V, K> implements MyMap<V, K> {
        public int probeCounter=0;
      public int quadCounter=0;
        int hashCounter=43;
	// Define the default hash-table size. Must be a power of 2
	private static int DEFAULT_INITIAL_CAPACITY = 4;

	// Define the maximum hash-table size. 1 << 30 is same as 2^30
	private static int  MAXIMUM_CAPACITY = 1 << 43;

	// Current hash-table capacity. Capacity is a power of 2
	private int capacity;

	// Define default load factor
	private static float DEFAULT_MAX_LOAD_FACTOR = 0.75f;

	// Specify a load factor used in the hash table
	private float loadFactorThreshold;

	// The number of entries in the map 
	private int size = 0;

	// Hash table is an ArrayList
	LinkedList<MyMap.Entry<K, V>>[] table;

	/** Construct a map with the default capacity and load factor */
	public MyHashMap() {
		this(DEFAULT_INITIAL_CAPACITY, DEFAULT_MAX_LOAD_FACTOR);
	}

	/** Construct a map with the specified initial capacity and 
	 * default load factor */
	public MyHashMap(int initialCapacity) {
		this(initialCapacity, DEFAULT_MAX_LOAD_FACTOR);
	}

	/** Construct a map with the specified initial capacity
	 * and load factor */
	public MyHashMap(int initialCapacity, float loadFactorThreshold) {
		if (initialCapacity > MAXIMUM_CAPACITY)
			this.capacity = MAXIMUM_CAPACITY;
		else
			this.capacity = trimToPowerOf2(initialCapacity);

		this.loadFactorThreshold = loadFactorThreshold;
		table = new LinkedList[capacity];
	}

	@Override /** Remove all of the entries from this map */
	public void clear() {
		size = 0;
		removeEntries();
	}

	@Override /** Return true if the specified key is in the map */
	public boolean containsKey(K key) {
		if (get(key) != null)
			return true;
		else
			return false;
	}

	@Override /** Return true if this map contains the value */
	public boolean containsValue(V value) {
		for (int i = 0; i < capacity; i++) {
			if (table[i]!=null){
                            LinkedList<Entry<K,V>> bucket=table[i];
                            for(Entry<K,V> entry: bucket)
                                if(entry.getValue().equals(value))
                                    return true;
                        }
		}

		return false;
	}

	@Override /** Return a set of entries in the map */
	public java.util.Set<MyMap.Entry<K,V>> entrySet() {
		java.util.Set<MyMap.Entry<K, V>> set = 
			new java.util.HashSet<MyMap.Entry<K,V>>();
		for (int i = 0; i < capacity; i++) {
			if (table[i] != null) {
				LinkedList<Entry<K,V>> bucket=table[i];
                                for(Entry<K,V> entry: bucket)
                                    set.add(entry);
			}
		}

		return set;
	}

	@Override /** Return the value that matches the specified key */
	public V get(K key) {
		int bucketIndex = hash(key.hashCode());

		if(table[bucketIndex] != null) {
			LinkedList<Entry<K,V>> bucket=table[bucketIndex];
			for(Entry<K,V> entry: bucket)
                            if(entry.getKey().equals(key))
                                return entry.getValue();
		}

		return null;
	}

	@Override /** Return true if this map contains no entries */
	public boolean isEmpty() {
		return size == 0;
	}

	@Override /** Return a set consisting of the keys in this map */
	public java.util.Set<K> keySet() {
		java.util.Set<K> set = new java.util.HashSet<K>();

		for (int i = 0; i < capacity; i++) {
			if (table[i] != null){
                            LinkedList<Entry<K,V>> bucket=table[i];
                            for(Entry<K,V> entry:bucket)
                                set.add(entry.getKey());
		}
                }
		return set;
	}
        
	@Override /** Remove the entry for the specified key */
	public void remove(K key) {
		int bucketIndex = hash(key.hashCode());
		
		if(get(key) != null) {
                    LinkedList<Entry<K,V>> bucket =table[bucketIndex];
			// The key is already in the map
                    for(Entry<K,V> entry: bucket)
			if (entry.getKey().equals(key)) {
                            bucket.remove(entry);
                            size--;
                            break;
                        }
                        }
	}


	@Override /** Return the number of entries in this map */
	public int size() {
		return size;
	}

	@Override /** Return a set consisting of values in this map */
	public java.util.Set<V> values() {
		java.util.Set<V> set = new java.util.HashSet<V>();
		for (int i = 0; i < capacity; i++) {
			if (table[i] != null){
			LinkedList<Entry<K,V>> bucket =table[i];
                        for(Entry<K,V> entry: bucket){
                           set.add(entry.getValue());
                           set.add(entry.getYear());
                           set.add(entry.getDept());
                           set.add(entry.getGpa());
                        }
      
                }
        
                }
		return set;
	}
	/** Hash function */
	private int hash(int hashCode) {
		return (supplementalHash(hashCode) & (capacity - 1))/43;
	}

	/** Ensure the hashing is evenly distributed */
	private static int supplementalHash(int h) {
		h ^= (h >>> 20) ^ (h >>> 12);
		return h ^ (h >>> 7) ^ (h >>> 4);
	}

	/** Return a power of 2 for initialCapacity */
	private int trimToPowerOf2(int initialCapacity) {
		int capacity = 1;
		while (capacity < initialCapacity) {
			capacity <<= 1;
		}

		return capacity;
	}

	/** Remove all entries from map */
	private void removeEntries() {
		for(int i=0; i<capacity;i++){
                if(table[i]!=null){
                table[i].clear();
                }
                }
	}

	/** Rehash the map */
	private void rehash() {
                probeCounter+=1;
		java.util.Set<Entry<K, V>> set = entrySet();
		//capacity <<= 1; // Same as capacity *= 2, linear hash shifts new entries in hash
                capacity= capacity*capacity+1;//quadratic hash shifts new entries in hash
                quadCounter+=1;// counts each quadratic probe to return an average quad probe count
		size = 0; // Reset size to 0
		table= new LinkedList[capacity]; // Clear the hash table
		for (Entry<K,V>entry: set){
			put(entry.getKey(), entry.getValue(),entry.getYear(),entry.getDept(),entry.getGpa());
                }
        }


	@Override /** Return a string repesentation for this map */
	public String toString() {
		StringBuilder builder = new StringBuilder("[");
		for (int i=0; i< capacity; i++) {
			if (table[i] != null && table[i].size() > 0)
                    for (Entry<K,V>entry: table[i]){
			builder.append(entry);
                }
		}

		builder.append("]");
		return builder.toString();
	}

    @Override
    public V put(K key, V value, V year, V dept, V gpa) {

		if(get(key) != null) {
                    int bucketIndex = hash(key.hashCode());
                    LinkedList<Entry<K,V>> bucket =table[bucketIndex];
			// The key is already in the map
                    for(Entry<K,V> entry: bucket)
			if (entry.getKey().equals(key)) {
				V oldvalue = entry.getValue();
                                V oldyear= entry.getYear();
                                V olddept= entry.getDept();
                                V oldgpa=entry.getGpa();
                              
				// Replace old value with new value
				entry.value = value;
                                entry.year=year;
                                entry.dept=dept;
                                entry.gpa=gpa;
				// Return the old value for the key
				return oldvalue;
			}
                }
 

		// Check load factor
		if (size >= capacity * loadFactorThreshold) {
			if (capacity == MAXIMUM_CAPACITY)
				throw new RuntimeException("Exceeding maximum capacity");
			rehash();
		}
                int bucketIndex = hash(key.hashCode());

                if(table[bucketIndex]==null){
                table[bucketIndex]=new LinkedList<Entry<K,V>>();
                }
                table[bucketIndex].add(new MyMap.Entry<K, V>(key,value,year,dept, gpa));
		size++; // Increase size

		return value;
    }

    @Override
    public java.util.Set<V> Search(K key) {
        int t=0;
        java.util.Set<V> set = new java.util.HashSet<V>();
	int bucketIndex = hash(key.hashCode());
        if (table[bucketIndex] != null) {
            LinkedList<Entry<K,V>> bucket=table[bucketIndex];
            for(Entry<K,V> entry: bucket){
                if(entry.getKey().equals(key)){
                  set.add(entry.getValue());
                  set.add(entry.getYear());
                  set.add(entry.getDept());
                  set.add(entry.getGpa());
                  set.add((V) entry.getKey());
                  t=1;
                return set;  
                }
                
            
            }
            
            
        }
       if(t==0){
                System.out.println("Error, record not found.");
                
            }
        return null;
    }

    @Override
    public int averageProbe() {
        return probeCounter/43;
       // return quadraticCounter/43;
    }

    
    
}