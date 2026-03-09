package com.example.tickets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * INTENTION: A ticket should be an immutable record-like object.
 *
 * CURRENT STATE (BROKEN ON PURPOSE):
 * - mutable fields
 * - multiple constructors
 * - public setters
 * - tags list can be modified from outside
 * - validation is scattered elsewhere
 *
 * TODO (student): refactor to immutable + Builder.
 */
public final class IncidentTicket {

    private final String id;
    private final String reporterEmail;
    private final String title;

    private final String description;
    private final String priority;       // LOW, MEDIUM, HIGH, CRITICAL
    private final List<String> tags;     // mutable leak
    private final String assigneeEmail;
    private final boolean customerVisible;
    private final Integer slaMinutes;    // optional
    private final String source;         // e.g. "CLI", "WEBHOOK", "EMAIL"

    private IncidentTicket(Builder b) {
        // this.tags = new ArrayList<>();
        this.id = b.id;
        this.reporterEmail = b.reporterEmail;
        this.title = b.title;
        this.description = b.description;
        this.priority = b.priority;
        this.tags = Collections.unmodifiableList(new ArrayList<>(b.tags)); 
        this.assigneeEmail = b.assigneeEmail;
        this.customerVisible = b.customerVisible;
        this.slaMinutes = b.slaMinutes;
        this.source = b.source;
    }

    // public IncidentTicket(String id, String reporterEmail, String title) {
    //     this();
    //     this.id = id;
    //     this.reporterEmail = reporterEmail;
    //     this.title = title;
    // }

    // public IncidentTicket(String id, String reporterEmail, String title, String priority) {
    //     this(id, reporterEmail, title);
    //     this.priority = priority;
    // }

    // Getters
    public String getId() { return id; }
    public String getReporterEmail() { return reporterEmail; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getPriority() { return priority; }
    public List<String> getTags() { return tags; } // BROKEN: leaks internal list
    public String getAssigneeEmail() { return assigneeEmail; }
    public boolean isCustomerVisible() { return customerVisible; }
    public Integer getSlaMinutes() { return slaMinutes; }
    public String getSource() { return source; }

    // // Setters (BROKEN: should not exist after refactor)
    // public void setId(String id) { this.id = id; }
    // public void setReporterEmail(String reporterEmail) { this.reporterEmail = reporterEmail; }
    // public void setTitle(String title) { this.title = title; }
    // public void setDescription(String description) { this.description = description; }
    // public void setPriority(String priority) { this.priority = priority; }
    // public void setTags(List<String> tags) { this.tags = tags; } // BROKEN: retains external reference
    // public void setAssigneeEmail(String assigneeEmail) { this.assigneeEmail = assigneeEmail; }
    // public void setCustomerVisible(boolean customerVisible) { this.customerVisible = customerVisible; }
    // public void setSlaMinutes(Integer slaMinutes) { this.slaMinutes = slaMinutes; }
    // public void setSource(String source) { this.source = source; }

    public Builder toBuilder() {
        Builder b = new Builder(this.id, this.reporterEmail, this.title);
        b.description(this.description);
        b.priority(this.priority);
        b.tags(new ArrayList<>(this.tags));
        b.assigneeEmail(this.assigneeEmail);
        b.customerVisible(this.customerVisible);
        b.slaMinutes(this.slaMinutes);
        b.source(this.source);
        return b;
    }
    


    @Override
    public String toString() {
        return "IncidentTicket{" +
                "id='" + id + '\'' +
                ", reporterEmail='" + reporterEmail + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", priority='" + priority + '\'' +
                ", tags=" + tags +
                ", assigneeEmail='" + assigneeEmail + '\'' +
                ", customerVisible=" + customerVisible +
                ", slaMinutes=" + slaMinutes +
                ", source='" + source + '\'' +
                '}';
    }


        // BUILDER 
        public static class Builder {

        private final String id;
        private final String reporterEmail;
        private final String title;

        private String description = null;
        private String priority = "LOW";
        private List<String> tags = new ArrayList<>();
        private String assigneeEmail = null;
        private boolean customerVisible = false;
        private Integer slaMinutes = null;
        private String source = null;

        public Builder(String id, String reporterEmail, String title) {
            this.id = id;
            this.reporterEmail = reporterEmail;
            this.title = title;
        }

        public Builder description(String val) { this.description = val; return this; }
        public Builder priority(String val) { this.priority = val; return this; }
        public Builder tags(List<String> val) { this.tags = (val != null) ? new ArrayList<>(val) : new ArrayList<>(); return this; }
        public Builder assigneeEmail(String val) { this.assigneeEmail = val; return this; }
        public Builder customerVisible(boolean val) { this.customerVisible = val; return this; }
        public Builder slaMinutes(Integer val) { this.slaMinutes = val; return this; }
        public Builder source(String val) { this.source = val; return this; }

        public IncidentTicket build() {
            Validation.requireTicketId(id);

            Validation.requireEmail(reporterEmail, "reporterEmail");

            Validation.requireNonBlank(title, "title");
            Validation.requireMaxLen(title, 80, "title");

            Validation.requireOneOf(priority, "priority", "LOW", "MEDIUM", "HIGH", "CRITICAL");

            if (assigneeEmail != null) {
                Validation.requireEmail(assigneeEmail, "assigneeEmail");
            }

            Validation.requireRange(slaMinutes, 5, 7200, "slaMinutes");

            return new IncidentTicket(this);
        }
    }
}
