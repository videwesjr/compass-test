package service;
import com.compass.model.Contact;
import com.compass.service.CsvReaderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class CsvReaderServiceTest {
    @Test
    public void testReadContactsFromCsv(@TempDir Path tempDir) throws IOException {
        // Prepare CSV content
        String csvContent = "ContactID,FirstName,LastName,Email,ZipCode,Address\n" +
                "1001,John,Doe,john.doe@example.com,12345,123 Elm St\n" +
                "1002,Jane,Smith,jane.smith@example.com,54321,456 Oak St";

        // Create a temporary file with the CSV content
        Path csvFile = tempDir.resolve("contacts.csv");
        Files.write(csvFile, csvContent.getBytes());

        // Read contacts from the temporary CSV file
        List<Contact> contacts = CsvReaderService.readContactsFromCsv(csvFile.toString());

        // Validate the results
        assertEquals(2, contacts.size());
        assertEquals(new Contact(1001, "John", "Doe", "john.doe@example.com", "12345", "123 Elm St"), contacts.get(0));
        assertEquals(new Contact(1002, "Jane", "Smith", "jane.smith@example.com", "54321", "456 Oak St"), contacts.get(1));
    }

    @Test
    public void testReadContactsFromEmptyCsv(@TempDir Path tempDir) throws IOException {
        // Create an empty CSV file
        Path csvFile = tempDir.resolve("empty_contacts.csv");
        Files.write(csvFile, "ContactID,FirstName,LastName,Email,ZipCode,Address\n".getBytes());

        // Read contacts from the empty CSV file
        List<Contact> contacts = CsvReaderService.readContactsFromCsv(csvFile.toString());

        // Validate the results
        assertTrue(contacts.isEmpty());
    }
}
