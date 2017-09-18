package br.com.xbrain.eccp2java.web;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Response {

    private int responseCode;
    private String status;
    private String message;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return The responseCode
     */
    public int getResponseCode() {
        return responseCode;
    }

    /**
     *
     * @param responseCode The response_code
     */
    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    /**
     *
     * @return The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    private int hashCodeCash = -1;

    @Override
    public int hashCode() {
        if (hashCodeCash == -1) {
            hashCodeCash = new HashCodeBuilder()
                    .append(responseCode)
                    .append(status)
                    .append(message)
                    .append(additionalProperties)
                    .toHashCode();
        }
        return hashCodeCash;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Response) == false) {
            return false;
        }
        Response rhs = ((Response) other);
        return new EqualsBuilder().append(responseCode, rhs.responseCode)
                .append(status, rhs.status)
                .append(message, rhs.message)
                .append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
