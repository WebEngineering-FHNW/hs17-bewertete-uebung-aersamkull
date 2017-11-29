package mvc

import grails.databinding.converters.ValueConverter

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DateConverter implements ValueConverter {
    @Override
    boolean canConvert(Object value) {
        value instanceof String
    }

    @Override
    Object convert(Object value) {
        def fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        LocalDate.parse(value, fmt)
    }

    @Override
    Class<?> getTargetType() {
        LocalDate
    }
}