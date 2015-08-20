package skinschanger.shared.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import skinschanger.libs.com.mojang.api.profiles.HttpProfileRepository;
import skinschanger.libs.com.mojang.api.profiles.Profile;
import skinschanger.libs.org.json.simple.JSONArray;
import skinschanger.libs.org.json.simple.JSONObject;
import skinschanger.libs.org.json.simple.parser.JSONParser;
import skinschanger.libs.org.json.simple.parser.ParseException;
import skinschanger.shared.format.SkinProperty;
import skinschanger.shared.utils.apacheutils.IOUtils;

public class DataUtils {
	// private static final String skullbloburl =
	// "https://sessionserver.mojang.com/session/minecraft/profile/";
	public static Profile getProfile(String nick)
			throws SkinGetUtils.SkinFetchFailedException {
		HttpProfileRepository repo = new HttpProfileRepository("minecraft");
		Profile[] profiles = repo.findProfilesByNames(new String[] { nick });
		if (profiles.length >= 1) {
			return profiles[0];
		}
		throw new SkinGetUtils.SkinFetchFailedException(SkinGetUtils.SkinFetchFailedException.Reason.NO_PREMIUM_PLAYER);
	}

	public static SkinProperty getProp(String id) throws IOException,
			ParseException, SkinGetUtils.SkinFetchFailedException {
		URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + id + "?unsigned=false");
		URLConnection connection = url.openConnection();
		connection.setConnectTimeout(10000);
		connection.setReadTimeout(10000);
		connection.setUseCaches(false);
		InputStream is = connection.getInputStream();
		String result = IOUtils.toString(is, StandardCharsets.UTF_8);
		IOUtils.closeQuietly(is);
		JSONArray properties = (JSONArray) ((JSONObject) new JSONParser().parse(result)).get("properties");
		for (int i = 0; i < properties.size(); i++) {
			JSONObject property = (JSONObject) properties.get(i);
			String name = (String) property.get("name");
			String value = (String) property.get("value");
			String signature = (String) property.get("signature");
			if (name.equals("textures")) {
				return new SkinProperty(name, value, signature);
			}
		}
		throw new SkinGetUtils.SkinFetchFailedException(SkinGetUtils.SkinFetchFailedException.Reason.NO_SKIN_DATA);
	}
}