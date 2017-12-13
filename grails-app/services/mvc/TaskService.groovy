package mvc

import grails.transaction.Transactional
import java.time.LocalDate

/**
 * Service for more complex task tasks 
 */
@Transactional
class TaskService {

    /**
     * Get a single task from either an id or a masterid and a date
     */
    def getTask(int id, int masterid, LocalDate date) { 		
		if(id) {
			return TaskBase.read(id)			
		}
		else {
			def master = TaskMaster.read(masterid)
			return taskService.getOccurences(master, date, date)[0]
		}
    }

    /**
     * Gets all tasks (occurences) between the given dates and optionally filtered by a user
     */
    public List<TaskSingle> getTasks(LocalDate fromDateDt, LocalDate toDateDt, String userFilter) {
		
		List<TaskBase> allTasks = TaskBase.where {
			(type == TaskBase.TYPE_SINGLE && (it as TaskSingle).date >= fromDateDt && (it as TaskSingle).date <= toDateDt) ||
			(type == TaskBase.TYPE_MASTER && (it as TaskMaster).rrule.start <= toDateDt && ((it as TaskMaster).rrule.until == null || (it as TaskMaster).rrule.until >= fromDate))
		}.list()
		List<TaskSingle> allSingleTasks = allTasks.findAll { it.type == TaskBase.TYPE_SINGLE }
		List<TaskSingle> occurences = allTasks.findAll { it.type == TaskBase.TYPE_MASTER }.collectMany { this.getOccurences(it, fromDateDt, toDateDt) }
		allSingleTasks.addAll(occurences)
		if(userFilter != null) {
			allSingleTasks.removeIf{ it.responsible.name != userFilter  }
		}
		return allSingleTasks
	}

    /**
     * Gets the occurences from a task master between two given dates
     */
	public List<TaskOccurenceException> getOccurences(TaskMaster tm, LocalDate fromDate, LocalDate toDate) {
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
		Set<TaskOccurenceException> exp = TaskOccurenceException.findAllByMasterid(tm.id);
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
						 masterid: tm.id,
						 date: currentDate,
						 responsible: tm.responsibles.getAt(responsibleId))
				}
				listOfOccurences.push(oce)
			}
			nrOccurencesFromStart++
			currentDate = addByFrequency(currentDate, tm.rrule.freq, tm.rrule.interval)
			responsibleId = responsibleCount==0? 0: ((responsibleId+1) % responsibleCount)
		}
		return listOfOccurences
	}

    /**
     * Returns a new date with the added interval
     */
	private static LocalDate addByFrequency(LocalDate date1, String freq, int interval) {
		// If's here are a bit of a code smell, but let's not make it complicated :)
		if(freq == Rrule.FreqDaily) {
			return date1.plusDays(interval);
		}
		else if(freq == Rrule.FreqMonthly) {
			return date1.plusMonths(interval);
		}
		else if(freq == Rrule.FreqWeekly) {
			return date1.plusWeeks(interval);
		}
		else if(freq == Rrule.FreqYearly) {
			return date1.plusYears(interval);
		}
		else {
			throw new IllegalArgumentException("freq");
		}
	}
}
