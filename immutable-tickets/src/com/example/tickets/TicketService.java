package com.example.tickets;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 * Service layer that creates tickets.
 *
 * CURRENT STATE (BROKEN ON PURPOSE):
 * - creates partially valid objects
 * - mutates after creation (bad for auditability)
 * - validation is scattered & incomplete
 *
 * TODO (student):
 * - After introducing immutable IncidentTicket + Builder, refactor this to stop mutating.
 */
public class TicketService {
    
    // public IncidentTicket createTicket(String id, String reporterEmail, String title) {
    //     if (id == null || id.trim().isEmpty()) throw new IllegalArgumentException("id required");
    //     if (reporterEmail == null || !reporterEmail.contains("@")) throw new IllegalArgumentException("email invalid");
    //     if (title == null || title.trim().isEmpty()) throw new IllegalArgumentException("title required");

    //     IncidentTicket t = new IncidentTicket(id, reporterEmail, title);

    //     t.setPriority("MEDIUM");
    //     t.setSource("CLI");
    //     t.setCustomerVisible(false);

    //     List<String> tags = new ArrayList<>();
    //     tags.add("NEW");
    //     t.setTags(tags);

    //     return t;
    // }

    // public void escalateToCritical(IncidentTicket t) {
    //     t.setPriority("CRITICAL");
    //     t.getTags().add("ESCALATED");
    // }

    // public void assign(IncidentTicket t, String assigneeEmail) {
    //     if (assigneeEmail != null && !assigneeEmail.contains("@")) {
    //         throw new IllegalArgumentException("assigneeEmail invalid");
    //     }
    //     t.setAssigneeEmail(assigneeEmail);
    // }





    

    public IncidentTicket createTicket(String id, String reporterEmail, String title) {

        return new IncidentTicket.Builder(id, reporterEmail, title)
                .priority("MEDIUM")
                .source("CLI")
                .customerVisible(false)
                .tags(Arrays.asList("NEW"))
                .build();
    }
 
    public IncidentTicket escalateToCritical(IncidentTicket ticket) {

        List<String> updatedTags = new ArrayList<>(ticket.getTags());
        updatedTags.add("ESCALATED");

        return ticket.toBuilder()
                .priority("CRITICAL")
                .tags(updatedTags)
                .build();
    }

    public IncidentTicket assign(IncidentTicket ticket, String assigneeEmail) {

        return ticket.toBuilder()
                .assigneeEmail(assigneeEmail)
                .build();
    }
}
