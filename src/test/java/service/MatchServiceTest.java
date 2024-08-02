package service;

import com.compass.model.Contact;
import com.compass.model.Match;
import com.compass.service.MatchService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatchServiceTest {
    @Test
    public void testFindPotentialMatchesHigh() {
        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact(1001, "John", "Doe", "john.doe@example.com", "12345", "123 Elm St"));
        contacts.add(new Contact(1002, "John", "Doe", "john.doe@example.com", "12345", "123 Elm St"));
        contacts.add(new Contact(1003, "Jane", "Smith", "jane.smith@example.com", "54321", "456 Oak St"));

        List<Match> results = MatchService.findPotentialMatches(contacts);

        assertEquals(1, results.size());
        assertEquals(1001, results.get(0).getSourceId());
        assertEquals(1002, results.get(0).getMatchId());
        assertEquals("High", results.get(0).getAccuracy());
    }

    @Test
    public void testFindPotentialMatchesLow() {
        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact(1001, "John", "Doe", "john.doe@example.com", "12345", "123 Elm St"));
        contacts.add(new Contact(1002, "J", "D", "john.doe@example.com", "321", "123"));

        List<Match> results = MatchService.findPotentialMatches(contacts);

        assertEquals(1, results.size());
        assertEquals(1001, results.get(0).getSourceId());
        assertEquals(1002, results.get(0).getMatchId());
        assertEquals("Low", results.get(0).getAccuracy());
    }

    @Test
    public void testFindPotentialMatchesMedium() {
        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact(1001, "J", "D", "different.email@example.com", "", "123 Elm St"));
        contacts.add(new Contact(1002, "John", "Doe", "different.email@example.com", "12345", "123 Elm St"));

        List<Match> results = MatchService.findPotentialMatches(contacts);

        assertEquals(1, results.size());
        assertEquals(1001, results.get(0).getSourceId());
        assertEquals(1002, results.get(0).getMatchId());
        assertEquals("Medium", results.get(0).getAccuracy());
    }

    @Test
    public void testExactMatch() {
        Contact c1 = new Contact(1, "John", "Doe", "john.doe@example.com", "12345", "123 Elm St");
        Contact c2 = new Contact(2, "John", "Doe", "john.doe@example.com", "12345", "123 Elm St");

        int score = MatchService.getMatchScore(c1, c2);

        assertEquals(5, score); // Expect full score for exact match
    }

    @Test
    public void testPartialMatch() {
        Contact c1 = new Contact(1, "John", "Doe", "john.doe@example.com", "12345", "123 Elm St");
        Contact c2 = new Contact(2, "John", "Doe", "john.doe@example.com", "", "");

        int score = MatchService.getMatchScore(c1, c2);

        assertEquals(3, score); // Expect partial match score
    }

    @Test
    public void testNoMatch() {
        Contact c1 = new Contact(1, "John", "Doe", "john.doe@example.com", "12345", "123 Elm St");
        Contact c2 = new Contact(2, "Jane", "Smith", "jane.smith@example.com", "54321", "456 Oak St");

        int score = MatchService.getMatchScore(c1, c2);

        assertEquals(0, score); // Expect no match score
    }

    @Test
    public void testSingleCharacterName() {
        Contact c1 = new Contact(1, "J", "D", "", "", "");
        Contact c2 = new Contact(2, "J", "D", "", "", "");

        int score = MatchService.getMatchScore(c1, c2);

        assertEquals(1, score); // Check partial match on single character names
    }

    @Test
    public void testDifferentCaseMatch() {
        Contact c1 = new Contact(1, "john", "doe", "JOHN.DOE@EXAMPLE.COM", "12345", "123 Elm St");
        Contact c2 = new Contact(2, "John", "Doe", "john.doe@example.com", "12345", "123 Elm St");

        int score = MatchService.getMatchScore(c1, c2);

        assertEquals(5, score); // Expect full score with case insensitivity
    }
}
