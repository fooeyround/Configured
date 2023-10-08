package tech.lemonlime.ToggleEnd.util;



import net.minecraft.text.Text;


//#if MC < 11700
//$$ import net.minecraft.text.LiteralText;
//$$ import net.minecraft.text.TranslatableText;
//#endif

public class TextHelper {


    public static Text literal(String text) {
        //#if MC >= 11700
        return Text.literal(text);
        //#else
        //$$ return new LiteralText(text);
    }



    public static Text translatable(String text) {
        //#if MC >= 11700
        return Text.translatable(text);
        //#else
        //$$ return new TranslatableText(text);
    }

    public static Text translatable(String text, Object... args) {
        //#if MC >= 11700
        return Text.translatable(text, args);
        //#else
        //$$ return new TranslatableText(text, args);
    }

}
