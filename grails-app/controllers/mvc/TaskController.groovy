package mvc

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import groovy.json.JsonOutput

class TaskController {

	def index(){
		return tasklist( null, null, null)
	} 
	def edit(int id, int masterid, String date) {
		def fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd")
		LocalDate dateDt = date == null ? null : LocalDate.parse(date, fmt) 
		TaskBase task;
		if(id) {
			task = TaskBase.read(id)
		}
		else {
			def master = TaskMaster.read(masterid)
			task = TaskEnumerator.getOccurences(master, dateDt, dateDt)[0]
		}
		render view: "taskedit", model : [task: task, newTask: true, id: id, masterid: masterid, date: date ]
	}
	
	def taskdata(int id, int masterid, String date) { 
		TaskBase data;
		
		if(id) {
			data = TaskBase.read(id)
			
		}
		else {
			def fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd")
			LocalDate dateDt = date == null ? null : LocalDate.parse(date, fmt) 
			def master = TaskMaster.read(masterid)
			data = TaskEnumerator.getOccurences(master, dateDt, dateDt)[0]
		}
		def jsonData = [id: data.id, 
			 name: data.name, type: data.type]
		if(data instanceof TaskMaster) {
			jsonData.rrule = data.rrule.toString()
			jsonData.responsibles = data.responsibles.collect { [id: it.id, name: it.name] }
		}
		if(data instanceof TaskSingle) {
			jsonData.date = data.date.toString()
			jsonData.responsible = [id: data.responsible.id, name: data.responsible.name]
		}
		if(data instanceof TaskOccurenceException) {
			jsonData.master = data.master.id
		}

		render JsonOutput.toJson(jsonData)
	}
	
	def "new"() {
		render view: "taskedit", model : [task: new TaskSingle(), newTask: false ]
	}
	
    def tasklist(String fromDate, String toDate, Boolean ownOnly) {
		String loggedInUser = request.cookies.find{ 'Username' == it.name }?.value
		if(loggedInUser == null) {
			return redirect(controller: "user", action: "index")
		}
        def fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        if(ownOnly == null) {
			ownOnly = false
		}
        LocalDate fromDateDt = fromDate == null ? LocalDate.now().plusDays(10) : LocalDate.parse(fromDate, fmt) 
        LocalDate toDateDt = toDate == null ?LocalDate.now().plusMonths(1) : LocalDate.parse(toDate, fmt) 
        def allSingleTasks = TaskEnumerator.getTasks(fromDateDt, toDateDt, ownOnly ? loggedInUser: null);
		render view: "tasklist", model: [tasks: allSingleTasks, fromDate: fromDateDt, toDate: toDateDt]
	}
}

class TaskEnumerator {
	public static List<TaskSingle> getTasks(LocalDate fromDateDt, LocalDate toDateDt, String userFilter) {
		
		List<TaskBase> allTasks = TaskBase.where {
			(type == TaskBase.TYPE_SINGLE && (it as TaskSingle).date >= fromDateDt && (it as TaskSingle).date <= toDateDt) ||
			(type == TaskBase.TYPE_MASTER && (it as TaskMaster).rrule.start <= toDateDt && ((it as TaskMaster).rrule.until == null || (it as TaskMaster).rrule.until >= fromDate))
		}.list()
		List<TaskSingle> allSingleTasks = allTasks.findAll { it.type == TaskBase.TYPE_SINGLE }
		List<TaskSingle> occurences = allTasks.findAll { it.type == TaskBase.TYPE_MASTER }.collectMany { TaskEnumerator.getOccurences(it, fromDateDt, toDateDt) }
		allSingleTasks.addAll(occurences)
		if(userFilter != null) {
			allSingleTasks.removeIf{ it.responsible.name != userFilter  }
		}
		return allSingleTasks
	}

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
