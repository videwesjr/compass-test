package com.compass.service;

import com.compass.model.Contact;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvReaderService {
    public static List<Contact> readContactsFromCsv(String filePath) {
        List<Contact> contacts = new ArrayList<>();
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            contacts = lines.skip(1)
                    .map(CsvReaderService::mapToContact)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contacts;
    }

    private static Contact mapToContact(String line) {
        String[] fields = line.split(",");
        return new Contact(
                Integer.parseInt(fields[0].trim()),
                fields[1].trim(),
                fields[2].trim(),
                fields[3].trim(),
                fields[4].trim(),
                fields.length > 5 ? fields[5].trim() : ""
        );
    }
}
