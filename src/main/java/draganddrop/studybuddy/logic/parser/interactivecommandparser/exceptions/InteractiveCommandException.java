package draganddrop.studybuddy.logic.parser.interactivecommandparser.exceptions;

/**
 * Represents an InteractiveCommandException.
 */
public class InteractiveCommandException extends RuntimeException {
    protected String errorType;
    protected String errorMessage;

    public InteractiveCommandException(String errorType) {
        this.errorType = errorType;

        switch (errorType) {
        case "unKnownException":
            errorMessage = "Unknown problem happened, please quit this command or relaunch me.\n\n"
                + "If you still face this error, you may have to contact my developer at: \n"
                + "https://github.com/AY1920S2-CS2103T-W16-3/main/blob/master/docs/ContactUs.adoc";
            break;

        case "emptyInputError":
            errorMessage = "The input is empty, please check again.";
            break;

        case "moduleWeightOverloadError":
            errorMessage = "The maximum sum of task's weights under the same module is 100.0, please check again.";
            break;

        case "taskCompletedError":
            errorMessage = "The task you chose is completed already";
            break;

        case "specialCharInputError":
            errorMessage = "The task name contains special character, please check again.";
            break;

        case "taskNameLengthError":
            errorMessage = "The maximum length of task name is 20 characters, please check again.";
            break;

        case "changeAssignmentToOtherTaskTypeError":
            errorMessage = "Due to the deadline/Duration format is different from assignment and the rest task types, "
                + "we currently don't support \"Assignment -> Another Task Type\" editing.";
            break;

        case "changeOtherTaskTypeToAssignmentError":
            errorMessage = "Due to the deadline/Duration format is different from assignment and the rest task types, "
                + "we currently don't support \"Another Task Type -> Assignment\" editing.";
            break;

        case "taskDescriptionLengthError":
            errorMessage = "The maximum length of task description is 300 characters, please check again.";
            break;

        case "wrongWeightFormatError":
            errorMessage = "Please enter decimal number for task task weight.";
            break;

        case "wrongWeightRangeError":
            errorMessage = "The weight should be from 0.0 to 100.0.";
            break;

        case "invalidInputError":
            errorMessage = "This is not a valid input.";
            break;

        case "wrongEstimatedTimeFormatError":
            errorMessage = "Please enter decimal number for the number of hours the task might take.";
            break;

        case "wrongEstimatedTimeRangeError":
            errorMessage = "Please enter a positive number for the number of hours the task might take.";
            break;

        case "noSuchModuleError":
            errorMessage = "Could not find the module based on the module code entered, please check again.";
            break;

        case "duplicateModuleNameError":
            errorMessage = "Detected duplicate module name. Please key in another module name.";
            break;

        case "duplicateModuleCodeError":
            errorMessage = "Detected duplicate module code. Please key in another module code.";
            break;

        case "wrongModuleCodeFormatError":
            errorMessage = "Invalid module code. Please key in module in the correct format. \n\n"
                + " Module code format:\n"
                + " include a 2-3 letter prefix,"
                + " a 4-digit number,"
                + " then a postfix (Optional).\n"
                + " E.g. BA1001, CS2030J, LSM2040C";
            break;

        case "noChangeFromOriginalModuleNameError":
            errorMessage = "Please enter a different module name.";
            break;

        case "noChangeFromOriginalModuleCodeError":
            errorMessage = "Please enter a different module code.";
            break;

        case "invalidIndexRangeError":
            errorMessage = "The index entered is out of range, please check again.";
            break;

        case "invalidOptionRangeError":
            errorMessage = "The option index entered is out of range, please check again.";
            break;

        case "wrongIndexFormatError":
            errorMessage = "The format of index entered is invalid, please enter an integer.";
            break;

        case "wrongOptionFormatError":
            errorMessage = "The format of option index entered is invalid, please enter an integer.";
            break;

        case "dataTimeFormatError":
            errorMessage = "Invalid date time format, please check again.";
            break;

        case "pastDateTime":
            errorMessage = "Invalid date time, please enter a time in the future.";
            break;

        case "eventEndBeforeStartError":
            errorMessage = "Invalid date time, the end date you entered is "
                + "not after the start date, please check again.";
            break;

        case "dateFormatError":
            errorMessage = "Invalid date format, please follow the format below:\n\n"
                + "dd/MM/yyyy";
            break;

        case "tooLongAway":
            errorMessage = "Date too far apart from current date, please input a more realistic date";
            break;

        default:
        }
    }


    public String getErrorMessage() {
        return errorMessage;
    }

    public String getErrorType() {
        return errorType;
    }
}
