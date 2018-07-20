package org.launchcode.techjobs.console.job_description;

import java.util.Comparator;

public class FieldComparator implements Comparator<JobDescription> {
    // Construct by passing the type to sort by
    // defaults to "name" field
    private String type;

    public FieldComparator() {
        this.type = "name";
    } // default to sort by "name"
    public FieldComparator(String type) {
        this.type = type;
    }

    public int compare(JobDescription jobOne, JobDescription jobTwo) {
        // compares any two String fields using the dynamic string -> getter map: getField()
        return jobOne.getField(this.type).compareTo(jobTwo.getField(this.type));
    }
}


