package com.example.model

case class Customer(firstName: String,
  lastName: String,
  _id: Option[String] = None,
  phoneNumber: Option[String] = None,
  address: Option[String] = None,
  city: Option[String] = Some("New York"),
  country: Option[String] = Some("USA"),
  zipcode: Option[String] = None) {
}