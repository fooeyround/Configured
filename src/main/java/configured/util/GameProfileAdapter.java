package configured.util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.mojang.authlib.GameProfile;
import configured.Configured;
import net.minecraft.commands.arguments.GameProfileArgument;

import java.io.IOException;
import java.util.UUID;

public class GameProfileAdapter extends TypeAdapter<GameProfileArgument.Result> {

    @Override
    public void write(JsonWriter writer, GameProfileArgument.Result value) throws IOException {
//        value.getNames(null).forEach(c -> writer.value(String.valueOf(c.id())));
    }

    @Override
    public GameProfileArgument.Result read(JsonReader reader) throws IOException {
//        return Configured.MC_SERVER.services().profileResolver().fetchById(UUID.fromString(reader.nextString())).orElseThrow();
        return null;
    }
}
