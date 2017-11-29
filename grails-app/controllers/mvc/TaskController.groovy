package mvc

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TaskController {

    def tasklist(String fromDate, String toDate, Boolean ownOnly) {
        def fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        if(ownOnly == null) {
			ownOnly = false
		}
        LocalDate fromDateDt = fromDate == null ? LocalDate.now().plusDays(10) : LocalDate.parse(fromDate, fmt) 
        LocalDate toDateDt = toDate == null ?LocalDate.now().plusMonths(1) : LocalDate.parse(toDate, fmt) 
        
		List<TaskBase> allTasks = TaskBase.where {
			(type == TaskBase.TYPE_SINGLE && (it as TaskSingle).date >= fromDateDt && (it as TaskSingle).date <= toDateDt) ||
			(type == TaskBase.TYPE_MASTER && (it as TaskMaster).rrule.start <= toDateDt && ((it as TaskMaster).rrule.until == null || (it as TaskMaster).rrule.until >= fromDate))
		}.list()
		List<TaskSingle> allSingleTasks = allTasks.findAll { it.type == TaskBase.TYPE_SINGLE }
		List<TaskSingle> occurences = allTasks.findAll { it.type == TaskBase.TYPE_MASTER }.collectMany { TaskEnumerator.getOccurences(it, fromDateDt, toDateDt) }
		allSingleTasks.addAll(occurences)
        if(ownOnly == true) {
			String ofUser = request.cookies.find{ 'Username' == it.name }?.value
            allSingleTasks.removeIf{ it.responsible.name != ofUser  }
        }
		render view: "tasklist", model: [tasks: allSingleTasks, fromDate: fromDateDt, toDate: toDateDt]
	}
}

class TaskEnumerator {
	
	public static List<TaskOccurenceException> getOccurences(TaskMaster tm, LocalDate fromDate, LocalDate toDate) {
		if(tm.rrule.start > toDate) {
			return [];
		}
		if(tm.rrule.until != null && tm.rrule.until < fromDate) {
			return [];
		}
		int nrOccurencesFromStart = 0;
		def listOfOccurences = [];
		LocalDate currentDate = tm.rrule.start;
		Set<LocalDate> deletedOccurences = tm.deletedOccurences*.date;
		Set<TaskOccurenceException> exp = tm.exceptions;
		Set<LocalDate> exceptionDates = exp*.date;
		int responsibleCount = tm.responsibles.size()
		int responsibleId = 0
		while(currentDate <= toDate) {
			if(tm.rrule.count != null && tm.rrule.count == nrOccurencesFromStart) {
				break;
			}
			if(currentDate >= fromDate && !deletedOccurences.contains(currentDate)) {
				TaskOccurenceException oce;
				if(exceptionDates.contains(currentDate)) {
					oce = exp.find { it.date == currentDate };
				}
				else {
				 	oce = new TaskOccurenceException(type: TaskBase.TYPE_OCCURENCE,
						 name: tm.name,
						 description: tm.description,
						 master: tm,
						 date: currentDate,
						 responsible: tm.responsibles.getAt(responsibleId))
				}
				listOfOccurences.push(oce)
			}
			nrOccurencesFromStart++
			currentDate = addByFrequency(currentDate, tm.rrule.freq, tm.rrule.interval)
			responsibleId = (responsibleId+1) % responsibleCount
		}
		return listOfOccurences
	}
	private static LocalDate addByFrequency(LocalDate date1, String freq, int interval) {
		// If's here are a bit of a code smell, but let's no make it complicated :)
		if(freq == Rrule.FreqDaily) {
			return date1.plusDays(interval);
		}
		else if(freq == Rrule.FreqMonthly) {
			return date1.plusMonths(interval);
		}
		else if(freq == Rrule.FreqWeekly) {
			return date1.plusMonths(interval);
		}
		else if(freq == Rrule.FreqYearly) {
			return date1.plusYears(interval);
		}
		else {
			throw new IllegalArgumentException("freq");
		}
	}
}
