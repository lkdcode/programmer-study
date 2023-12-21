package com.level5;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InspectorTest {

    @Test
    void inspectOneConditionEvaluatesTrue() {

        final Facts facts = new Facts();
        facts.addFact("jobTitle", "CEO");
        final ConditionalAction conditionAction = new JobTitleCondition();

        final Inspector inspector = new Inspector(conditionAction);

        final List<Diagnosis> diagnosisList = inspector.inspect(facts);

        assertEquals(1, diagnosisList.size());
        assertTrue(diagnosisList.get(0).isPositive());
    }

    private static class JobTitleCondition implements ConditionalAction {
        @Override
        public void perform(Facts facts) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean evaluate(Facts facts) {
            return "CEO".equals(facts.getFact("jobTitle"));
        }
    }

}