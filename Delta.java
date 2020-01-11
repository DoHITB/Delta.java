import java.util.ArrayList;

/**
 * Delta Class
 * 
 * Helper class that is suitable to do delta comparation between two data array
 * 
 * The data array must have same Class, either it would not be able to compare
 * Also, the Class must extend the Historify Class, as it have comparation
 * methods declared that are being used on this class:
 * 
 *  - equals: this is a final boolean method, so there is no need to implement
 *  		  it on subclasses. It does a compare, but only having in mind the
 *  		  ID's.
 *  
 *  - hardEquals: this method must be implemented on each subclass. It may
 *  			  do a compare of all attributes in class.
 *  
 * @author DoHITB
 *
 * @param <T extends Historify>: The Class is being used.
 */
public class Delta <T extends Historify>{
	private T[] old;
	private T[] cur;
	
	private ArrayList<T> ins;
	private ArrayList<T> upd;
	private ArrayList<T> del;
	private ArrayList<T> alpha;
	private ArrayList<T> aandb;
	private ArrayList<T> beta;
	
	/**
	 * Class constructor
	 * 
	 * @param old: an array of <T> Class having the previous data
	 * @param cur: an array of <T> Class having the current data
	 */
	public Delta(T[] old, T[] cur) {
		this.old = old;
		this.cur = cur;
	}
	
	/**
	 * Main method for Delta Class.
	 * 
	 * It distributes data, then do the Delta.
	 * After this execution, "ins", "upd", and "del" will be fulfilled.
	 * 
	 * @throws ClassDifferException if "old" and "cur" have different class.
	 */
	public void check() throws ClassDifferException{
		//restart all the data
		init();
		
		//first, distribute all the data.
		this.distribute();
		
		//then, do the delta
		this.doDelta();
	}
	
	/**
	 * Distribute method.
	 * 
	 * Based on "old" and "cur", it distributes which data are on both sides,
	 * and which one is only on one of the data arrays
	 * 
	 * @throws ClassDifferException if "old" and "cur" have different class.
	 */
	private void distribute() throws ClassDifferException{
		//first, check old vs cur
		this.compare(this.old, this.cur, true, this.aandb, this.alpha, true);

		//then, check for cur vs old
		this.compare(this.cur, this.old, false, null, this.beta, true);
	}
	
	
	@SuppressWarnings("unchecked")
	/**
	 * doDelta method.
	 * 
	 * Based on the results of "distribute", it distributes which data are on 
	 * both sides, and which one is only on one of the data arrays
	 * 
	 * @throws ClassDifferException if "old" and "cur" have different class.
	 */
	private void doDelta() throws ClassDifferException{
		T[] alphaA = (T[]) new Historify[this.alpha.size()];
		T[] betaA  = (T[]) new Historify[this.beta.size()];
		
		alphaA = this.alpha.toArray(alphaA);
		betaA  = this.beta.toArray(betaA);
		
		
		//finally, check alpha vs beta, and beta vs alpha
		this.compare(alphaA, betaA, true, this.upd, this.del, false);
		
		this.compare(betaA, alphaA, false, null, this.ins, false);
	}
	
	/**
	 * Compare method.
	 * 
	 * It checks if given array exists on the given array. If "storeEquals" is
	 * true, it will store all data existing on the given array. If "strict" is
	 * true, it will do a full comparation of the object against the data array
	 * 
	 * @param a: array to be checked object by object
	 * @param b: array to be checked against
	 * @param storeEquals: if true, positive results will be stored
	 * @param equal: ArrayList on which positive results will be stored
	 * @param differ: ArrayList on which negative results will be stored
	 * @param strict: if true, a full comparation will be done.
	 * @throws ClassDifferException if "old" and "cur" have different class.
	 */
	private void compare(T[] a, T[] b, boolean storeEquals, 
						 ArrayList<T> equal, ArrayList<T> differ,
						 boolean strict) throws ClassDifferException{
		for(T obj : a) 
			if(this.equals(obj, b, strict)) 
				//the Object exist both in old and cur
				this.storeEquals(storeEquals, equal, obj);
			else 
				//the Object only exist in old
				differ.add(obj);
	}
	
	/**
	 * Equals method.
	 * 
	 * It checks if given object exists on the given array. If "strict" is true
	 * it will do a full compare; else it will only compare both ID's.
	 * 
	 * @param o: object to be checked against array
	 * @param a: array to be checked against
	 * @param strict: if true, it will do a full comparation
	 * @return true if found, else otherwise
	 * @throws ClassDifferException if "old" and "cur" have different class.
	 */
	private boolean equals(T o, T[] a, boolean strict) 
			               throws ClassDifferException{
		for(T obj : a)
			if(obj.equals(o, strict))
				return true;

		return false;
	}
	
	/**
	 * StoreEquals method.
	 * 
	 * It checks if positive results may be stored, then store if necessary
	 * 
	 * @param done: if true, results will be stored
	 * @param target: ArrayList to be increased
	 * @param item: Object to be stored
	 */
	private void storeEquals(boolean done, ArrayList<T> target, T item) {
		if(done)
			target.add(item);
	}
	
	/**
	 * Init method.
	 * 
	 * It initializes all the ArrayLists
	 */
	private void init() {
		this.aandb = new ArrayList<T>();
		this.alpha = new ArrayList<T>();
		this.beta  = new ArrayList<T>();
		this.del   = new ArrayList<T>();
		this.ins   = new ArrayList<T>();
		this.upd   = new ArrayList<T>();
	}
	
	/**
	 * getInserted method.
	 * 
	 * Getter method ins attribute
	 * @return ArrayList<T> ins
	 */
	public ArrayList<T> getInserted(){
		return this.ins;
	}

	/**
	 * getUpdated method.
	 * 
	 * Getter method upd attribute
	 * @return ArrayList<T> upd
	 */
	public ArrayList<T> getUpdated(){
		return this.upd;
	}

	/**
	 * getDeleted method.
	 * 
	 * Getter method del attribute
	 * @return ArrayList<T> del
	 */
	public ArrayList<T> getDeleted(){
		return this.del;
	}
}
