package app1

import javax.ws.rs.core.{MediaType, Response}
import javax.ws.rs.{Consumes, POST, Path, Produces}

@Path("/protected")
@Consumes(Array(MediaType.APPLICATION_JSON))
@Produces(Array(MediaType.APPLICATION_JSON))
class InjectionResource {
   @POST
   def doPost(@BiSessionIdInjectable injectable: BiSessionId, body: String) = {
     val response = s"doPost  body: $body, injectable: $injectable, value: ${injectable.get}"
    println(response)
     Response.ok.`type`(MediaType.TEXT_PLAIN).entity(response).build()
   }
}
