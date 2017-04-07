package io.flutter.plugins.firebase.database;

import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ChildEventListener;

import io.flutter.app.FlutterActivity;
import io.flutter.plugin.common.FlutterMethodChannel;
import io.flutter.plugin.common.FlutterMethodChannel.MethodCallHandler;
import io.flutter.plugin.common.FlutterMethodChannel.Response;
import io.flutter.plugin.common.MethodCall;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * FirebaseDatabasePlugin
 */
public class FirebaseDatabasePlugin implements MethodCallHandler {
  private FlutterActivity activity;
  private FlutterMethodChannel channel;

  public static void register(FlutterActivity activity) {
    new FirebaseDatabasePlugin(activity);
  }

  private FirebaseDatabasePlugin(FlutterActivity activity) {
    this.activity = activity;
    this.channel = new FlutterMethodChannel(activity.getFlutterView(), "firebase_database");
    channel.setMethodCallHandler(this);
    FirebaseDatabase.getInstance().getReference().limitToLast(10).addChildEventListener(new ChildEventListener() {
      @Override
      public void onCancelled(DatabaseError error) {
      }
      @Override
      public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
        List arguments = Arrays.asList(snapshot.getValue());
        channel.invokeMethod("DatabaseReference#childAdded", arguments);
      }
      @Override
      public void onChildChanged(DataSnapshot snapshot, String previousChildName) {
      }
      @Override
      public void onChildMoved(DataSnapshot snapshot, String previousChildName) {
      }
      @Override
      public void onChildRemoved(DataSnapshot snapshot) {
      }
    });
  }

  @Override
  public void onMethodCall(MethodCall call, final Response response) {
    if (call.method.equals("DatabaseReference#set")) {
      List arguments = (List) call.arguments;
      Map data = (Map) arguments.get(0);
      DatabaseReference ref = FirebaseDatabase.getInstance().getReference().push();
      ref.updateChildren(data, new DatabaseReference.CompletionListener() {
        @Override
        public void onComplete(DatabaseError error, DatabaseReference ref) {
          if (error != null) {
            response.error(String.valueOf(error.getCode()), error.getMessage(), error.getDetails());
          } else {
            response.success(null);
          }
        }
      });
    } else {
      response.notImplemented();
    }
  }
}
