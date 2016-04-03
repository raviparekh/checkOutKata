import com.raviparekh.engine.PricingEngine

class Checkout(private val pricingEngine: PricingEngine) {

  def scan(productSkus: List[String]): Double = {

    pricingEngine.getTotalPriceFor(productSkus)

  }
}
