package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;

import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.Model;
import seedu.address.model.task.Task;

/**
 * Archives an entry in the address book.
 */
public class ArchiveTaskCommand extends Command {

    public static final String COMMAND_WORD = "archive";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Archives the selected entry";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Archived Person: %1$s";

    private final Index targetIndex;

    public ArchiveTaskCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTaskList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Task taskToArchive = lastShownList.get(targetIndex.getZeroBased());
        model.deleteTask(taskToArchive);
        model.archiveTask(taskToArchive);
        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, taskToArchive));
    }

}