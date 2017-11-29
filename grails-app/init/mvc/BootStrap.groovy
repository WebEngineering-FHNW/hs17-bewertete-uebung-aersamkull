package mvc

import grails.util.Environment

class BootStrap {

    def init = { servletContext ->
        if (Environment.current == Environment.PRODUCTION) {
            return
        }

		User user1 = save(new User(name: "User1"));
		User user2 = save(new User(name: "User2"));
		User user3 = save(new User(name: "User3"));
		
		Rrule rule = new Rrule(start: new java.time.LocalDate(1993, 4, 16), freq: Rrule.FreqWeekly, interval: 2);
		TaskMaster tm = new TaskMaster(name: "Bi-Weekly clean the coffee machine", responsibles: [user1], rrule: rule);
		save(tm);
		
		
        Room windowRoom = save(new Room(name: "1.333", max: 32))
        Room cornerRoom = save(new Room(name: "1.331", max: 42))

        Person dierk = save(new Person(firstName: "Dierk", lastName: "König", email:"dierk.koenig@fhnw.ch"))

        Date today = new Date().clearTime();
        save(new Booking(booker: dierk, room: windowRoom, date: today - 1, slot: Booking.AM))
        save(new Booking(booker: dierk, room: windowRoom, date: today,     slot: Booking.AM))
        save(new Booking(booker: dierk, room: windowRoom, date: today + 1, slot: Booking.AM))
        save(new Booking(booker: dierk, room: cornerRoom, date: today ,    slot: Booking.AM))

    }

    static save(domainObject) {
        domainObject.save(failOnError: true)
    }

    def destroy = {
    }
}
