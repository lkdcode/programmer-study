package com.practice.level3;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static com.practice.level3.Attributes.*;

class LetterImporter implements Importer {
    @Override
    public Document importFile(File file) throws IOException {
        TextFile textFile = new TextFile(file);

        final int lineNumber = textFile.addLines(2, String::isEmpty, LETTER);

        textFile.addLines(lineNumber + 1, line -> line.startsWith(REGARDS), BODY);

        Map<String, String> attributes = textFile.getAttributes();
        attributes.put(TYPE, LETTER);
        return new Document(attributes);
    }
}
