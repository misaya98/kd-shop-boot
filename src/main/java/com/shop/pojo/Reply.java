package com.shop.pojo;

public class Reply {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column reply.id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column reply.user_id
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column reply.atuser_id
     *
     * @mbggenerated
     */
    private Integer atuserId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column reply.commet_id
     *
     * @mbggenerated
     */
    private Integer commetId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column reply.create_at
     *
     * @mbggenerated
     */
    private String createAt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column reply.content
     *
     * @mbggenerated
     */
    private String content;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table reply
     *
     * @mbggenerated
     */
    public Reply(Integer id, Integer userId, Integer atuserId, Integer commetId, String createAt, String content) {
        this.id = id;
        this.userId = userId;
        this.atuserId = atuserId;
        this.commetId = commetId;
        this.createAt = createAt;
        this.content = content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table reply
     *
     * @mbggenerated
     */
    public Reply() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column reply.id
     *
     * @return the value of reply.id
     *
     * @mbggenerated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column reply.id
     *
     * @param id the value for reply.id
     *
     * @mbggenerated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column reply.user_id
     *
     * @return the value of reply.user_id
     *
     * @mbggenerated
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column reply.user_id
     *
     * @param userId the value for reply.user_id
     *
     * @mbggenerated
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column reply.atuser_id
     *
     * @return the value of reply.atuser_id
     *
     * @mbggenerated
     */
    public Integer getAtuserId() {
        return atuserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column reply.atuser_id
     *
     * @param atuserId the value for reply.atuser_id
     *
     * @mbggenerated
     */
    public void setAtuserId(Integer atuserId) {
        this.atuserId = atuserId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column reply.commet_id
     *
     * @return the value of reply.commet_id
     *
     * @mbggenerated
     */
    public Integer getCommetId() {
        return commetId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column reply.commet_id
     *
     * @param commetId the value for reply.commet_id
     *
     * @mbggenerated
     */
    public void setCommetId(Integer commetId) {
        this.commetId = commetId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column reply.create_at
     *
     * @return the value of reply.create_at
     *
     * @mbggenerated
     */
    public String getCreateAt() {
        return createAt;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column reply.create_at
     *
     * @param createAt the value for reply.create_at
     *
     * @mbggenerated
     */
    public void setCreateAt(String createAt) {
        this.createAt = createAt == null ? null : createAt.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column reply.content
     *
     * @return the value of reply.content
     *
     * @mbggenerated
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column reply.content
     *
     * @param content the value for reply.content
     *
     * @mbggenerated
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}