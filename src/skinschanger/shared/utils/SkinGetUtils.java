package skinschanger.shared.utils;

import java.util.UUID;
import skinschanger.libs.com.mojang.api.profiles.Profile;
import skinschanger.libs.org.json.simple.parser.ParseException;
import skinschanger.shared.format.SkinProfile;
import skinschanger.shared.format.SkinProperty;

public class SkinGetUtils {
	public static SkinProfile getSkinProfile(String name)
			throws SkinFetchFailedException {
		try {
			Profile profile = DataUtils.getProfile(name);
			SkinProperty prop = DataUtils.getProp(profile.getId());
			return new SkinProfile(prop);
		} catch (ParseException e) {
			throw new SkinFetchFailedException(SkinFetchFailedException.Reason.SKIN_RECODE_FAILED);
		} catch (SkinFetchFailedException sffe) {
			throw sffe;
		} catch (Throwable t) {
			throw new SkinFetchFailedException(t);
		}
	}

	public static UUID uuidFromString(String input) {
		return UUID.fromString(input.replaceFirst("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"));
	}

	public static class SkinFetchFailedException extends Exception {
		private static final long serialVersionUID = -7597517818949217019L;

		public SkinFetchFailedException(Reason reason) {
			super();
		}

		public SkinFetchFailedException(Throwable exception) {
			super(exception);
		}

		public static enum Reason {
			NO_PREMIUM_PLAYER(
					"Can't find a valid premium player with that name"),
			NO_SKIN_DATA(
					"No skin data found for player with that name"),
			SKIN_RECODE_FAILED("Can't decode skin data"),
			GENERIC_ERROR("An error has occured");

			private String exceptionCause;

			private Reason(String exceptionCause) {
				this.exceptionCause = exceptionCause;
			}

			public String getExceptionCause() {
				return this.exceptionCause;
			}
		}
	}
}
