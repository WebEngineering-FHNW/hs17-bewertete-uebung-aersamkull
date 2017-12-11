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
	
	def tasklist4debug(){
		def data = TaskBase.list()
		def jsons = data.collect { getTaskJson(it)}
		render JsonOutput.toJson(jsons)
	}
	private def getTaskJson(TaskBase data) {

		def jsonData = [id: data.id, description: data.description,
			 name: data.name, type: data.type]
		if(data instanceof TaskMaster) {
			jsonData.rrule = data.rrule.toString()
			jsonData.responsibles = data.responsibles.collect { [id: it.id, name: it.name] }
		}
		if(data instanceof TaskSingle) {
			jsonData.date = data.date.toString()
			if(data.responsible) {
				jsonData.responsible = [id: data.responsible.id, name: data.responsible.name]
			}
		}
		if(data instanceof TaskOccurenceException) {
			jsonData.masterid = data.masterid
		}
		return jsonData
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
		def jsonData = getTaskJson(data)

		render JsonOutput.toJson(jsonData)
	}
	
	def "new"() {
		render view: "taskedit", model : [task: new TaskSingle(), newTask: false ]
	}

	def save() { 
		def fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd")
		def task;
		if(!params.id || params.id == "" || params.id == "0") {
			if(params.type == TaskBase.TYPE_MASTER) {
				task = new TaskMaster(type: TaskBase.TYPE_MASTER);
			}
			else if(params.type == TaskBase.TYPE_SINGLE) {
				task = new TaskSingle(type: TaskBase.TYPE_SINGLE);
			} 
			else if(params.type == TaskBase.TYPE_OCCURENCE) {
				TaskMaster master = TaskMaster.get(params.masterid)				
				task = new TaskOccurenceException(type: TaskBase.TYPE_EXCEPTION, masterid: master.id, date: LocalDate.parse(params.date, fmt) );
			} 
			else {
				throw IllegalArgumentException("type");
			}
		}
		else {
			task = TaskBase.get(params.id);
			if(!task) {
				throw IllegalArgumentException("task");
			}
		} 
		task.name = params.name
		task.description = params.description
		if(task.type == TaskBase.TYPE_MASTER) {
			task.responsibles = params.responsibles.collect { User.get(it.toInteger()) }
			task.rrule = new Rrule()
			task.rrule.freq = params.rrule_freq
			task.rrule.start = LocalDate.parse(params.rrule_start, fmt) 
			task.rrule.until = params.rrule_until ? LocalDate.parse(params.rrule_until, fmt)  : null
			task.rrule.count = params.rrule_count ? params.rrule_count : null
			task.rrule.interval = params.rrule_interval.toInteger()
		}
		else if (task.type == TaskBase.TYPE_SINGLE || task.type == TaskBase.TYPE_EXCEPTION) {
			task.responsible = User.get(params.responsible.toInteger())
			if(params.type == TaskBase.TYPE_SINGLE) {
				// Cannot change the date of a task occurence or task exception 
				task.date = LocalDate.parse(params.date, fmt) 
			}
			
		}
		
		task.save(failOnError: true)
	//	redirect  (uri: "/")
		render model: getTaskJson(task)
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
