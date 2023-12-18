package com.practice.level3;

import java.util.Map;

class Document {

    private final Map<String, String> attributes;

    Document(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    String getAttribute(final String attributeName) {
        return attributes.get(attributeName);
    }

    @Override
    public String toString() {
        return "Document{" +
                "attributes=" + attributes +
                '}';
    }
}
