package draganddrop.studybuddy.model.module;

import static java.util.Objects.requireNonNull;

import draganddrop.studybuddy.model.module.exceptions.ModuleException;
import draganddrop.studybuddy.model.task.Task;
import draganddrop.studybuddy.model.task.exceptions.DuplicateTaskException;
import draganddrop.studybuddy.model.task.exceptions.TaskNotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * packages task into various modules that the student have. Each module has list of task, taken from the main TaskList.
 * Identified by ModuleCode instead of ModuleName to minimise errors.
 */
public class Module {
    private ObservableList<Task> internalTaskList = FXCollections.observableArrayList();
    private String moduleName;
    private ModuleCode moduleCode;

    /**
     * Constructs a Module. Will check for any duplicates in the moduleList.
     *
     * @param moduleName
     * @param fullModuleCode
     * @throws ModuleException
     */
    public Module(String moduleName, String fullModuleCode) {
        this.moduleName = moduleName;
        try {
            this.moduleCode = new ModuleCode(fullModuleCode);
        } catch (ModuleException e) {
            System.out.println("ModuleCode is invalid!");
        }
    }

    /**
     * Same as previous constructor, but a module without a name.
     *
     * @param fullModuleCode
     * @throws ModuleException
     */
    public Module(String fullModuleCode) throws ModuleException {
        this.moduleName = "";
        this.moduleCode = new ModuleCode(fullModuleCode);
    }

    public Module() {
        this.moduleName = "";
        try {
            this.moduleCode = new ModuleCode("OO0000O");
        } catch (ModuleException e) {
            System.out.println("from Module(). ModuleCode is invalid!");
        }
    }

    /**
     * returns whether a Task is inside the internalList of this module.
     *
     * @param toCheck
     * @return
     */
    public boolean isContains(Task toCheck) {
        requireNonNull(toCheck);
        return internalTaskList.stream().anyMatch(toCheck::isSameTask);
    }

    /**
     * Adds a task.
     *
     * @param toAdd
     * @throws DuplicateTaskException
     */
    public void add(Task toAdd) throws DuplicateTaskException {
        requireNonNull(toAdd);
        if (isContains(toAdd)) {
            throw new DuplicateTaskException("duplicateTask");
        }
        internalTaskList.add(toAdd);
    }

    /**
     * Designed to add task from another taskList to this.
     * @param toAdd
     * @throws DuplicateTaskException
     */
    public void addAll(ObservableList<Task> toAdd) throws DuplicateTaskException {
        requireNonNull(toAdd);
        if (!toAdd.filtered(x -> isContains(x)).isEmpty()) {
            throw new DuplicateTaskException("duplicateTask");
        } else {
            internalTaskList.addAll(toAdd);
        }
    }

    /**
     * Removes a task from the module list.
     *
     * @param toRemove
     * @throws TaskNotFoundException
     */
    public void remove(Task toRemove) throws TaskNotFoundException {
        requireNonNull(toRemove);
        if (isContains(toRemove)) {
            internalTaskList.remove(toRemove);
        } else {
            throw new TaskNotFoundException();
        }
    }

    /**
     * Compares moduleCode instead of moduleName.
     *
     * @param other object, possibly a module
     * @return true or false.
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Module) {
            ModuleCode otherModuleCode = ((Module) other).moduleCode;
            return this.moduleCode.equals(otherModuleCode);
        } else {
            return false;
        }
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public ModuleCode getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) throws ModuleException {
        this.moduleCode = new ModuleCode(moduleCode);
    }

    public ObservableList<Task> getInternalTaskList() {
        return internalTaskList;
    }

    public void setInternalTaskList(ObservableList<Task> internalTaskList) {
        this.internalTaskList = internalTaskList;
    }

    /**
     * Takes in an ObservableList of Task, filters it, then setInternalTaskList().
     *
     * @param externalTaskList
     */
    public void filterAndSetInternalTaskList(ObservableList<Task> externalTaskList) {
        ObservableList<Task> collectedTasks = externalTaskList.filtered(x -> x.getModule().equals(this));
        setInternalTaskList(collectedTasks);
    }

    @Override
    public String toString() {
        return getModuleCode().toString();
    }

}
