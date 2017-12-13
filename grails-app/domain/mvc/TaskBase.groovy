package mvc

/** 
 * Represents a base class for tasks having all properties that all task types have in common
 */
abstract class TaskBase {
    String name
	String description
	String type // Actually, we could get this from the class but we want to decouple this 
				// in order to be flexible
	
	static final String TYPE_MASTER = "MASTER"
	static final String TYPE_OCCURENCE = "OCCURENCE"
	static final String TYPE_EXCEPTION = "EXCEPTION"
	static final String TYPE_SINGLE = "SINGLE"
	
    static constraints = {
		name (blank: false, maxSize: 50, minSize: 3)
        description (nullable: true, blank: true, maxSize: 4000)
		type inList: [TYPE_MASTER, TYPE_OCCURENCE, TYPE_EXCEPTION, TYPE_SINGLE] 
    }
	
	String toString() {
		"$name ($type)"
	}
}
