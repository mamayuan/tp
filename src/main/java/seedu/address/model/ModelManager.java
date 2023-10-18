package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.contact.Contact;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ConText conText;
    private final UserPrefs userPrefs;
    private final FilteredList<Contact> filteredContacts;

    /**
     * Initializes a ModelManager with the given conText and userPrefs.
     */
    public ModelManager(ReadOnlyConText conText, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(conText, userPrefs);

        logger.fine("Initializing with address book: " + conText + " and user prefs " + userPrefs);

        this.conText = new ConText(conText);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredContacts = new FilteredList<>(this.conText.getContactList());
    }

    public ModelManager() {
        this(new ConText(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getConTextFilePath() {
        return userPrefs.getConTextFilePath();
    }

    @Override
    public void setConTextFilePath(Path conTextFilePath) {
        requireNonNull(conTextFilePath);
        userPrefs.setConTextFilePath(conTextFilePath);
    }

    //=========== ConText ================================================================================

    @Override
    public void setConText(ReadOnlyConText conText) {
        this.conText.resetData(conText);
    }

    @Override
    public ReadOnlyConText getConText() {
        return conText;
    }

    @Override
    public boolean hasContact(Contact contact) {
        requireNonNull(contact);
        return conText.hasContact(contact);
    }

    @Override
    public void deleteContact(Contact target) {
        conText.removeContact(target);
    }

    @Override
    public void addContact(Contact contact) {
        conText.addContact(contact);
        updateFilteredContactList(PREDICATE_SHOW_ALL_CONTACTS);
    }

    @Override
    public void setContact(Contact target, Contact editedContact) {
        requireAllNonNull(target, editedContact);

        conText.setContact(target, editedContact);
    }

    //=========== Filtered Contact List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Contact} backed by the internal list of
     * {@code versionedConText}
     */
    @Override
    public ObservableList<Contact> getFilteredContactList() {
        return filteredContacts;
    }

    @Override
    public void updateFilteredContactList(Predicate<Contact> predicate) {
        requireNonNull(predicate);
        filteredContacts.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return conText.equals(otherModelManager.conText)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredContacts.equals(otherModelManager.filteredContacts);
    }

}
