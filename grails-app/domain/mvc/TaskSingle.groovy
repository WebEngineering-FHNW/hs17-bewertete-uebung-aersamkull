package mvc

import java.time.LocalDate

/**
 * Represents a single task, meaning a task that occurs once at a specified date.¨
 * TaskOccurence derivs from this class
 */

class TaskSingle extends TaskBase {
    User responsible
    LocalDate date

    static constraints = {
    }
}
