package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.COUNT_DESC_BAGEL;
import static seedu.address.logic.commands.CommandTestUtil.COUNT_DESC_DONUT;
import static seedu.address.logic.commands.CommandTestUtil.COUNT_DESC_ZERO;
import static seedu.address.logic.commands.CommandTestUtil.ID_DESC_BAGEL;
import static seedu.address.logic.commands.CommandTestUtil.ID_DESC_DONUT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COUNT_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COUNT_VALUE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ID_BAGEL;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ID_BAGEL_2;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_BAKED;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_POPULAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COUNT_BAGEL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_BAGEL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BAGEL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddToOrderCommand;
import seedu.address.model.item.ItemDescriptor;
import seedu.address.model.item.Name;
import seedu.address.testutil.ItemDescriptorBuilder;

public class AddToOrderCommandParserTest {
    private AddToOrderCommandParser parser = new AddToOrderCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        ItemDescriptor expectedDescriptor = new ItemDescriptorBuilder()
                .withName(VALID_NAME_BAGEL)
                .withId(VALID_ID_BAGEL)
                .withCount(VALID_COUNT_BAGEL)
                .build();

        // All fields
        assertParseSuccess(parser, VALID_NAME_BAGEL + ID_DESC_BAGEL + COUNT_DESC_BAGEL,
                new AddToOrderCommand(expectedDescriptor));

        // multiple id - last id accepted
        assertParseSuccess(parser,
                VALID_NAME_BAGEL + ID_DESC_DONUT + ID_DESC_BAGEL + COUNT_DESC_BAGEL,
                new AddToOrderCommand(expectedDescriptor));

        // multiple count - last count accepted
        assertParseSuccess(parser,
                VALID_NAME_BAGEL + ID_DESC_BAGEL + COUNT_DESC_DONUT + COUNT_DESC_BAGEL,
                new AddToOrderCommand(expectedDescriptor));
    }

    @Test
    public void parse_nameOnlyNoId_success() {
        ItemDescriptor expectedDescriptor = new ItemDescriptorBuilder()
                .withName(VALID_NAME_BAGEL)
                .withCount(VALID_COUNT_BAGEL)
                .build();

        assertParseSuccess(parser, VALID_NAME_BAGEL + COUNT_DESC_BAGEL,
                new AddToOrderCommand(expectedDescriptor));
    }

    @Test
    public void parse_idOnlyNoName_success() {
        ItemDescriptor expectedDescriptor = new ItemDescriptorBuilder()
                .withId(VALID_ID_BAGEL)
                .withCount(VALID_COUNT_BAGEL)
                .build();

        assertParseSuccess(parser, ID_DESC_BAGEL + COUNT_DESC_BAGEL,
                new AddToOrderCommand(expectedDescriptor));
    }

    @Test
    public void parse_countMissing_success() {

        // no count (count should be defaulted to 1)
        ItemDescriptor expectedDescriptor = new ItemDescriptorBuilder()
                .withName(VALID_NAME_BAGEL)
                .withId(VALID_ID_BAGEL)
                .withCount(1)
                .build();
        assertParseSuccess(parser, VALID_NAME_BAGEL + ID_DESC_BAGEL,
                new AddToOrderCommand(expectedDescriptor));

    }

    @Test
    public void parse_noNameOrId_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddToOrderCommand.MESSAGE_USAGE);

        // both name and id prefix missing
        assertParseFailure(parser, COUNT_DESC_BAGEL, expectedMessage);
    }

    @Test
    public void parse_countZero_failure() {
        assertParseFailure(parser, VALID_NAME_BAGEL + ID_DESC_BAGEL + COUNT_DESC_ZERO,
                Messages.MESSAGE_INVALID_COUNT_INTEGER);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME + ID_DESC_BAGEL + COUNT_DESC_BAGEL
                + TAG_DESC_POPULAR + TAG_DESC_BAKED, Name.MESSAGE_CONSTRAINTS);

        // invalid id with negative number
        assertParseFailure(parser, VALID_NAME_BAGEL + INVALID_ID_BAGEL_2 + COUNT_DESC_BAGEL
                + TAG_DESC_POPULAR + TAG_DESC_BAKED, Messages.MESSAGE_INVALID_ID_LENGTH_AND_SIGN);

        // invalid id with 3 numbers
        assertParseFailure(parser, VALID_NAME_BAGEL + INVALID_ID_BAGEL + COUNT_DESC_BAGEL
                + TAG_DESC_POPULAR + TAG_DESC_BAKED, Messages.MESSAGE_INVALID_ID_LENGTH_AND_SIGN);

        // invalid count format
        assertParseFailure(parser, VALID_NAME_BAGEL + ID_DESC_BAGEL + INVALID_COUNT_FORMAT + TAG_DESC_BAKED,
                Messages.MESSAGE_INVALID_COUNT_FORMAT);

        // invalid count value
        assertParseFailure(parser, VALID_NAME_BAGEL + ID_DESC_BAGEL + INVALID_COUNT_VALUE + TAG_DESC_BAKED,
                Messages.MESSAGE_INVALID_COUNT_INTEGER);
    }
}