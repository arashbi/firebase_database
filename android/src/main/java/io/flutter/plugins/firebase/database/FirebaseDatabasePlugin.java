package io.flutter.plugins.firebase.database;

import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ChildEventListener;

import io.flutter.app.FlutterActivity;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.MethodCall;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * FirebaseDatabasePlugin
 */
public class FirebaseDatabasePlugin implements MethodCallHandler {
  private FlutterActivity activity;
  private MethodChannel channel;

  public static FirebaseDatabasePlugin register(FlutterActivity activity) {
    return new FirebaseDatabasePlugin(activity);
  }

  private FirebaseDatabasePlugin(FlutterActivity activity) {
    this.activity = activity;
    this.channel = new MethodChannel(activity.getFlutterView(), "firebase_database");
    channel.setMethodCallHandler(this);
    FirebaseDatabase.getInstance().getReference().limitToLast(10).addChildEventListener(new ChildEventListener() {
      @Override
      public void onCancelled(DatabaseError error) {
      }
      @Override
      public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
        List arguments = Arrays.asList(snapshot.getKey(), snapshot.getValue());
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
  public void onMethodCall(MethodCall call, final Result result) {
    if (call.method.equals("DatabaseReference#set")) {
      List arguments = (List) call.arguments;
      Map data = (Map) arguments.get(0);
      DatabaseReference ref = FirebaseDatabase.getInstance().getReference().push();
      ref.updateChildren(data, new DatabaseReference.CompletionListener() {
        @Override
        public void onComplete(DatabaseError error, DatabaseReference ref) {
          if (error != null) {
            result.error(String.valueOf(error.getCode()), error.getMessage(), error.getDetails());
          } else {
            result.success(null);
          }
        }
      });
    } else {
      result.notImplemented();
    }
  }
}
