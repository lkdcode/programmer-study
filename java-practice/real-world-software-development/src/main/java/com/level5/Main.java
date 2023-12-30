package com.level5;

class Main {

    public static void main(String[] args) {

        Facts facts = new Facts();
        facts.addFact("name", "Bob");
        facts.addFact("jobTitle", "CEO");

        final BusinessRuleEngine businessRuleEngine = new BusinessRuleEngine(facts);

        final Rule ruleSendEmailToSalesWhenCEO = RuleBuilder
                .when(fact -> "CEO".equals(fact.getFact("jobTitle")))
                .then(fact -> {
                    final String name = fact.getFact("name");
                    System.out.println("Relevant customer!!!: " + name);
                });

        businessRuleEngine.addRule(ruleSendEmailToSalesWhenCEO);
        businessRuleEngine.run();
    }

}
