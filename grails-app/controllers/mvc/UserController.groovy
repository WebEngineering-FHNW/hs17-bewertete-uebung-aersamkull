package mvc

import groovy.json.JsonBuilder
import javax.servlet.http.Cookie

class UserController {

	// static allowedMethods = [loginuser:'POST', logout: 'POST']

    def index() { 
		def users = User.list()
		render view: "index", model : [users: users]
	}
	
	def loginuser(User us) {
		
		if(!us.name) {
			throw new IllegalArgumentException("userName")
		}
		Cookie userCookie = new Cookie( 'Username', us.name )
		userCookie.path = '/'
		userCookie.setHttpOnly(true)
		userCookie.maxAge = -1
		response.addCookie userCookie
		if(!User.findByName(us.name)) {
			us.save();
		}
		redirect  (uri: "/")
		
		//render "Ok"
	}

	def usersdata() {
		def data = User.list()
		def json = new JsonBuilder()
		json {
			dt(data.collect { [id: it.id, name: it.name] })
		}	
		render json.toPrettyString()
	}
	
	def logout() {
		
		Cookie cookie = new Cookie('Username', '')
		cookie.setVersion(-1)
		cookie.path = "/"
		cookie.maxAge = 0

		response.addCookie(cookie)

		render "Logged out!"
	}
}
