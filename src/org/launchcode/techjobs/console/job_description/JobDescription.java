package org.launchcode.techjobs.console.job_description;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

public class JobDescription {
    private String name;
    private String location;
    private String employer;
    private String positionType; // maps to "position type"
    private String coreCompetency; // maps to "core competency"

    public String getName() { return name; }
    public String getLocation() { return location; }
    public String getEmployer() { return employer; }
    public String getPositionType() { return positionType; }
    public String getCoreCompetency() { return coreCompetency; }


    /**
     * @apiNote
     *  Maps a String field name to its corresponding getter method
     *  Provides dynamic access to getter methods
     *
     * @param {String} JobDescription field
     * @throws ValueException for invalid field name argument
     * @return {Method} getter method corresponding to field name
     */
    public String getField (String field) {
        // dynamic map of string to getter method
        switch(field) {
            case "name": return this.getName();
            case "employer": return this.getEmployer();
            case "location": return this.getLocation();
            case "core competency": return this.getCoreCompetency();
            case "position type": return this.getPositionType();
            default:
                throw new ValueException("Invalid field requested");
        }
    }

    @Override
    public String toString() {
        return String.format(
            "\nName: %s\nEmployer: %s\nLocation: %s\nPosition Type: %s\nCore Competency: %s\n",
            this.name,
            this.employer,
            this.location,
            this.positionType,
            this.coreCompetency
        );
    }

    public JobDescription(
        String name,
        String location,
        String employer,
        String positionType,
        String coreCompetency
    ) {
        this.name = name;
        this.location = location;
        this.employer = employer;
        this.positionType = positionType;
        this.coreCompetency = coreCompetency;
    }
}
