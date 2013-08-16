package com.example.service

import akka.actor.Actor
import spray.routing._
import spray.http._
import spray.http.MediaTypes._
import spray.routing.Directive.pimpApply
import spray.routing.directives.CompletionMagnet.fromObject
import com.example.model.Customer
import spray.httpx.Json4sSupport
import org.json4s.Formats
import org.json4s.DefaultFormats
import com.example.model.Customer
import org.json4s.JsonAST.JObject

// we don't implement our route structure directly in the service actor because
// we want to be able to test it independently, without having to spin up an actor
class CustomerServiceActor extends Actor with CustomerService with AjaxService {
  implicit def json4sFormats: Formats = DefaultFormats
  // the HttpService trait defines only one abstract member, which
  // connects the services environment to the enclosing actor or test
  def actorRefFactory = context

  // this actor only runs our route, but you could add
  // other things here, like request stream processing
  // or timeout handling
  def receive = runRoute(customerRoutes ~ ajaxRoutes)
}

trait AjaxService extends HttpService {
  val ajaxRoutes =
    path("search" / Segment) { query =>
      get {
        complete {
          //Free text search implementation
          s"success ${query}"
        }
      }
    }
}

// this trait defines our service behavior independently from the service actor
trait CustomerService extends HttpService with Json4sSupport {

  val customerRoutes =
    path("addCustomer") {
      post {
        entity(as[JObject]) { customerObj =>
          complete {
            val customer = customerObj.extract[Customer]
            //insert customer information into a DB and return back customer obj
            customer
          }
        }
      }
    } ~
      path("getCustomer" / Segment) { customerId =>
        get {
          complete {
            //get customer from db using customerId as Key
            val customer = Customer(id = Some(customerId), firstName = "Karthik", lastName = "Srinivasan")
            customer
          }
        }
      }
}