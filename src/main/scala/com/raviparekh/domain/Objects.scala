package com.raviparekh.domain

trait PricingRule {
  def calculatePrice(productQuantity: Int): Double
}

class Basket(private var items: List[String]) {

  def addItem(item: String): Unit = {
    items = item :: items
  }

  def remove(item: String): Unit = {
    items = items diff Seq(item)
  }

  def placeOnTil: List[String] = items

}


class StandardPricing(val unitPrice: Double) extends PricingRule {

  override def calculatePrice(productQuantity: Int): Double = {
    unitPrice * productQuantity
  }

}


class MultiBuyPricing(val specialPrice: Double, val regularPrice: Double, val offerMinQuantity: Int) extends PricingRule {

  override def calculatePrice(productQuantity: Int): Double ={
    val quantityOutsideOffer = productQuantity % offerMinQuantity
    val quantityInsideOffer = productQuantity - quantityOutsideOffer


    (specialPrice * (quantityInsideOffer/offerMinQuantity)) + (regularPrice * quantityOutsideOffer)
  }
}