package com.raviparekh.engine

import com.raviparekh.domain.PricingRule

trait PricingEngine {
  def getTotalPriceFor(productsSku: List[String]): Double
}

class StandardPricingEngine(private val prices: Map[String, PricingRule]) extends PricingEngine{

  override def getTotalPriceFor(productsSku: List[String]): Double = {
    val productToQuantity: Map[String, Int] = productsSku.groupBy(identity).mapValues(_.size)
    calculateTotal(productToQuantity)
  }


  private def calculateTotal(products: Map[String, Int]): Double = {
    var total = 0.0
    if (products.keySet subsetOf prices.keySet) {
      for ((sku, quantity) <- products; price <- prices.get(sku)) {
        total += price.calculatePrice(quantity)
      }
    } else {
      throw new RuntimeException("Invalid SKU given")
    }
    total
  }

}
