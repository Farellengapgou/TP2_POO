package LOST;

import java.sql.Date;

public class Lost_items {
    private String name;
    private String serialNumber;
    private String description;
    private Date dateReported;   // Date du signalement
    private int reporterId;      // ID de la personne qui signale


    public Lost_items(String name, String serialNumber, String description, Date dateReported, int reporterId) {
        this.name = name;
        this.serialNumber = serialNumber;
        this.description = description;
        this.dateReported = dateReported;
        this.reporterId = reporterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateReported() {
        return dateReported;
    }

    public void setDateReported(Date dateReported) {
        this.dateReported = dateReported;
    }

    public int getReporterId() {
        return reporterId;
    }

    public void setReporterId(int reporterId) {
        this.reporterId = reporterId;
    }
}
