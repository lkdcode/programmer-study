package chapC;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

class FruitBoxTest {
    private Apple mockApple = mock(Apple.class);
    private Banana mockBanana = mock(Banana.class);

    private FruitBox fruitBox;

    @BeforeEach
    void setFruitBox() {
        this.fruitBox = new FruitBox();
    }

    @Test
    void sizeTest() {
        this.fruitBox.add(this.mockBanana);
        this.fruitBox.add(this.mockApple);

        assertThat(this.fruitBox.size())
                .isEqualTo(2);
    }

    @Test
    void hasTest() {
        this.fruitBox.add(this.mockApple);
        assertThat(this.fruitBox.has(this.mockApple))
                .isTrue();
    }

    @Test
    void addTest() {
        this.fruitBox.add(this.mockApple);
        assertThat(this.fruitBox.size())
                .isEqualTo(1);
    }

    // mock
    @Test
    void hasTest_mock() {
        final FruitBox fakeFruitBox = mock(FruitBox.class);
        given(fakeFruitBox.get(1))
                .willReturn(new Apple("나는 사과"));

        assertThat(fakeFruitBox.get(1).getName())
                .isEqualTo("나는 사과");
    }

    @Test
    void addTest_mock() {
        final FruitBox fakeFruitBox = mock(FruitBox.class);
        given(fakeFruitBox.has(any()))
                .willReturn(true);

        assertThat(fakeFruitBox.has(new Apple("나는 사과")))
                .isTrue();

        assertThat(fakeFruitBox.has(new Banana("나는 바나나")))
                .isTrue();

        assertThat(fakeFruitBox.size())
                .isZero();
    }

    @Test
    void sizeTest_mock() {
        final FruitBox fakeFruitBox = mock(FruitBox.class);
        given(fakeFruitBox.size()).willReturn(99);

        assertThat(fakeFruitBox.size()).isEqualTo(99);

        then(fakeFruitBox).should(only()).size();
        then(fakeFruitBox).should(never()).add(any());
    }

    @Test
    void addTest_mock_captor() {
        final FruitBox fakeFruitBox = mock(FruitBox.class);
        Apple apple = new Apple("애플");
        fakeFruitBox.add(apple);

        ArgumentCaptor<Fruit> argumentCaptor = ArgumentCaptor.forClass(Apple.class);

        then(fakeFruitBox)
                .should()
                .add(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue().getName())
                .isEqualTo(apple.getName());
    }
}
