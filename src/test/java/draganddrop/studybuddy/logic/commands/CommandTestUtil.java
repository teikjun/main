package draganddrop.studybuddy.logic.commands;

import static draganddrop.studybuddy.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import draganddrop.studybuddy.logic.commands.exceptions.CommandException;
import draganddrop.studybuddy.model.Model;
import draganddrop.studybuddy.model.StudyBuddy;
import draganddrop.studybuddy.model.task.Task;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_TASK_NAME_ONE = "Project meeting";
    public static final String VALID_TASK_NAME_TWO = "Job application";
    public static final String VALID_TASK_TYPE_THREE = "Assignment";
    public static final String VALID_TASK_TYPE_FOUR = "Quiz";
    public static final String VALID_DESCRIPTION_ONE = "Project";
    public static final String VALID_DESCRIPTION_TWO = "Final draft of Project";
    public static final String VALID_MODULE_ONE = "CS2103T";
    public static final String VALID_MODULE_TWO = "CS2101";
    public static final String VALID_DATE_ONE = "17:00 28/04/2060";
    public static final String VALID_DATE_TWO = "00:00 29/06/2040- 13:00 30/06/2040";

    public static final String INVALID_DATE_ONE = "12/12/12";


    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException | ParseException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the study buddy, filtered task list and selected task in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        StudyBuddy expectedStudyBuddy = new StudyBuddy(actualModel.getStudyBuddy());
        List<Task> expectedFilteredList = new ArrayList<>(actualModel.getFilteredTaskList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedStudyBuddy, actualModel.getStudyBuddy());
        assertEquals(expectedFilteredList, actualModel.getFilteredTaskList());
    }

}
