package com.shop.pojo;

public class Notice {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notice.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notice.user_id
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notice.create_at
     *
     * @mbggenerated
     */
    private String createAt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notice.status
     *
     * @mbggenerated
     */
    private Byte status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notice.context
     *
     * @mbggenerated
     */
    private String context;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notice
     *
     * @mbggenerated
     */
    public Notice(Integer id, Integer userId, String createAt, Byte status, String context) {
        this.id = id;
        this.userId = userId;
        this.createAt = createAt;
        this.status = status;
        this.context = context;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notice
     *
     * @mbggenerated
     */
    public Notice() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notice.id
     *
     * @return the value of notice.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notice.id
     *
     * @param id the value for notice.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notice.user_id
     *
     * @return the value of notice.user_id
     *
     * @mbggenerated
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notice.user_id
     *
     * @param userId the value for notice.user_id
     *
     * @mbggenerated
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notice.create_at
     *
     * @return the value of notice.create_at
     *
     * @mbggenerated
     */
    public String getCreateAt() {
        return createAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notice.create_at
     *
     * @param createAt the value for notice.create_at
     *
     * @mbggenerated
     */
    public void setCreateAt(String createAt) {
        this.createAt = createAt == null ? null : createAt.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notice.status
     *
     * @return the value of notice.status
     *
     * @mbggenerated
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notice.status
     *
     * @param status the value for notice.status
     *
     * @mbggenerated
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notice.context
     *
     * @return the value of notice.context
     *
     * @mbggenerated
     */
    public String getContext() {
        return context;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notice.context
     *
     * @param context the value for notice.context
     *
     * @mbggenerated
     */
    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
    }
}