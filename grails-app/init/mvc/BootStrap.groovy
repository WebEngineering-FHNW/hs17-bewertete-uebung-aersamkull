package mvc

import grails.util.Environment
import java.time.LocalDate

class BootStrap {

    def init = { servletContext ->
        if (Environment.current == Environment.PRODUCTION) {
            return
        }

		User user1 = save(new User(name: "User1"));
		User user2 = save(new User(name: "User2"));
		User user3 = save(new User(name: "User3"));
		
		Rrule rule = new Rrule(start: new LocalDate(1993, 4, 16), freq: Rrule.FreqWeekly, interval: 2);
		TaskMaster tm = new TaskMaster(name: "Bi-Weekly clean the coffee machine", responsibles: [user1, user2, user3], rrule: rule);
		save(tm);
		TaskSingle sl = new TaskSingle(type: TaskBase.TYPE_SINGLE, name: "Cleen the floor", responsible: user1, date: new LocalDate(2018,1,2));
        save(sl);
    }

    static save(domainObject) {
        domainObject.save(failOnError: true)
    }

    def destroy = {
    }
}
