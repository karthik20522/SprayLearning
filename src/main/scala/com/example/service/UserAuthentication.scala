package com.example.service

import spray.routing.authentication.Authentication
import spray.routing.authentication.ContextAuthenticator
import scala.concurrent.Future
import com.typesafe.config.ConfigFactory
import scala.concurrent.ExecutionContext.Implicits.global
import spray.routing.AuthenticationFailedRejection

case class User(userName: String, token: String) {}

trait UserAuthentication {

  val conf = ConfigFactory.load()
  lazy val configusername = conf.getString("security.username")
  lazy val configpassword = conf.getString("security.password")

  def authenticateUser: ContextAuthenticator[User] = {
    ctx =>
      {
        //get username and password from the url        
        val usr = ctx.request.uri.query.get("usr").get
        val pwd = ctx.request.uri.query.get("pwd").get

        doAuth(usr, pwd)
      }
  }

  private def doAuth(userName: String, password: String): Future[Authentication[User]] = {
    //here you can call database or a web service to authenticate the user    
    Future {
      Either.cond(password == configpassword && userName == configusername,
        User(userName = userName, token = java.util.UUID.randomUUID.toString),
        AuthenticationFailedRejection("CredentialsRejected"))
    }
  }
}