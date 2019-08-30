package swapnil.calcii.legacyioscalculator.analytics;

import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class Analytics {
    public enum Event {
        CALCULATE, ERROR
    }

    public static void logAnalytics(FirebaseAnalytics analytics, Event e, Bundle extras) {
        switch (e) {
            case CALCULATE:
                analytics.logEvent("Calculate", extras);
                break;

            case ERROR:
                analytics.logEvent("Error", extras);
                break;
        }
    }

}
