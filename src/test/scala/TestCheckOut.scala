
import com.raviparekh.domain.{Basket, StandardPricing, MultiBuyPricing}
import com.raviparekh.engine.StandardPricingEngine
import org.scalatest.FunSuite

class TestCheckOut extends FunSuite {
  val productPricings = Map(
    "A" -> new MultiBuyPricing(specialPrice = 130, regularPrice = 50, offerMinQuantity = 3),
    "B" -> new MultiBuyPricing(specialPrice = 45, regularPrice = 30, offerMinQuantity = 2),
    "C" -> new StandardPricing(unitPrice = 20),
    "D" -> new StandardPricing(unitPrice = 15)
  )
  val DefaultPricingEngine = new StandardPricingEngine(productPricings)
  val checkoutTil = new Checkout(DefaultPricingEngine)

  test("Basket with 2 A items should total to 100.0") {
    val basket = new Basket(List("A", "A"))

    assert(checkoutTil.scan(basket.placeOnTil) == 100.0)
  }

  test("Basket with 3 A items should total to a discounted 130") {
    val basket = new Basket(List("A", "A", "A"))

    assert(checkoutTil.scan(basket.placeOnTil) == 130.0)
  }

  test("Basket with 6 A items should total to a discounted 260") {
    val basket = new Basket(List("A", "A", "A", "A", "A", "A"))

    assert(checkoutTil.scan(basket.placeOnTil) == 260.0)
  }

  test("Basket with 3 A items and 2 B should total sum of discounted price of 175") {
    val basket = new Basket(List("A", "A", "A", "B", "B"))

    assert(checkoutTil.scan(basket.placeOnTil) == 175.0)
  }

  test("Basket with 3 A items and 2 C should total sum of discounted for A and regular price for C of 170") {
    val basket = new Basket(List("A", "A", "A", "C", "C"))

    assert(checkoutTil.scan(basket.placeOnTil) == 170.0)
  }

  test("Basket all items should price to 210") {
    val basket = new Basket(List("A", "A", "A", "B", "B", "C", "D"))

    assert(checkoutTil.scan(basket.placeOnTil) == 210.0)
  }

  test("Invalid SKU should raise exception") {
    intercept[RuntimeException]{
      checkoutTil.scan(List("T"))
    }
  }
}
