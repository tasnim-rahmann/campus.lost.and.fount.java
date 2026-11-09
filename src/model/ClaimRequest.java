package model;

import java.io.Serializable;

public class ClaimRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String claimId;
    private final String itemId;
    private final String studentId;
    private final String studentName;
    private final String message;
    private String status;

    public ClaimRequest(String claimId, String itemId, String studentId,
                        String studentName, String message) {
        this.claimId = claimId;
        this.itemId = itemId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.message = message;
        this.status = "Pending";
    }
    public String getClaimId() { return claimId; }
    public String getItemId() { return itemId; }
    public String getStudentId() { return studentId; }
    public String getStudentName() { return studentName; }
    public String getMessage() { return message; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
