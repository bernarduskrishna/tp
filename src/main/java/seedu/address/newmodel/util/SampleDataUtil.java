package seedu.address.newmodel.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.newmodel.Inventory;
import seedu.address.newmodel.ReadOnlyInventory;
import seedu.address.newmodel.item.Item;
import seedu.address.newmodel.item.Name;
import seedu.address.newmodel.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Item[] getSampleItems() {
        return new Item[] {
            new Item(new Name("Oatmeal Cookie"), "#140121",
                getTagSet("baked")),
            new Item(new Name("Banana Muffin"), "#201928",
                    getTagSet("baked")),
            new Item(new Name("Pecan Pie"), "#178522",
                    getTagSet("baked")),
            new Item(new Name("Oreo Cheesecake"), "#109128",
                    getTagSet("desert")),
            new Item(new Name("Strawberry Shortcake"), "#091287",
                    getTagSet("desert")),
            new Item(new Name("Cold Brew Coffee"), "#001858",
                    getTagSet("beverage")),
        };
    }

    public static ReadOnlyInventory getSampleItemList() {
        Inventory sampleInventory = new Inventory();
        for (Item sampleItem : getSampleItems()) {
            sampleInventory.addItem(sampleItem);
        }
        return sampleInventory;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
