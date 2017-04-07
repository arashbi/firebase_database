#import "FirebaseDatabasePlugin.h"

#import <Firebase/Firebase.h>

@interface NSError(FlutterError)
@property (readonly, nonatomic) FlutterError *flutterError;
@end

@implementation NSError(FlutterError)
- (FlutterError *)flutterError {
  return [FlutterError errorWithCode:[NSString stringWithFormat:@"Error %d", self.code]
                             message:self.domain
                             details:self.localizedDescription];
}
@end

@implementation FirebaseDatabasePlugin {
}

- (instancetype)initWithFlutterView:(FlutterViewController *)flutterView {
  self = [super init];
  if (self) {
    if (![FIRApp defaultApp]) {
      [FIRApp configure];
    }
    FlutterMethodChannel *channel = [FlutterMethodChannel
                                     methodChannelWithName:@"firebase_database"
                                     binaryMessenger:flutterView];
    [[[FIRDatabase database].reference queryLimitedToLast:10]
     observeEventType:FIRDataEventTypeChildAdded withBlock:^(FIRDataSnapshot * _Nonnull snapshot) {
      [channel invokeMethod:@"DatabaseReference#childAdded" arguments:@[snapshot.value]];
    }];
    [channel setMethodCallHandler:^(FlutterMethodCall *call,
                                    FlutterResultReceiver result) {
      if ([@"DatabaseReference#set" isEqualToString:call.method]) {
        NSDictionary *data = call.arguments[0];
        FIRDatabaseReference *ref = [[FIRDatabase database].reference childByAutoId];
        [ref updateChildValues:data
         withCompletionBlock:^(NSError * _Nullable error, FIRDatabaseReference * _Nonnull ref) {
             if (error != nil) {
               result(error.flutterError);
             } else {
               result(nil);
             }
         }];
      }
    }];
  }
  return self;
}

@end
