package mvc

abstract class TaskBase {
    String name
	String description
	String type
	
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
