package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalItems.APPLE_PIE;
import static seedu.address.testutil.TypicalItems.BAGEL;
import static seedu.address.testutil.TypicalItems.getTypicalInventory;
import static seedu.address.testutil.TypicalItems.getTypicalItems;

import java.util.Collection;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.item.Item;

public class DisplayListTest {

    private DisplayList displayList = new DisplayList(getTypicalInventory().getItemList());
    private FilteredList<Item> filteredList = displayList.getFilteredItemList();

    @Test
    public void constructor_setSource() {
        ObservableList<Item> itemSource = FXCollections.observableArrayList(getTypicalItems());
        DisplayList displayList = new DisplayList(itemSource);
        assertSourceListening(displayList.getFilteredItemList(), itemSource);
    }

    @Test
    public void setItems_changeSource_success() {
        ObservableList<Item> itemSource = FXCollections.observableArrayList(getTypicalItems());
        displayList.setItems(itemSource);
        assertSourceListening(filteredList, itemSource);
    }

    @Test
    public void setItems_notListeningToOldSource() {
        ObservableList<Item> itemSource1 = FXCollections.observableArrayList(getTypicalItems());
        displayList.setItems(itemSource1);

        ObservableList<Item> itemSource2 = FXCollections.observableArrayList(getTypicalItems());
        displayList.setItems(itemSource2);

        itemSource1.add(BAGEL); // Change in itemSource1 should no longer propagate to filteredList
        assertEquals(itemSource2, filteredList);
    }

    @Test
    public void setItems_sameSource_success() {
        ObservableList<Item> itemSource = FXCollections.observableArrayList(getTypicalItems());
        displayList.setItems(itemSource);
        displayList.setItems(itemSource); // Attach to same source again
        assertSourceListening(filteredList, itemSource);
    }

    @Test
    public void setPredicate_success() {
        displayList.setPredicate(x -> x.equals(APPLE_PIE));

        FilteredList<Item> expectedList = new FilteredList<>(getTypicalInventory().getItemList());
        expectedList.setPredicate(x -> x.equals(APPLE_PIE));

        assertEquals(filteredList, expectedList);
    }

    /**
     * Asserts that source and filtered lists match
     * and any changes in the given source is propagated to the filteredList
     */
    private void assertSourceListening(FilteredList<Item> filteredList, Collection<Item> source) {
        // Check if lists are initially the same
        assertEquals(source, filteredList);

        // Edit source
        source.add(BAGEL);

        // Test if change is propagated
        assertEquals(source, filteredList);
    }
}