package mvc

import java.time.LocalDate

/**
 * Represents a single task, meaning a task that occurs once at a specified date.¨
 * TaskOccurence derives from this class
 */
class TaskSingle extends TaskBase {
    User responsible
    LocalDate date

    static constraints = {
      importFrom TaskBase 
      name (unique: ['date'])
    }

    String toString() {
		"$name at $date ($type)"
	}
}
