package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.*;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalItems.BAGEL;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.item.Item;
import seedu.address.model.item.Name;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ItemBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Item expectedItem = new ItemBuilder(BAGEL).withTags(VALID_TAG_BAKED).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BAGEL + ID_DESC_BAGEL + COUNT_DESC_BAGEL
                + TAG_DESC_BAKED, new AddCommand(expectedItem));

        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_DONUT + NAME_DESC_BAGEL + ID_DESC_BAGEL + COUNT_DESC_BAGEL
                + TAG_DESC_BAKED, new AddCommand(expectedItem));

        // multiple phones - last id accepted
        assertParseSuccess(parser, NAME_DESC_BAGEL + ID_DESC_DONUT + ID_DESC_BAGEL + COUNT_DESC_BAGEL
                + TAG_DESC_BAKED, new AddCommand(expectedItem));

        // multiple tags - all accepted
        Item expectedItemMultipleTags = new ItemBuilder(BAGEL).withTags(VALID_TAG_POPULAR, VALID_TAG_BAKED)
                .build();
        assertParseSuccess(parser, NAME_DESC_BAGEL + ID_DESC_BAGEL + COUNT_DESC_BAGEL
                + TAG_DESC_POPULAR + TAG_DESC_BAKED, new AddCommand(expectedItemMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Item expectedItem = new ItemBuilder(BAGEL).withTags().build();
        assertParseSuccess(parser, NAME_DESC_BAGEL + ID_DESC_BAGEL + COUNT_DESC_BAGEL,
                new AddCommand(expectedItem));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BAGEL + ID_DESC_BAGEL, expectedMessage);

        // missing id prefix
        assertParseFailure(parser, NAME_DESC_BAGEL + VALID_ID_BAGEL, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BAGEL + VALID_ID_BAGEL, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + ID_DESC_BAGEL + COUNT_DESC_BAGEL
                + TAG_DESC_POPULAR + TAG_DESC_BAKED, Name.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BAGEL + ID_DESC_BAGEL + COUNT_DESC_BAGEL
                + INVALID_TAG_DESC + VALID_TAG_BAKED, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + ID_DESC_BAGEL + COUNT_DESC_BAGEL
                + INVALID_TAG_DESC, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BAGEL + ID_DESC_BAGEL + COUNT_DESC_BAGEL
                + TAG_DESC_BAKED + TAG_DESC_POPULAR,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
