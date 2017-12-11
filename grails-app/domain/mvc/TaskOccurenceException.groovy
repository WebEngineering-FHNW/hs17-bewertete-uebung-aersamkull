package mvc

import java.time.LocalDate


/**
 * Class for both, occurences and exceptions (share same properties)
 * Distinction between the two is possible via derived "type" property
 */
class TaskOccurenceException extends TaskSingle {
    int masterid
    
    static constraints = {
        importFrom TaskBase 
    }
}
