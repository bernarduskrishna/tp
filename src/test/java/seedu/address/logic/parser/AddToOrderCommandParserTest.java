package seedu.address.logic.parser;


import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_NAME_SPECIFIED_TWICE;
import static seedu.address.logic.commands.CommandTestUtil.COUNT_DESC_BAGEL;
import static seedu.address.logic.commands.CommandTestUtil.COUNT_DESC_DONUT;
import static seedu.address.logic.commands.CommandTestUtil.COUNT_DESC_MAX_INT;
import static seedu.address.logic.commands.CommandTestUtil.COUNT_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.ID_DESC_BAGEL;
import static seedu.address.logic.commands.CommandTestUtil.ID_DESC_DONUT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COUNT_BEYOND_MAX_INT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COUNT_LETTER;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COUNT_NEGATIVE_VALUE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COUNT_ZERO;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ID_LETTER;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ID_NEGATIVE_NUMBER;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ID_SEVEN_DIGITS;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ID_SPECIAL_CHAR;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_SPECIAL_CHAR;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_BAKED;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_POPULAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COUNT_BAGEL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COUNT_DONUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_BAGEL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_DONUT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_100PLUS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BAGEL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DONUT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
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
    public void parse_nameEp() {

        // EP: valid name: alphabetical string
        // Heuristic: other inputs valid EP appear at least once
        ItemDescriptor expectedDescriptor = new ItemDescriptorBuilder()
                .withName(VALID_NAME_BAGEL)
                .withId(VALID_ID_BAGEL)
                .withCount(VALID_COUNT_BAGEL)
                .build();

        assertParseSuccess(parser, VALID_NAME_BAGEL + ID_DESC_BAGEL + COUNT_DESC_BAGEL,
                new AddToOrderCommand(expectedDescriptor));

        // EP: valid name: alphanumeric string
        // Heuristic: other inputs valid EP appear at least once
        expectedDescriptor = new ItemDescriptorBuilder()
                .withName(VALID_NAME_100PLUS)
                .withId(VALID_ID_DONUT)
                .withCount(VALID_COUNT_DONUT)
                .build();

        assertParseSuccess(parser, VALID_NAME_100PLUS + ID_DESC_DONUT + COUNT_DESC_DONUT,
                new AddToOrderCommand(expectedDescriptor));

        String invalidNameErrorMsg =
                "Names should only contain alphanumeric characters and spaces, and it should not be blank";
        // EP: invalid name: empty string for name.
        //assertParseFailure(parser, INVALID_NAME_EMPTY_STRING + ID_DESC_BAGEL + COUNT_DESC_BAGEL, invalidNameErrorMsg);

        // EP: invalid name: special chars involved. e.g."cake$"
        //assertParseFailure(parser, INVALID_NAME_SPECIAL_CHAR + ID_DESC_BAGEL + COUNT_DESC_BAGEL, invalidNameErrorMsg);

        // EP: invalid name: name comes with prefix.
        //assertParseFailure(parser, INVALID_NAME_DESC + ID_DESC_BAGEL + COUNT_DESC_BAGEL, invalidNameErrorMsg);
    }

    @Test
    public void parse_idEp() {

        // EP: valid id: alphabetical string
        // Heuristic: other inputs valid EP appear at least once
        ItemDescriptor expectedDescriptor = new ItemDescriptorBuilder()
                .withName(VALID_NAME_BAGEL)
                .withId(VALID_ID_BAGEL)
                .withCount(VALID_COUNT_BAGEL)
                .build();

        assertParseSuccess(parser, VALID_NAME_BAGEL + ID_DESC_BAGEL + COUNT_DESC_BAGEL,
                new AddToOrderCommand(expectedDescriptor));

        // EP: invalid id: negative number
        String invalidIdErrorMsg = "The id provided must be positive and at most 6 digits!";
        assertParseFailure(parser, VALID_NAME_BAGEL + INVALID_ID_NEGATIVE_NUMBER + COUNT_DESC_BAGEL,
                invalidIdErrorMsg);

        // EP: invalid id: more than 6 digits
        String invalidLengthErrorMsg = "The id provided must be positive and at most 6 digits!";
        assertParseFailure(parser, VALID_NAME_BAGEL + INVALID_ID_SEVEN_DIGITS + COUNT_DESC_BAGEL,
                invalidLengthErrorMsg);

        // EP: invalid id: contains letters
        String notIntegerErrorMsg = "The id provided must be integer!";
        assertParseFailure(parser, VALID_NAME_BAGEL + INVALID_ID_SPECIAL_CHAR + COUNT_DESC_BAGEL, notIntegerErrorMsg);

        // EP: invalid id: contains special chars
        assertParseFailure(parser, VALID_NAME_BAGEL + INVALID_ID_LETTER + COUNT_DESC_BAGEL, notIntegerErrorMsg);
    }

    @Test
    public void parse_countEp() {
        // EP: valid count: boundary value 1
        ItemDescriptor expectedDescriptor = new ItemDescriptorBuilder()
                .withName(VALID_NAME_BAGEL)
                .withId(VALID_ID_BAGEL)
                .withCount("1")
                .build();

        assertParseSuccess(parser, VALID_NAME_BAGEL + ID_DESC_BAGEL + COUNT_DESC_ONE,
                new AddToOrderCommand(expectedDescriptor));

        // EP: valid count: boundary value Integer.MAX_VALUE
        expectedDescriptor = new ItemDescriptorBuilder()
                .withName(VALID_NAME_BAGEL)
                .withId(VALID_ID_BAGEL)
                .withCount(String.valueOf(Integer.MAX_VALUE))
                .build();

        assertParseSuccess(parser, VALID_NAME_BAGEL + ID_DESC_BAGEL + COUNT_DESC_MAX_INT,
                new AddToOrderCommand(expectedDescriptor));

        // EP: invalid count: boundary value 0
        String zeroCountErrorMsg = "The count provided must be positive!";
        assertParseFailure(parser, VALID_NAME_BAGEL + ID_DESC_BAGEL + INVALID_COUNT_ZERO,
                zeroCountErrorMsg);

        // EP: invalid count: boundary value -1
        String negativeCountErrorMsg = "The count provided must be positive!";
        assertParseFailure(parser, VALID_NAME_BAGEL + ID_DESC_BAGEL + INVALID_COUNT_NEGATIVE_VALUE,
                negativeCountErrorMsg);

        // EP: invalid count: boundary value Integer.MAX_VALUE + 1
        String notIntegerErrorMsg = "The count provided must be integer!";
        assertParseFailure(parser, VALID_NAME_BAGEL + ID_DESC_BAGEL + INVALID_COUNT_BEYOND_MAX_INT,
                notIntegerErrorMsg);

        // EP: invalid count: not a number
        assertParseFailure(parser, VALID_NAME_BAGEL + ID_DESC_BAGEL + INVALID_COUNT_LETTER,
                notIntegerErrorMsg);
    }


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
    public void parse_twoNameFields_failure() {
        String expectedMessage = String.format(MESSAGE_NAME_SPECIFIED_TWICE + "\n" + AddToOrderCommand.MESSAGE_USAGE);

        // both name and id prefix missing
        assertParseFailure(parser, VALID_NAME_BAGEL + " " + PREFIX_NAME + VALID_NAME_DONUT, expectedMessage);
    }

    @Test
    public void parse_countZero_failure() {
        assertParseFailure(parser, VALID_NAME_BAGEL + ID_DESC_BAGEL + INVALID_COUNT_ZERO,
                Messages.MESSAGE_INVALID_COUNT_INTEGER);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_SPECIAL_CHAR + ID_DESC_BAGEL + COUNT_DESC_BAGEL
                + TAG_DESC_POPULAR + TAG_DESC_BAKED, Name.MESSAGE_CONSTRAINTS);

        // invalid id with negative number
        assertParseFailure(parser, VALID_NAME_BAGEL + INVALID_ID_NEGATIVE_NUMBER + COUNT_DESC_BAGEL
                + TAG_DESC_POPULAR + TAG_DESC_BAKED, Messages.MESSAGE_INVALID_ID_LENGTH_AND_SIGN);

        // invalid id with 3 numbers
        assertParseFailure(parser, VALID_NAME_BAGEL + INVALID_ID_LETTER + COUNT_DESC_BAGEL
                + TAG_DESC_POPULAR + TAG_DESC_BAKED, Messages.MESSAGE_INVALID_ID_FORMAT);

        // invalid count format
        assertParseFailure(parser, VALID_NAME_BAGEL + ID_DESC_BAGEL + INVALID_COUNT_LETTER + TAG_DESC_BAKED,
                Messages.MESSAGE_INVALID_COUNT_FORMAT);

        // invalid count value
        assertParseFailure(parser, VALID_NAME_BAGEL + ID_DESC_BAGEL + INVALID_COUNT_NEGATIVE_VALUE + TAG_DESC_BAKED,
                Messages.MESSAGE_INVALID_COUNT_INTEGER);
    }
}
