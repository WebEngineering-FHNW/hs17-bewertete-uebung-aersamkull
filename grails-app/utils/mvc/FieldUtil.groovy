package mvc

import grails.validation.Validateable
import org.springframework.validation.FieldError

/**
 * Not required currently. However, this could change later
 */
class FieldUtil {
    static boolean hasError(Validateable model, String propertyName) {
        null != findError(model, propertyName)
    }
    static FieldError findError(Validateable model, String propertyName) {
        model.errors.fieldErrors.find {it.field == propertyName}
    }
}
