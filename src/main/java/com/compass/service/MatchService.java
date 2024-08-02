package com.compass.service;

import com.compass.model.Contact;
import com.compass.model.Match;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchService {

    public static List<Match> findPotentialMatches(List<Contact> contacts) {
        List<Match> results = new ArrayList<>();
        List<Contact> contactsMatched = new ArrayList<>();

        for (int i = 0; i < contacts.size(); i++) {
            Contact c1 = contacts.get(i);
            for (int j = i + 1; j < contacts.size(); j++) {
                if (contactsMatched.contains(contacts.get(j))) {
                    continue; // Skip already matched contacts
                }
                Contact c2 = contacts.get(j);
                int score = getMatchScore(c1, c2);

                String accuracy;
                switch (score) {
                    //If there are more than 3 identical values, the probability of duplicates is high.
                    case  4, 5 -> accuracy = "High";
                    case 3 -> accuracy = "Medium";
                    //If there is only 1 identical value, the chance of it being duplicated is very small.
                    case 2 -> accuracy = "Low";
                    default -> accuracy = "None";
                }

                //After find a potential match, put in contactsMatched for skip
                if (score > 1) {
                    results.add(new Match(c1.getContactId(), c2.getContactId(), accuracy));
                    contactsMatched.add(c2);
                }
            }
        }

        return results;
    }

    public static int getMatchScore(Contact contact, Contact contactCompare) {
        int score = 0;
        //To compare names, I'm using the logic of firstName + lastName as there may be lastNames that are different people.
        if ((contact.getFirstName().equalsIgnoreCase(contactCompare.getFirstName())
                || contact.getFirstName().substring(0, 1).equalsIgnoreCase(contactCompare.getFirstName().substring(0, 1)))
                && (contact.getLastName().equalsIgnoreCase(contactCompare.getLastName())
                || contact.getLastName().substring(0, 1).equalsIgnoreCase(contactCompare.getLastName().substring(0, 1))))
            score++;
        //If the name contains more than 1 letter and has the same firstName and lastName, add 1 to the score
        if (contact.getFirstName().length() > 1
                && contact.getFirstName().equalsIgnoreCase(contactCompare.getFirstName())
                && contact.getLastName().equalsIgnoreCase(contactCompare.getLastName())) score++;
        //Only counts for comparison if the field is filled in
        if (!contact.getEmail().isBlank() && contact.getEmail().equalsIgnoreCase(contactCompare.getEmail())) score++;
        if (!contact.getZipCode().isBlank() && contact.getZipCode().equalsIgnoreCase(contactCompare.getZipCode()))
            score++;
        if (!contact.getAddress().isBlank() && contact.getAddress().equalsIgnoreCase(contactCompare.getAddress()))
            score++;

        return score;
    }
}
