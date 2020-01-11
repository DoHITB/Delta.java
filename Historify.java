/**
 * Historify Class.
 * 
 * Base Class to be used on Delta Class. It provides the basic methods to be
 * used on Delta.
 * 
 * @author DoHITB
 *
 */
public abstract class Historify{
	/**
	 * Object Id. It identifies each class instance
	 */
	private Object id;
	
	/**
	 * Class Constructor.
	 * 
	 * It checks that "id" cannot be null.
	 * @param id: instance ID
	 * @throws NullIdException if id attribute is null
	 */
	public Historify(Object id) throws NullIdException{
		if(id == null)
			throw new NullIdException("Invalid ID provided");
		
		this.id = id;
	}
	
	/**
	 * Equals method.
	 * 
	 * It compares current instance against given object. Both objects must
	 * have same class to be compared.
	 * 
	 * @param o: object to compare against
	 * @param strict: if true, full comparation will be done (hardEquals)
	 * @return true if equals; false otherwise
	 * @throws ClassDifferException if o class differs from current class.
	 */
	public final boolean equals(Historify o, boolean strict) 
								throws ClassDifferException {
		if(!o.getClass().equals(this.getClass()))
			throw new ClassDifferException(this, o);
		
		if(strict)
			return this.hardEquals(o);
		
		return this.id.equals(o.getId());
	}
	
	/**
	 * HardEquals method.
	 * 
	 * It makes a full comparation of all attributes on the class.
	 * @param o: object to compare against
	 * @return true if equals; false otherwise
	 */
	public abstract boolean hardEquals(Historify o);
	
	/**
	 * GetId method.
	 * 
	 * Getter for "id" attribute
	 * @return id
	 */
	public Object getId() {
		return this.id;
	}
}
