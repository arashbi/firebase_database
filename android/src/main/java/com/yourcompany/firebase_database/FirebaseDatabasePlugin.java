package com.yourcompany.firebase_database;

import io.flutter.app.FlutterActivity;
import io.flutter.plugin.common.FlutterMethodChannel;
import io.flutter.plugin.common.FlutterMethodChannel.MethodCallHandler;
import io.flutter.plugin.common.FlutterMethodChannel.Response;
import io.flutter.plugin.common.MethodCall;

import java.util.HashMap;
import java.util.Map;

/**
 * FirebaseDatabasePlugin
 */
public class FirebaseDatabasePlugin implements MethodCallHandler {
  private FlutterActivity activity;

  public static void register(FlutterActivity activity) {
    new FirebaseDatabasePlugin(activity);
  }

  private FirebaseDatabasePlugin(FlutterActivity activity) {
    this.activity = activity;
    new FlutterMethodChannel(activity.getFlutterView(), "firebase_database").setMethodCallHandler(this);
  }

  @Override
  public void onMethodCall(MethodCall call, Response response) {
    if (call.method.equals("getPlatformVersion")) {
      response.success("Android " + android.os.Build.VERSION.RELEASE);
    } else {
      response.notImplemented();
    }
  }
}
