package com.go_stop.rule;

import com.go_stop.hwatoo.HwaToo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static com.go_stop.hwatoo.HwaToo.*;
import static org.assertj.core.api.Assertions.assertThat;

class GoStopRuleTest {

    private GoStopRule goStopRule;

    @BeforeEach
    void setGoStopRule() {
        this.goStopRule = new GoStopRule();
    }

    @ParameterizedTest
    @MethodSource("animalCardData")
    void calculateAnimalCardTest(final List<HwaToo> list, final int expectedPoint) {
        final int point = goStopRule.calculateAnimalCard(list).process();

        assertThat(point)
                .isEqualTo(expectedPoint);
    }

    private static Stream<Arguments> animalCardData() {
        return Stream.of(
                Arguments.of(Arrays.asList(매화열, 등꽃열, 공산열), 5)
                , Arguments.of(Arrays.asList(매화열, 등꽃열, 공산열, 모란열, 흑싸리열, 단풍열, 비열), 8)
                , Arguments.of(Arrays.asList(매화열, 모란열, 흑싸리열, 단풍열, 비열), 1)
                , Arguments.of(Arrays.asList(등꽃열, 공산열, 모란열, 흑싸리열, 단풍열), 1)
                , Arguments.of(Arrays.asList(등꽃열, 공산열), 0)
                , Arguments.of(Arrays.asList(비열), 0)
        );
    }

    @ParameterizedTest
    @MethodSource("lightCardData")
    void calculateLightTest(final List<HwaToo> list, final int expectedPoint) {
        final int point = goStopRule.calculateLight(list).process();

        assertThat(point)
                .isEqualTo(expectedPoint);
    }

    private static Stream<Arguments> lightCardData() {
        return Stream.of(
                Arguments.of(Arrays.asList(송학광, 벚꽃광, 공산광), 3)
                , Arguments.of(Arrays.asList(비광, 오동광, 공산광), 2)
                , Arguments.of(Arrays.asList(송학광, 벚꽃광, 공산광, 오동광, 비광), 15)
                , Arguments.of(Arrays.asList(오동광, 벚꽃광, 공산광), 3)
                , Arguments.of(Arrays.asList(오동광, 벚꽃광, 공산광, 비광), 4)
                , Arguments.of(Arrays.asList(오동광), 0)
                , Arguments.of(Arrays.asList(벚꽃광), 0)
                , Arguments.of(Arrays.asList(공산광), 0)
                , Arguments.of(Arrays.asList(비광), 0)
                , Arguments.of(Arrays.asList(비광, 공산광), 0)
                , Arguments.of(Arrays.asList(비광, 오동광), 0)
                , Arguments.of(Arrays.asList(), 0)
        );
    }

    @ParameterizedTest
    @MethodSource("lineCardData")
    void calculateLineCardsTest(final List<HwaToo> list, final int expectedPoint) {
        final int point = goStopRule.calculateLineCards(list).process();

        assertThat(point)
                .isEqualTo(expectedPoint);
    }

    private static Stream<Arguments> lineCardData() {
        return Stream.of(
                Arguments.of(Arrays.asList(송학띠, 매화띠, 벚꽃띠), 3)
                , Arguments.of(Arrays.asList(모란띠, 국화띠, 단풍띠), 3)
                , Arguments.of(Arrays.asList(등꽃띠, 난초띠, 흑싸리띠), 3)
                , Arguments.of(Arrays.asList(송학띠, 모란띠, 등꽃띠, 난초띠, 비띠), 1)
                , Arguments.of(Arrays.asList(), 0)
                , Arguments.of(Arrays.asList(등꽃띠, 난초띠, 흑싸리띠), 3)
                , Arguments.of(Arrays.asList(등꽃띠, 난초띠), 0)
                , Arguments.of(Arrays.asList(송학띠, 매화띠, 벚꽃띠, 모란띠, 국화띠, 단풍띠, 등꽃띠, 난초띠, 흑싸리띠, 비띠), 15)
                , Arguments.of(Arrays.asList(송학띠, 매화띠, 모란띠, 국화띠, 등꽃띠, 난초띠, 비띠), 3)
                , Arguments.of(Arrays.asList(송학띠, 매화띠, 벚꽃띠, 모란띠, 국화띠, 단풍띠, 등꽃띠, 난초띠, 흑싸리띠), 14)
                , Arguments.of(Arrays.asList(송학띠, 매화띠, 벚꽃띠, 국화띠, 단풍띠, 비띠, 흑싸리띠), 6)
        );
    }

    @ParameterizedTest
    @MethodSource("normalCardData")
    void calculateNormalCardsTest(final List<HwaToo> list, final int expectedPoint) {
        final int point = goStopRule.calculateNormalCards(list).process();

        assertThat(point)
                .isEqualTo(expectedPoint);
    }

    private static Stream<Arguments> normalCardData() {
        return Stream.of(
                Arguments.of(Arrays.asList(송학피1, 송학피2, 매화피1, 매화피2, 등꽃피1, 등꽃피2, 난초피1, 모란피1, 모란피2), 0)
                , Arguments.of(Arrays.asList(송학피1, 송학피2, 매화피1, 매화피2, 등꽃피1, 등꽃피2, 난초피1, 모란피1, 모란피2, 오동피1), 1)
                , Arguments.of(Arrays.asList(송학피1, 송학피2, 매화피1, 매화피2, 벚꽃피1, 벚꽃피2, 등꽃피1, 등꽃피2, 난초피1, 난초피2, 모란피1, 모란피2), 3)
                , Arguments.of(Arrays.asList(송학피1, 송학피2, 매화피1, 매화피2, 벚꽃피1, 벚꽃피2, 등꽃피1, 등꽃피2, 난초피1, 난초피2, 모란피1, 모란피2), 3)
                , Arguments.of(Arrays.asList(송학피1, 송학피2, 매화피1, 매화피2, 벚꽃피1, 벚꽃피2, 등꽃피1, 등꽃피2, 난초피1, 난초피2, 모란피1, 모란피2), 3)
        );
    }

}