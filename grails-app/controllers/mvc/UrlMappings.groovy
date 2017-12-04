package mvc

class UrlMappings {

    static mappings = {
        // "/rooms"(resources: "room")
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }
        "/"(controller:"task", action: "tasklist")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
