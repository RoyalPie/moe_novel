package com.evo.common.error;

public enum ServiceUnavailableError implements ResponseError {
    SERVICE_UNAVAILABLE_ERROR(50300001, "Service unavailable"),
    IAM_SERVICE_UNAVAILABLE_ERROR(50300002, "IAM Service unavailable"),
    BUILDING_SERVICE_UNAVAILABLE_ERROR(50300003, "Building Service unavailable"),
    STORAGE_SERVICE_UNAVAILABLE_ERROR(50300004, "Storage Service unavailable"),
    NOTIFICATION_SERVICE_UNAVAILABLE_ERROR(50300005, "Notification Service unavailable"),
    SYSTEM_SERVICE_UNAVAILABLE_ERROR(50300006, "System Service unavailable"),
    LEASING_SERVICE_UNAVAILABLE_ERROR(50300007, "Leasing Service unavailable"),
    INTEGRATION_SERVICE_UNAVAILABLE_ERROR(50300008, "Integration service unavailable"),
    PARTNER_SERVICE_UNAVAILABLE_ERROR(50300009, "Partner Service unavailable"),
    ORGANIZATION_SERVICE_UNAVAILABLE_ERROR(50300010, "Organization Management Service unavailable"),
    PERSONNEL_SERVICE_UNAVAILABLE_ERROR(50300011, "Personnel Administration Service unavailable"),
    TENANT_SERVICE_UNAVAILABLE_ERROR(50300012, "Tenant Service unavailable"),
    ACCESS_CONTROL_SERVICE_UNAVAILABLE_ERROR(50300013, "Access control service unavailable"),
    PAYROLL_MANAGEMENT_SERVICE_UNAVAILABLE_ERROR(50300014, "Payroll management service unavailable"),
    TIME_MANAGEMENT_SERVICE_UNAVAILABLE_ERROR(50300015, "Time management service unavailable"),
    PERFORMANCE_MANAGEMENT_SERVICE_UNAVAILABLE_ERROR(50300016, "Performance management service unavailable"),
    WORKFLOW_SERVICE_UNAVAILABLE_ERROR(50300016, "Workflow Service unavailable"),
    PP_SERVICE_UNAVAILABLE_ERROR(50300017, "PP Service unavailable"),
    MM_SERVICE_UNAVAILABLE_ERROR(50300018, "MM Service unavailable");

    private final Integer code;
    private final String message;

    private ServiceUnavailableError(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getName() {
        return this.name();
    }

    public String getMessage() {
        return this.message;
    }

    public int getStatus() {
        return 503;
    }

    public Integer getCode() {
        return this.code;
    }
}
