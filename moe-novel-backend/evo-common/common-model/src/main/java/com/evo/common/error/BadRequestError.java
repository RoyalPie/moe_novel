package com.evo.common.error;

public enum BadRequestError implements ResponseError {
    INVALID_INPUT(40000001, "Invalid input : {0}"),
    INVALID_ACCEPT_LANGUAGE(40000002, "Invalid value for request header Accept-Language: {0}"),
    MISSING_PATH_VARIABLE(40000003, "Missing path variable"),
    PATH_INVALID(40000004, "Path is invalid"),
    UNDEFINED(40000005, ""),
    FILE_SIZE_EXCEEDED(40000006, "File size exceeds the limit"),
    CAN_NOT_APPROVE_WITH_APPROVAL_STATUS_OTHER_THAN_WAIT_APPROVE_STATUS(40000007, "Can not approve with approval status other than wait approve status"),
    CAN_NOT_REJECT_WITH_APPROVAL_STATUS_OTHER_THAN_WAIT_APPROVE_STATUS(40000008, "Can not reject with approval status other than wait approve status"),
    CAN_NOT_CANCEL_WITH_APPROVAL_STATUS_OTHER_THAN_WAIT_APPROVE_STATUS(40000009, "Can not cancel with approval status other than wait approve status"),
    CAN_NOT_WAIT_APPROVE_WITH_APPROVAL_STATUS_OTHER_THAN_NEW_STATUS(40000010, "Can not wait approve with approval status other than new status"),
    CAN_NOT_DELETE_IN_APPROVED_OR_WAIT_APPROVE(40000011, "Can not delete in approved or wait approve"),
    RECORD_IS_BEING_UPDATED(40000012, "The record is being updated. Please wait a minute"),
    CAN_NOT_BACK_WAIT_APPROVE_WITH_APPROVAL_STATUS_OTHER_THAN_APPROVED_STATUS(40000013, "Can not back wait approve with approval status other than approved status"),
    CAN_NOT_BACK_NEW_WITH_APPROVAL_STATUS_OTHER_THAN_APPROVED_STATUS(40000013, "Can not back new with approval status other than approved status"),
    INVALID_TOKEN(40000015, "Invalid token"),
    ONLY_CREATED_USER_CAN_ACCESS_NEW_RECORD(40000016, "Only created user can access new record"),
    USER_INVALID(40000017, "User invalid"),
    APPROVAL_STATUS_NOT_WAIT_APPROVE(40000018, "Approval status not wait approve"),
    CREATE_DOMAIN_CHANGE_LOG_FAIL(40000019, "Create domain change log fail"),
    REJECT_DOMAIN_CHANGE_LOG_BATCH_FAIL(40000020, "Approve domain change log batch fail"),
    APPROVE_DOMAIN_CHANGE_LOG_BATCH_FAIL(40000021, "Approve domain change log batch fail"),
    APPROVE_DOMAIN_CHANGE_LOG_FAIL(40000022, "Approve domain change log fail"),
    REJECT_DOMAIN_CHANGE_LOG_FAIL(40000023, "Reject domain change log fail"),
    CANCEL_DOMAIN_CHANGE_LOG_FAIL(40000024, "Cancel domain change log fail"),
    CREATE_DOMAIN_CHANGE_LOG_BATCH_FAIL(40000025, "Create domain change log batch fail"),
    REQUEST_APPROVE_DOMAIN_CHANGE_LOG_FAIL(40000026, "Request approve domain change log fail"),
    CANCEL_DRAFT_CHANGE_FAIL(40000027, "Create domain change log batch fail"),
    CAN_NOT_ACCESS_RECORD(40000028, "Can not access record"),
    ANOTHER_REQUEST_IS_IN_PROGRESS(40000029, "There is another request currently being processed"),
    YOUR_SERVICE_PACK_ORDER_HAS_EXPIRED(40000029, "Your service package has expired"),
    YOUR_SERVICE_PACK_ORDER_NOT_AVAILABLE(40000030, "Your service pack order is not available"),
    YOU_DO_NOT_PERMISSION_SCOPE(40000031, "You do not have permission for this scope."),
    SOURCE_USER_DO_NOT_PERMISSION_SCOPE(40000032, "Source user do not have permission for this scope."),
    TARGET_USER_DO_NOT_PERMISSION_SCOPE(40000033, "Target user do not have permission for this scope."),
    SCOPE_INVALID(40000034, "Scope invalid");

    private final Integer code;
    private final String message;

    private BadRequestError(Integer code, String message) {
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
        return 400;
    }

    public Integer getCode() {
        return this.code;
    }
}
