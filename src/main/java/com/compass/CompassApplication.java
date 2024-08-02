package com.compass;

import com.compass.model.Contact;
import com.compass.model.Match;
import com.compass.service.CsvReaderService;
import com.compass.service.MatchService;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class CompassApplication {
    public static void main(String[] args) {
        String filePath = "src/main/resources/duplicates_input.csv";
        List<Contact> contacts = CsvReaderService.readContactsFromCsv(filePath);

        List<Match> matches = MatchService.findPotentialMatches(contacts);
        matches.forEach(result ->
                System.out.printf("ContactID Source: %d, ContactID Match: %d, Accuracy: %s%n",
                        result.getSourceId(), result.getMatchId(), result.getAccuracy()));
    }
}
