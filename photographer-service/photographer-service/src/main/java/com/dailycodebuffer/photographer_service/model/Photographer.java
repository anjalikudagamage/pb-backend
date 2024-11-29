package com.dailycodebuffer.photographer_service.model;

import jakarta.persistence.*;
import java.util.Map;

@Entity
@Table(name="photographer_info")
public class Photographer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String businessName;
    private String businessDescription;

    @ElementCollection
    @CollectionTable(name = "package_details", joinColumns = @JoinColumn(name = "photographer_id"))
    @MapKeyColumn(name = "package_name")
    @Column(name = "details")
    private Map<String, String> packageDetails;

    // Constructors, Getters, and Setters
    public Photographer() {}

    public Photographer(Long id, String businessName, String businessDescription, String email, String password, Map<String, String> packageDetails) {
        this.id = id;
        this.businessName = businessName;
        this.businessDescription = businessDescription;
        this.email = email;
        this.password = password;
        this.packageDetails = packageDetails;
    }

    // Getters and Setters for all fields

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusinessDescription() {
        return businessDescription;
    }

    public void setBusinessDescription(String businessDescription) {
        this.businessDescription = businessDescription;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, String> getPackageDetails() {
        return packageDetails;
    }

    public void setPackageDetails(Map<String, String> packageDetails) {
        this.packageDetails = packageDetails;
    }
}

