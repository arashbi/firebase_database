// Copyright 2017, the Flutter project authors.  Please see the AUTHORS file
// for details. All rights reserved. Use of this source code is governed by a
// BSD-style license that can be found in the LICENSE file.

import 'dart:async';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

// Temporary stubs while we build out a PlatformMessages-based implementation.

const Duration delay = const Duration(seconds: 1);

class FirebaseDatabase {
  static const PlatformMethodChannel _channel =
      const PlatformMethodChannel('firebase_database');

  FirebaseDatabase() {
    _channel.setMethodCallHandler((MethodCall call) {
      if (call.method == "DatabaseReference#childAdded") {
        Event event = new Event(call.arguments[0]);
        DatabaseReference._childAdded.add(event);
      }
    });
  }

  static FirebaseDatabase _instance = new FirebaseDatabase();
  static FirebaseDatabase get instance => _instance;
  DatabaseReference reference() => new DatabaseReference();
}

class DatabaseReference {
  static StreamController<Event> _childAdded = new StreamController<Event>.broadcast();
  Stream<Event> get onChildAdded => _childAdded.stream;
  DatabaseReference push() => new DatabaseReference();
  Future set(Map<String, dynamic> value) async {
    await FirebaseDatabase._channel.invokeMethod(
      "DatabaseReference#set",
      [value]
    );
    return value;
  }
}

class FirebaseAuth {
  static FirebaseAuth get instance => new FirebaseAuth();
  Future signInAnonymously() {
    return new Future.value(<String, dynamic>{});
  }
}

class Event {
  Event(dynamic val) : snapshot = new DataSnapshot(val);
  final DataSnapshot snapshot;
}

class DataSnapshot {
  dynamic _val;
  DataSnapshot(this._val);
  dynamic val() => _val;
}

class FirebaseMessaging {
}
