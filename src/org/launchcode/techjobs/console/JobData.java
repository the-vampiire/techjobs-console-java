package org.launchcode.techjobs.console;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.launchcode.techjobs.console.job_description.FieldComparator;
import org.launchcode.techjobs.console.job_description.JobDescription;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

/**
 * Created by LaunchCode
 */
public class JobData {

    private static final String DATA_FILE = "resources/job_data.csv";
    private static Boolean isDataLoaded = false;

    private static ArrayList<JobDescription> allJobs;

    /**
     * Fetch list of all values from loaded data,
     * without duplicates, for a given column.
     *
     * @param field The column to retrieve values from
     * @return List of all of the values of the given field
     */
    public static ArrayList<String> findAll(String field) {

        // load data, if not already loaded
        loadData();

        ArrayList<String> values = new ArrayList<>();

        for (JobDescription job : allJobs) {
            String jobValue = job.getField(field);
            if (!values.contains(jobValue)) {
                values.add(jobValue);
            }
        }
        values.sort(null);

        return values;
    }

    public static ArrayList<JobDescription> findAll() {

        // load data, if not already loaded
        loadData();

        return allJobs;
    }

    /**
     * Returns results of search the jobs data by key/value, using
     * inclusion of the search term.
     *
     * For example, searching for employer "Enterprise" will include results
     * with "Enterprise Holdings, Inc".
     *
     * @param column   Column that should be searched.
     * @param value Value of teh field to search for
     * @return List of all jobs matching the criteria
     */
    public static ArrayList<JobDescription> findByColumnAndValue(String column, String value) {

        // load data, if not already loaded
        loadData();

        ArrayList<JobDescription> jobs = new ArrayList<>();

        for (JobDescription job : allJobs) {
            String jobValue = job.getField(column);
            if (jobValue.contains(value)) {
                jobs.add(job);
            }
        }

        return jobs;
    }

    public static ArrayList<JobDescription> findbyTerm(String term) {

        // load data, if not already loaded
        loadData();

        ArrayList<JobDescription> results = new ArrayList<>();

        String[] columns = {
            "name",
            "employer",
            "location",
            "core competency",
            "position type"
        };

        for (JobDescription job : allJobs) {
            for (String column: columns) {
                String columnValue = job.getField(column).toLowerCase();
                String searchTerm = term.toLowerCase();

                if (
                    columnValue.contains(searchTerm) &&
                    !results.contains(job)
                ) { results.add(job); }
            }
        }
        return results;
    }

    // sort by column
    public static ArrayList<JobDescription> sortResults(ArrayList<JobDescription> results, String column) {
        ArrayList<JobDescription> copy = new ArrayList<>(results);
        Collections.sort(copy, new FieldComparator(column));
        return copy;
    }

    // default sort by "name" field
    public static ArrayList<JobDescription> sortResults(ArrayList<JobDescription> results) {
        ArrayList<JobDescription> copy = new ArrayList<>(results);
        Collections.sort(copy, new FieldComparator());
        return copy;
    }

    /**
     * Read in data from a CSV file and store it in a list
     */
    private static void loadData() {

        // Only load data once
        if (isDataLoaded) { return; }

        try {

            // Open the CSV file and set up pull out column header info and records
            Reader in = new FileReader(DATA_FILE);
            CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
            List<CSVRecord> records = parser.getRecords();
            Integer numberOfColumns = records.get(0).size();
            String[] headers = parser.getHeaderMap().keySet().toArray(new String[numberOfColumns]);

            allJobs = new ArrayList<>();

            // Put the records into a more friendly format
            for (CSVRecord record : records) {
                JobDescription newJob = new JobDescription(
                    record.get("name"),
                    record.get("location"),
                    record.get("employer"),
                    record.get("position type"),
                    record.get("core competency")
                );

                allJobs.add(newJob);
            }

            // flag the data as loaded, so we don't do it twice
            isDataLoaded = true;

        } catch (IOException e) {
            System.out.println("Failed to load job data");
            e.printStackTrace();
        }
    }

}
