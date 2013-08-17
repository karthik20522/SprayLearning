package com.example.dal
import com.mongodb.casbah.Imports._
import com.example.model.Customer

class CustomerDal {

  val conn = MongoFactory.getConnection

  def saveCustomer(customer: Customer) = {
    val customerObj = buildMongoDbObject(customer)
    val result = MongoFactory.getCollection(conn).save(customerObj)
    val id = customerObj.getAs[org.bson.types.ObjectId]("_id").get
    println(id)
    id
  }

  def findCustomer(id: String) = {
    var q = MongoDBObject("_id" -> new org.bson.types.ObjectId(id))
    val collection = MongoFactory.getCollection(conn)
    val result = collection findOne q

    val customerResult = result.get

    val customer = Customer(firstName = customerResult.as[String]("firstName"),
      lastName = customerResult.as[String]("lastName"),
      _id = Some(customerResult.as[org.bson.types.ObjectId]("_id").toString()),
      phoneNumber = Some(customerResult.as[String]("phoneNumber")),
      address = Some(customerResult.as[String]("address")),
      city = Some(customerResult.as[String]("city")),
      country = Some(customerResult.as[String]("country")),
      zipcode = Some(customerResult.as[String]("zipcode")))

    customer //return the customer object
  }

  //Convert our Customer object into a BSON format that MongoDb can store.
  private def buildMongoDbObject(customer: Customer): MongoDBObject = {
    val builder = MongoDBObject.newBuilder
    builder += "firstName" -> customer.firstName
    builder += "lastName" -> customer.lastName
    builder += "phoneNumber" -> customer.phoneNumber.getOrElse("")
    builder += "address" -> customer.address.getOrElse("")
    builder += "city" -> customer.city.get
    builder += "country" -> customer.country.get
    builder += "zipcode" -> customer.zipcode.getOrElse("")
    builder.result
  }
}