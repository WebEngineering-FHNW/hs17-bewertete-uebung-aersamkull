package mvc

/** 
 * A user
 */
class User {

	String name;
	
	
	static hasMany = [taskMaster: TaskMaster]
	static mappedBy = [taskMaster: "responsibles"]
	static belongsTo = TaskMaster;
	
    static constraints = {
		name (unique: true, minSize: 3, maxSize: 255)
    }
	
	String toString() {
		"$name"
	}
}
