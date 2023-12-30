package com.practice.level3;

import com.practice.level2.ResultData;

import java.io.File;
import java.io.IOException;

class App {
    private static final String RESOURCES = "../spring-boot-practice/real-world-software-development/src/main/resources/level3/";

    public static void main(String[] args) throws IOException {
        final String invoice = "patient.invoice";
        final String letter = "patient.letter";
        final String report = "patient.report";
        final String image = "xray.jpg";

        Importer invoiceImporter = new InvoiceImporter();
        Importer letterImporter = new LetterImporter();
        Importer reportImporter = new ReportImporter();
        Importer imageImporter = new ImageImporter();

        Document invoiceDocument = invoiceImporter.importFile(new File(RESOURCES + invoice));
        Document letterDocument = letterImporter.importFile(new File(RESOURCES + letter));
        Document reportDocument = reportImporter.importFile(new File(RESOURCES + report));
        Document imageDocument = imageImporter.importFile(new File(RESOURCES + image));

        System.out.println("===========");
        System.out.println(invoiceDocument);
        System.out.println("===========");
        System.out.println(letterDocument);
        System.out.println("===========");
        System.out.println(reportDocument);
        System.out.println("===========");
        System.out.println(imageDocument);
        System.out.println("===========");
    }
}
