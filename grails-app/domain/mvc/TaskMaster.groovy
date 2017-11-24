package mvc

import java.time.LocalDate

class TaskMaster extends TaskBase {
	
	static final String FreqDaily = "DAILY"
	static final String FreqWeekly = "WEEKLY"
	static final String FreqMonthly = "MONTHLY"
	static final String FreqYearly = "YEARLY" 
	
	// Loosely oriented on iCalendar(https://lists.oasis-open.org/archives/obix-xml/200708/msg00001.html)
	String rrule_freq
	Integer rrule_count
	LocalDate rrule_until
	int rrule_interval
	
	
	static hasMany = [responsibles: User]
	static mappedBy = [responsibles: "taskMaster"]
		
	
	
    static constraints = {
		rrule_freq inList: [FreqDaily, FreqWeekly, FreqMonthly, FreqYearly]
		rrule_count(validator: {val, obj->
			if(val && obj.rrule_until) {
				return 'taskmaster.until_or_count'
			}
		})
		rrule_interval (min: 1)
	}
	
	
}
