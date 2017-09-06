package org.Tollainmear.UUIDFilter;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializer;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.ResourceBundle;

public class Translator {
    ResourceBundle resourceBundle;
    TextSerializer stringFormattor = TextSerializers.FORMATTING_CODE;

    public Text take(String key){
        return stringFormattor.deserialize(key);
    }
}
