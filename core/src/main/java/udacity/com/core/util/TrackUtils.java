package udacity.com.core.util;

import android.os.Bundle;

import udacity.com.core.Application;

public class TrackUtils {

    public static void trackEvent(String trackScreenEvent) {
        Bundle b = new Bundle();
        b.putString(ConstantsUtils.TrackEvent.TRACK_SCREEN_ACTION, trackScreenEvent);
        Application.firebaseAnalytics.logEvent(ConstantsUtils.TrackEvent.TRACK_CONSULTA_FIPE, b);
    }
}
