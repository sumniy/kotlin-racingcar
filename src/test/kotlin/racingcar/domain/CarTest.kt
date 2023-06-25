package racingcar.domain

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import racingcar.domain.Position.Companion.DEFAULT_POSITION
import racingcar.strategy.ManualStrategy

class CarTest {
    @Test
    fun `Car의 기본 positon은 1이다`() {
        val name = "test1"
        val car = Car(CarName(name))

        car.position.value shouldBe DEFAULT_POSITION
    }

    @Test
    fun `condition이 true 이면 Car의 position이 1 증가한다`() {
        val name = "test1"
        val car = Car(name = CarName(name), moveStrategy = ManualStrategy(true))
        car.move()

        car.position.value shouldBe DEFAULT_POSITION + 1
    }

    @Test
    fun `condition이 false 이면 Car의 position은 변하지 않는다`() {
        val name = "test1"
        val car = Car(name = CarName(name), moveStrategy = ManualStrategy(false))
        car.move()

        car.position.value shouldBe DEFAULT_POSITION
    }
}
