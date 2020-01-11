
public class ClassDifferException extends Exception{
	private static final long serialVersionUID = 5591379474849171473L;
	
	private Object a;
	private Object b;
	
	public ClassDifferException(Object a, Object b) {
		super("");

		this.a = a;
		this.b = b;
	}
	
	public void printStackTrace() {
		StringBuffer sb = new StringBuffer("");
		
		sb.append("Delta clases differs. Comparing ~");
		sb.append(a.getClass());
		sb.append("~ with ~");
		sb.append(b.getClass());
		sb.append("~");
		 
		System.out.println(sb.toString());
	}
	

}
