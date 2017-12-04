package mvc

import java.time.LocalDate

/**
 * A class representing a "master" that will yield multiple occurences
 */
class TaskMaster extends TaskBase {
	
	// Loosely oriented on iCalendar(https://lists.oasis-open.org/archives/obix-xml/200708/msg00001.html)
	Rrule rrule;
	
	List<User> responsibles
	static hasMany = [responsibles: User, deletedOccurences: DeletedTask, exceptions: TaskOccurenceException]
	static mappedBy = [responsibles: "taskMaster", exceptions: "master"]

	static embedded = ["rrule"]	
	
	static constraints = {
		rrule (nullable: false)
		type inList: [TaskBase.TYPE_MASTER]
		responsibles (minSize: 1)
	}
	
	TaskMaster() {
		type = TaskBase.TYPE_MASTER
		
	}
}

/**
 * A class that represents the repetition rule
 */
class Rrule {
	String freq
	Integer count
	LocalDate start
	LocalDate until
	int interval


	static final String FreqDaily = "DAILY"
	static final String FreqWeekly = "WEEKLY"
	static final String FreqMonthly = "MONTHLY"
	static final String FreqYearly = "YEARLY" 
	
    static constraints = {
		freq inList: [FreqDaily, FreqWeekly, FreqMonthly, FreqYearly]
		count(validator: {val, obj->
			if(val && obj.until) {
				return 'taskfreq.until_or_count'
			}
		}, nullable: true)
		interval (min: 1)
		until (nullable: true)
	}

	String toString() {
		def endStr = (count != null ? "COUNT=$count;": (until != null ? "UNTIL=$until;" : ""))
		"FREQ=$freq;INTERVAL=$interval;" + endStr + "START=$start;"
	}
	
	static mapping = {
		interval defaultValue: "1"
	 }
}

/** 
 * Actually a wrapper around the LocalDate object
 */
class DeletedTask {
	LocalDate date;
}