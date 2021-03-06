package draganddrop.studybuddy.logic.interactiveprompt.edit;

import static draganddrop.studybuddy.logic.interactiveprompt.InteractivePromptType.ARCHIVE_TASK;

import java.text.ParseException;

import draganddrop.studybuddy.commons.core.index.Index;
import draganddrop.studybuddy.logic.commands.edit.ArchiveTaskCommand;
import draganddrop.studybuddy.logic.commands.exceptions.CommandException;
import draganddrop.studybuddy.logic.interactiveprompt.InteractivePrompt;
import draganddrop.studybuddy.logic.interactiveprompt.InteractivePromptTerms;
import draganddrop.studybuddy.logic.parser.interactivecommandparser.exceptions.ArchiveTaskCommandException;
import draganddrop.studybuddy.model.task.Task;

/**
 * Represents an ArchiveTaskInteractivePrompt, which interacts with user to archive a task.
 */
public class ArchiveTaskInteractivePrompt extends InteractivePrompt {
    public static final String QUIT_COMMAND_MSG = "Successfully quited from archive command.";
    private static final String END_OF_COMMAND_MSG = "Task archived successfully!";
    private static final String REQUIRED_INDEX_MSG = "Please enter the index number of task you wish to archive.";
    private int index;

    public ArchiveTaskInteractivePrompt() {
        super();
        this.interactivePromptType = ARCHIVE_TASK;
    }

    @Override
    public String interact(String userInput) {
        if ("quit".equalsIgnoreCase(userInput)) {
            endInteract(QUIT_COMMAND_MSG);
            return reply;
        }

        switch (currentTerm) {

        case INIT:
            this.reply = REQUIRED_INDEX_MSG;
            currentTerm = InteractivePromptTerms.TASK_INDEX;
            break;

        case TASK_INDEX:
            try {
                if (userInput.isBlank()) {
                    throw new ArchiveTaskCommandException("emptyInputError");
                }
                index = Integer.parseInt(userInput);
                if (index > Task.getCurrentTasks().size() || index <= 0) {
                    throw new ArchiveTaskCommandException("invalidIndexRangeError");
                }
                index = Integer.parseInt(userInput);
                reply = "The task " + Task.getCurrentTasks().get(index - 1).getTaskName() + " will be archived. \n\n"
                    + "Please press enter again to make the desired changes.";
                currentTerm = InteractivePromptTerms.READY_TO_EXECUTE;
            } catch (NumberFormatException ex) {
                reply = (new ArchiveTaskCommandException("wrongIndexFormatError")).getErrorMessage()
                    + "\n\n" + REQUIRED_INDEX_MSG;
            } catch (ArchiveTaskCommandException ex) {
                reply = ex.getErrorMessage()
                    + "\n\n" + REQUIRED_INDEX_MSG;
            }
            break;

        case READY_TO_EXECUTE:
            try {
                ArchiveTaskCommand archiveTaskCommand = new ArchiveTaskCommand(Index.fromZeroBased(index - 1));
                logic.executeCommand(archiveTaskCommand);
                endInteract(END_OF_COMMAND_MSG);
            } catch (CommandException | ParseException ex) {
                reply = ex.getMessage();
            }
            break;

        default:
        }
        return reply;
    }

    @Override
    public void endInteract(String reply) {
        this.reply = reply;
        super.setEndOfCommand(true);
    }
}
