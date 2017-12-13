package mvc

/** 
 * A user, currently only has a name, but this could be changed later on
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
