package com.thesocialcoin.models.pojos;

/**
 * Created by identitat on 13/11/14.
 */
import com.google.gson.annotations.Expose;

public class User extends iPojo {

    @Expose
    private String realm;
    @Expose
    private String username;
    @Expose
    private String credentials;
    @Expose
    private String challenges;
    @Expose
    private String email;
    @Expose
    private String emailVerified;
    @Expose
    private String verificationToken;
    @Expose
    private String status;
    @Expose
    private String created;
    @Expose
    private String lastUpdated;
    @Expose
    private String id;
    @Expose
    private String projectId;

    /**
     *
     * @return
     * The realm
     */
    public String getRealm() {
        return realm;
    }

    /**
     *
     * @param realm
     * The realm
     */
    public void setRealm(String realm) {
        this.realm = realm;
    }

    /**
     *
     * @return
     * The username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     * The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     * The credentials
     */
    public String getCredentials() {
        return credentials;
    }

    /**
     *
     * @param credentials
     * The credentials
     */
    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    /**
     *
     * @return
     * The challenges
     */
    public String getChallenges() {
        return challenges;
    }

    /**
     *
     * @param challenges
     * The challenges
     */
    public void setChallenges(String challenges) {
        this.challenges = challenges;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The emailVerified
     */
    public String getEmailVerified() {
        return emailVerified;
    }

    /**
     *
     * @param emailVerified
     * The emailVerified
     */
    public void setEmailVerified(String emailVerified) {
        this.emailVerified = emailVerified;
    }

    /**
     *
     * @return
     * The verificationToken
     */
    public String getVerificationToken() {
        return verificationToken;
    }

    /**
     *
     * @param verificationToken
     * The verificationToken
     */
    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }

    /**
     *
     * @return
     * The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     * The created
     */
    public String getCreated() {
        return created;
    }

    /**
     *
     * @param created
     * The created
     */
    public void setCreated(String created) {
        this.created = created;
    }

    /**
     *
     * @return
     * The lastUpdated
     */
    public String getLastUpdated() {
        return lastUpdated;
    }

    /**
     *
     * @param lastUpdated
     * The lastUpdated
     */
    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The projectId
     */
    public String getProjectId() {
        return projectId;
    }

    /**
     *
     * @param projectId
     * The projectId
     */
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }


}
